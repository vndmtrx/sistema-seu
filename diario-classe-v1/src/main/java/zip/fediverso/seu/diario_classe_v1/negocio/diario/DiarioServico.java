package zip.fediverso.seu.diario_classe_v1.negocio.diario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço para operações de negócio relacionadas ao Diario.
 * <p>
 * Esta classe fornece métodos para criar, atualizar, deletar e recuperar diarios do sistema,
 * convertendo as entidades Diario em DTOs (Data Transfer Objects) para interação com outras
 * camadas da aplicação. A classe também garante que todas as operações sejam executadas dentro
 * de um contexto transacional para evitar problemas de carregamento Lazy.
 * </p>
 * 
 * <p><b>Métodos:</b></p>
 * <ul>
 *     <li>{@link #obterTodosDiarios()}: Obtém todos os diarios do sistema.</li>
 *     <li>{@link #obterDiarioPorId(Long)}: Obtém um diario pelo seu ID.</li>
 *     <li>{@link #criarDiario(DiarioDto)}: Cria um novo diario no sistema.</li>
 *     <li>{@link #atualizarDiario(DiarioDto)}: Atualiza um diario existente no sistema.</li>
 *     <li>{@link #deletarDiario(DiarioDto)}: Deleta um diario pelo seu ID.</li>
 * </ul>
 */
@Service
public class DiarioServico {

    @Autowired
    private DiarioRepositorio diarioRepositorio;

    @Autowired
    private DiarioMapper diarioMapper;

    /**
     * Obtém todos os diarios do sistema.
     * 
     * @return Lista de DiarioDto representando todos os diarios.
     */
    @Transactional(readOnly = true)
    public List<DiarioDto> obterTodosDiarios() {
        List<DiarioEntidade> diarios = diarioRepositorio.findAll();
        return diarios.stream().map(diarioMapper::paraDto).collect(Collectors.toList());
    }

    /**
     * Obtém um diario pelo seu ID.
     * 
     * @param id Identificador do diario.
     * @return DiarioDto representando o diario, ou null se não encontrado.
     * @throws DiarioExcecao Se o diario não existe.
     */
    @Transactional(readOnly = true)
    public DiarioDto obterDiarioPorId(UUID id) throws DiarioExcecao {
        Optional<DiarioEntidade> diario = diarioRepositorio.findById(id);
        return diario.map(diarioMapper::paraDto).orElseThrow(() -> new DiarioExcecao("Diario não existe.", null));
    }

    /**
     * Cria um novo diario no sistema.
     * 
     * @param diarioDto DTO contendo as informações do diario a ser criado.
     * @return DiarioDto representando o diario criado.
     * @throws DiarioExcecao Se o diario existe ou se há uma violação de restrição.
     */
    @Transactional
    public DiarioDto criarDiario(DiarioDto diarioDto) throws DiarioExcecao {
        if (diarioDto == null) {
            throw new DiarioExcecao("DiarioDto não pode ser NULL.", null);
        }

        try {
            if (diarioDto.getId() != null) {
                Boolean diarioExiste = diarioRepositorio.existsById(diarioDto.getId());
                if (diarioExiste) {
                    throw new DiarioExcecao("Diario já existe. ID: " + diarioDto.getId(), null);
                } else {
                    throw new DiarioExcecao("Diario não deve conter Id no ato de criação, pois este valor é gerado automaticamente.", null);
                }
            }

            DiarioEntidade diario = diarioMapper.paraEntidade(diarioDto);
            diario = diarioRepositorio.saveAndFlush(diario);
            return diarioMapper.paraDto(diario);
        } catch (JpaSystemException e) {
            if (e.getMessage().contains("UNIQUE")) {
                throw new DiarioExcecao("ID já existe: " + diarioDto.getId(), e);
            } else {
                throw e;
            }
        }
    }

    /**
     * Atualiza um diario existente no sistema.
     * 
     * @param diarioDto DTO contendo as novas informações do diario.
     * @return DiarioDto representando o diario atualizado, ou null se não encontrado.
     * @throws DiarioExcecao Se o diario não existe ou se há uma violação de restrição.
     */
    @Transactional
    public DiarioDto atualizarDiario(DiarioDto diarioDto) throws DiarioExcecao {
        if (diarioDto == null) {
            throw new DiarioExcecao("DiarioDto não pode ser NULL.", null);
        }

        try {
            if (diarioDto.getId() == null) {
                throw new DiarioExcecao("Não é possível atualizar sem Id do Diario.", null);
            } else {
                Boolean diarioExiste = diarioRepositorio.existsById(diarioDto.getId());
                if (!diarioExiste) {
                    throw new DiarioExcecao("Diario não existe.", null);
                }
            }

            Optional<DiarioEntidade> diarioExistente = diarioRepositorio.findById(diarioDto.getId());

            DiarioEntidade diario = diarioExistente.get();
            diario.setCurso(diarioDto.getCurso());
            diario.setTurma(diarioDto.getTurma());
            diario.setComponente(diarioDto.getComponente());
            diario.setTurno(diarioDto.getTurno());
            diario.setPeriodo(diarioDto.getPeriodo());

            diario = diarioRepositorio.saveAndFlush(diario);
            return diarioMapper.paraDto(diario);
        } catch (JpaSystemException e) {
            if (e.getMessage().contains("UNIQUE")) {
                throw new DiarioExcecao("ID já existe: " + diarioDto.getId(), e);
            } else {
                throw e;
            }
        }
    }

    /**
     * Deleta um diario pelo seu ID.
     * 
     * @param diarioDto DTO contendo o ID do diario a ser deletado.
     */
    @Transactional
    public void deletarDiario(DiarioDto diarioDto) {
        if (diarioDto == null) {
            throw new DiarioExcecao("DiarioDto não pode ser NULL.", null);
        }

        if (diarioDto.getId() == null) {
            throw new DiarioExcecao("Não é possível deletar sem Id do Diario.", null);
        } else {
            Boolean diarioExiste = diarioRepositorio.existsById(diarioDto.getId());
            if (!diarioExiste) {
                throw new DiarioExcecao("Diario não existe.", null);
            }
        }

        diarioRepositorio.deleteById(diarioDto.getId());
    }

    /**
     * Método não implementado para obtenção de vínculos.
     */
    public void obterVinculos() {
        throw new UnsupportedOperationException("Método não implementado");
    }

    /**
     * Método não implementado para obtenção de encontros.
     */
    public void obterEncontros() {
        throw new UnsupportedOperationException("Método não implementado");
    }
}
