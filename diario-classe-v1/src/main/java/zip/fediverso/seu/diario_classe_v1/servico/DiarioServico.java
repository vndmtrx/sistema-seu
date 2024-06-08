package zip.fediverso.seu.diario_classe_v1.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zip.fediverso.seu.diario_classe_v1.dominio.Aluno;
import zip.fediverso.seu.diario_classe_v1.dominio.Diario;
import zip.fediverso.seu.diario_classe_v1.dominio.Vinculo;
import zip.fediverso.seu.diario_classe_v1.repositorio.AlunoRepositorio;
import zip.fediverso.seu.diario_classe_v1.repositorio.DiarioRepositorio;
import zip.fediverso.seu.diario_classe_v1.repositorio.VinculoRepositorio;
import zip.fediverso.seu.diario_classe_v1.servico.dto.DiarioDto;

/**
 * Serviço para operações de negócio relacionadas ao Diario.
 * <p>
 * Esta classe fornece métodos para criar, atualizar, deletar e recuperar diários do sistema,
 * convertendo as entidades Diario em DTOs (Data Transfer Objects) para interação com outras
 * camadas da aplicação. A classe também garante que todas as operações sejam executadas dentro
 * de um contexto transacional para evitar problemas de carregamento Lazy.
 * </p>
 * 
 * <p><b>Métodos:</b></p>
 * <ul>
 *     <li>{@link #obterTodosDiarios()}: Obtém todos os diários do sistema.</li>
 *     <li>{@link #obterDiarioPorId(Long)}: Obtém um diário pelo seu ID.</li>
 *     <li>{@link #criarDiario(DiarioDto)}: Cria um novo diário no sistema.</li>
 *     <li>{@link #atualizarDiario(Long, DiarioDto)}: Atualiza um diário existente no sistema.</li>
 *     <li>{@link #deletarDiario(Long)}: Deleta um diário pelo seu ID.</li>
 *     <li>{@link #adicionarAluno(Long, Long)}: Adiciona um aluno a um diário.</li>
 *     <li>{@link #removerAluno(Long, Long)}: Remove um aluno de um diário.</li>
 * </ul>
 */
@Service
public class DiarioServico {

    @Autowired
    private DiarioRepositorio diarioRepositorio;

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private VinculoRepositorio vinculoRepositorio;

    /**
     * Obtém todos os diários do sistema.
     * 
     * @return Lista de DiarioDto representando todos os diários.
     */
    @Transactional(readOnly = true)
    public List<DiarioDto> obterTodosDiarios() {
        List<Diario> diarios = diarioRepositorio.findAll();
        return diarios.stream().map(this::converteParaDto).collect(Collectors.toList());
    }

    /**
     * Obtém um diário pelo seu ID.
     * 
     * @param id Identificador do diário.
     * @return DiarioDto representando o diário, ou null se não encontrado.
     */
    @Transactional(readOnly = true)
    public DiarioDto obterDiarioPorId(Long id) {
        Optional<Diario> diario = diarioRepositorio.findById(id);
        return diario.map(this::converteParaDto).orElse(null);
    }

    /**
     * Cria um novo diário no sistema.
     * 
     * @param diarioDto DTO contendo as informações do diário a ser criado.
     * @return DiarioDto representando o diário criado.
     */
    @Transactional
    public DiarioDto criarDiario(DiarioDto diarioDto) {
        Diario diario = converteParaEntidade(diarioDto);
        diario = diarioRepositorio.save(diario);
        return converteParaDto(diario);
    }

    /**
     * Atualiza um diário existente no sistema.
     * 
     * @param id Identificador do diário a ser atualizado.
     * @param diarioDto DTO contendo as novas informações do diário.
     * @return DiarioDto representando o diário atualizado, ou null se não encontrado.
     */
    @Transactional
    public DiarioDto atualizarDiario(Long id, DiarioDto diarioDto) {
        if (!diarioRepositorio.existsById(id)) {
            return null;
        }
        Diario diario = converteParaEntidade(diarioDto);
        diario.setId(id);
        diario = diarioRepositorio.saveAndFlush(diario);
        return converteParaDto(diario);
    }

    /**
     * Deleta um diário pelo seu ID.
     * 
     * @param id Identificador do diário a ser deletado.
     */
    @Transactional
    public void deletarDiario(Long id) {
        diarioRepositorio.deleteById(id);
    }

    /**
     * Adiciona um aluno a um diário.
     * 
     * @param diarioId ID do diário.
     * @param alunoId ID do aluno.
     */
    @Transactional
    public void adicionarAluno(Long diarioId, Long alunoId) {
        Optional<Diario> diarioOpt = diarioRepositorio.findById(diarioId);
        Optional<Aluno> alunoOpt = alunoRepositorio.findById(alunoId);
        
        if (diarioOpt.isPresent() && alunoOpt.isPresent()) {
            Diario diario = diarioOpt.get();
            Aluno aluno = alunoOpt.get();
            Vinculo vinculo = new Vinculo();
            vinculo.setDiario(diario);
            vinculo.setAluno(aluno);
            vinculoRepositorio.save(vinculo);
        }
    }

    /**
     * Remove um aluno de um diário.
     * 
     * @param diarioId ID do diário.
     * @param alunoId ID do aluno.
     */
    @Transactional
    public void removerAluno(Long diarioId, Long alunoId) {
        Optional<Vinculo> vinculoOpt = vinculoRepositorio.buscaPorIdAlunoEIdDiario(alunoId, diarioId);
        
        vinculoOpt.ifPresent(vinculoRepositorio::delete);
    }

    /**
     * Converte uma entidade Diario para um DTO DiarioDto.
     * 
     * @param diario Entidade Diario a ser convertida.
     * @return DiarioDto representando o diário.
     */
    private DiarioDto converteParaDto(Diario diario) {
        DiarioDto diarioDto = new DiarioDto();
        diarioDto.setId(diario.getId());
        diarioDto.setCurso(diario.getCurso());
        diarioDto.setTurma(diario.getTurma());
        diarioDto.setComponente(diario.getComponente());
        diarioDto.setTurno(diario.getTurno());
        diarioDto.setPeriodo(diario.getPeriodo());
        return diarioDto;
    }

    /**
     * Converte um DTO DiarioDto para uma entidade Diario.
     * 
     * @param diarioDto DTO a ser convertido.
     * @return Entidade Diario representando o diário.
     */
    private Diario converteParaEntidade(DiarioDto diarioDto) {
        Diario diario = new Diario();
        diario.setCurso(diarioDto.getCurso());
        diario.setTurma(diarioDto.getTurma());
        diario.setComponente(diarioDto.getComponente());
        diario.setTurno(diarioDto.getTurno());
        diario.setPeriodo(diarioDto.getPeriodo());
        return diario;
    }
}
