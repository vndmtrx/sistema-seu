package zip.fediverso.seu.diario_classe_v1.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zip.fediverso.seu.diario_classe_v1.dominio.Aluno;
import zip.fediverso.seu.diario_classe_v1.repositorio.AlunoRepositorio;
import zip.fediverso.seu.diario_classe_v1.servico.dto.AlunoDto;

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
 *     <li>{@link #atualizarAluno(Long, AlunoDto)}: Atualiza um aluno existente no sistema.</li>
 *     <li>{@link #deletarAluno(Long)}: Deleta um aluno pelo seu ID.</li>
 *     <li>{@link #obterAlunosPorDiario(Long)}: Obtém todos os alunos associados a um diário específico.</li>
 * </ul>
 *
 * <p><b>Conversão de DTO:</b></p>
 * <ul>
 *     <li>{@link #converteParaDto(Aluno)}: Converte uma entidade Aluno para um DTO AlunoDto.</li>
 *     <li>{@link #converteParaEntidade(AlunoDto)}: Converte um DTO AlunoDto para uma entidade Aluno.</li>
 * </ul>
 */
@Service
public class AlunoServico {

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    /**
     * Obtém todos os alunos do sistema.
     * 
     * @return Lista de AlunoDTO representando todos os alunos.
     */
    @Transactional(readOnly = true)
    public List<AlunoDto> obterTodosAlunos() {
        List<Aluno> alunos = alunoRepositorio.findAll();
        return alunos.stream().map(this::converteParaDto).collect(Collectors.toList());
    }

    /**
     * Obtém um aluno pelo seu ID.
     * 
     * @param id Identificador do aluno.
     * @return AlunoDTO representando o aluno, ou null se não encontrado.
     */
    @Transactional(readOnly = true)
    public AlunoDto obterAlunoPorId(Long id) {
        Optional<Aluno> aluno = alunoRepositorio.findById(id);
        return aluno.map(this::converteParaDto).orElse(null);
    }

    /**
     * Obtém um aluno pela sua matrícula.
     * 
     * @param matricula Matrícula do aluno.
     * @return AlunoDTO representando o aluno, ou null se não encontrado.
     */
    @Transactional(readOnly = true)
    public AlunoDto obterAlunoPorMatricula(String matricula) {
        Optional<Aluno> aluno = alunoRepositorio.buscaPorMatricula(matricula);
        return aluno.map(this::converteParaDto).orElse(null);
    }

    /**
     * Cria um novo aluno no sistema.
     * 
     * @param alunoDto DTO contendo as informações do aluno a ser criado.
     * @return AlunoDTO representando o aluno criado.
     */
    @Transactional
    public AlunoDto criarAluno(AlunoDto alunoDto) {
        Aluno aluno = converteParaEntidade(alunoDto);
        aluno = alunoRepositorio.saveAndFlush(aluno);
        return converteParaDto(aluno);
    }

    /**
     * Atualiza um aluno existente no sistema.
     * 
     * @param id Identificador do aluno a ser atualizado.
     * @param alunoDto DTO contendo as novas informações do aluno.
     * @return AlunoDTO representando o aluno atualizado, ou null se não encontrado.
     */
    @Transactional
    public AlunoDto atualizarAluno(Long id, AlunoDto alunoDto) {
        if (!alunoRepositorio.existsById(id)) {
            return null;
        }
        Aluno aluno = converteParaEntidade(alunoDto);
        aluno.setId(id);
        aluno = alunoRepositorio.saveAndFlush(aluno);
        return converteParaDto(aluno);
    }

    /**
     * Deleta um aluno pelo seu ID.
     * 
     * @param id Identificador do aluno a ser deletado.
     */
    @Transactional
    public void deletarAluno(Long id) {
        alunoRepositorio.deleteById(id);
    }

    /**
     * Converte uma entidade Aluno para um DTO AlunoDTO.
     * 
     * @param aluno Entidade Aluno a ser convertida.
     * @return AlunoDTO representando o aluno.
     */
    private AlunoDto converteParaDto(Aluno aluno) {
        AlunoDto alunoDTO = new AlunoDto();
        alunoDTO.setId(aluno.getId());
        alunoDTO.setMatricula(aluno.getMatricula());
        alunoDTO.setNome(aluno.getNome());
        return alunoDTO;
    }

    /**
     * Converte um DTO AlunoDTO para uma entidade Aluno.
     * 
     * @param alunoDto DTO a ser convertido.
     * @return Entidade Aluno representando o aluno.
     */
    private Aluno converteParaEntidade(AlunoDto alunoDto) {
        Aluno aluno = new Aluno();
        aluno.setMatricula(alunoDto.getMatricula());
        aluno.setNome(alunoDto.getNome());
        return aluno;
    }
}
