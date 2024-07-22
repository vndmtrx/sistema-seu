package zip.fediverso.seu.diario_classe_v1.negocio.aluno;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço para operações de negócio relacionadas ao Aluno.
 * <p>
 * Esta classe fornece métodos para criar, atualizar, deletar e recuperar alunos do sistema,
 * convertendo as entidades Aluno em DTOs (Data Transfer Objects) para interação com outras
 * camadas da aplicação. A classe também garante que todas as operações sejam executadas dentro
 * de um contexto transacional para evitar problemas de carregamento Lazy.
 * </p>
 * 
 * <p><b>Métodos:</b></p>
 * <ul>
 *     <li>{@link #obterTodosAlunos()}: Obtém todos os alunos do sistema.</li>
 *     <li>{@link #obterAlunoPorId(Long)}: Obtém um aluno pelo seu ID.</li>
 *     <li>{@link #obterAlunoPorMatricula(String)}: Obtém um aluno pela sua matrícula.</li>
 *     <li>{@link #criarAluno(AlunoDto)}: Cria um novo aluno no sistema.</li>
 *     <li>{@link #atualizarAluno(AlunoDto)}: Atualiza um aluno existente no sistema.</li>
 *     <li>{@link #deletarAluno(AlunoDto)}: Deleta um aluno pelo seu ID.</li>
 * </ul>
 */
@Service
public class AlunoServico {

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private AlunoMapper alunoMapper;

    /**
     * Obtém todos os alunos do sistema.
     * 
     * @return Lista de AlunoDto representando todos os alunos.
     */
    @Transactional(readOnly = true)
    public List<AlunoDto> obterTodosAlunos() {
        List<AlunoEntidade> alunos = alunoRepositorio.findAll();
        return alunos.stream().map(alunoMapper::paraDto).collect(Collectors.toList());
    }

    /**
     * Obtém todos os alunos ativos do sistema.
     * 
     * @return Lista de AlunoDto representando todos os alunos ativos.
     */
    @Transactional(readOnly = true)
    public List<AlunoDto> obterTodosAlunosAtivos() {
        List<AlunoEntidade> alunosAtivos = alunoRepositorio.buscaPorStatusAtivo();
        return alunosAtivos.stream().map(alunoMapper::paraDto).collect(Collectors.toList());
    }

    /**
     * Obtém um aluno pelo seu ID.
     * 
     * @param id Identificador do aluno.
     * @return AlunoDto representando o aluno, ou null se não encontrado.
     * @throws AlunoExcecao Se o aluno não existe.
     */
    @Transactional(readOnly = true)
    public AlunoDto obterAlunoPorId(UUID id) throws AlunoExcecao {
        Optional<AlunoEntidade> aluno = alunoRepositorio.findById(id);
        return aluno.map(alunoMapper::paraDto).orElseThrow(() -> new AlunoExcecao("Aluno não existe.", null));
    }

    /**
     * Obtém um aluno pela sua matrícula.
     * 
     * @param matricula Matrícula do aluno.
     * @return AlunoDto representando o aluno, ou null se não encontrado.
     */
    @Transactional(readOnly = true)
    public AlunoDto obterAlunoPorMatricula(String matricula) {
        Optional<AlunoEntidade> aluno = alunoRepositorio.buscaPorMatricula(matricula);
        return aluno.map(alunoMapper::paraDto).orElseThrow(() -> new AlunoExcecao("Matrícula não existe.", null));
    }

    /**
     * Cria um novo aluno no sistema.
     * 
     * @param alunoDto DTO contendo as informações do aluno a ser criado.
     * @return AlunoDto representando o aluno criado.
     * @throws AlunoExcecao Se o aluno existe ou se há uma violação de restrição.
     */
    @Transactional
    public AlunoDto criarAluno(AlunoDto alunoDto) throws AlunoExcecao {
        if (alunoDto == null) {
            throw new AlunoExcecao("AlunoDto não pode ser NULL.", null);
        }

        try {
            if (alunoDto.getId() != null) {
                Boolean alunoExiste = alunoRepositorio.existsById(alunoDto.getId());
                if (alunoExiste) {
                    throw new AlunoExcecao("Aluno já existe. ID: " + alunoDto.getId(), null);
                } else {
                    throw new AlunoExcecao("Aluno não deve conter Id no ato de criação, pois este valor é gerado automaticamente.", null);
                }
            }

            AlunoEntidade aluno = alunoMapper.paraEntidade(alunoDto);
            aluno = alunoRepositorio.saveAndFlush(aluno);
            return alunoMapper.paraDto(aluno);
        } catch (JpaSystemException e) {
            if (e.getMessage().contains("UNIQUE")) {
                throw new AlunoExcecao("Matrícula já existe: " + alunoDto.getMatricula(), e);
            } else {
                throw e;
            }
        }
    }

    /**
     * Atualiza um aluno existente no sistema.
     * 
     * @param alunoDto DTO contendo as novas informações do aluno.
     * @return AlunoDto representando o aluno atualizado, ou null se não encontrado.
     * @throws AlunoExcecao Se o aluno não existe ou se há uma violação de restrição.
     */
    @Transactional
    public AlunoDto atualizarAluno(AlunoDto alunoDto) throws AlunoExcecao {
        if (alunoDto == null) {
            throw new AlunoExcecao("AlunoDto não pode ser NULL.", null);
        }

        try {
            if (alunoDto.getId() == null) {
                throw new AlunoExcecao("Não é possível atualizar sem Id do Aluno.", null);
            } else {
                Boolean alunoExiste = alunoRepositorio.existsById(alunoDto.getId());
                if (!alunoExiste) {
                    throw new AlunoExcecao("Aluno não existe.", null);
                }
            }

            Optional<AlunoEntidade> alunoExistente = alunoRepositorio.findById(alunoDto.getId());

            AlunoEntidade aluno = alunoExistente.get();
            aluno.setMatricula(alunoDto.getMatricula());
            aluno.setNome(alunoDto.getNome());
            aluno.setStatus(alunoDto.getStatus());

            aluno = alunoRepositorio.saveAndFlush(aluno);
            return alunoMapper.paraDto(aluno);
        } catch (JpaSystemException e) {
            if (e.getMessage().contains("UNIQUE")) {
                throw new AlunoExcecao("Matrícula já existe: " + alunoDto.getMatricula(), e);
            } else {
                throw e;
            }
        }
    }

    /**
     * Deleta um aluno pelo seu ID.
     * 
     * @param alunoDto DTO contendo o ID do aluno a ser deletado.
     */
    @Transactional
    public void deletarAluno(AlunoDto alunoDto) {
        if (alunoDto == null) {
            throw new AlunoExcecao("AlunoDto não pode ser NULL.", null);
        }

        if (alunoDto.getId() == null) {
            throw new AlunoExcecao("Não é possível deletar sem Id do Aluno.", null);
        } else {
            Boolean alunoExiste = alunoRepositorio.existsById(alunoDto.getId());
            if (!alunoExiste) {
                throw new AlunoExcecao("Aluno não existe.", null);
            }
        }

        alunoRepositorio.deleteById(alunoDto.getId());
    }
}
