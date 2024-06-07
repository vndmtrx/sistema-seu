package zip.fediverso.seu.diario_classe_v1.dominio.enums;

/**
 * Enumeração que representa os diferentes tipos de avaliação no sistema.
 *
 * <p><b>Tipos de Avaliação:</b></p>
 * <ul>
 *     <li>{@code PROVA}: Avaliação tradicional que testa o conhecimento do aluno em uma matéria específica.</li>
 *     <li>{@code TRABALHO}: Avaliação baseada em trabalhos escritos ou pesquisas realizadas pelo aluno.</li>
 *     <li>{@code PROJETO}: Avaliação que envolve a execução de um projeto prático ou teórico.</li>
 *     <li>{@code APRESENTACAO}: Avaliação baseada em apresentações orais realizadas pelo aluno.</li>
 *     <li>{@code PARTICIPACAO}: Avaliação da participação do aluno em sala de aula ou em atividades escolares.</li>
 *     <li>{@code ATIVIDADE_PRATICA}: Avaliação de atividades práticas realizadas em laboratório ou em campo.</li>
 *     <li>{@code EXAME_FINAL}: Avaliação abrangente realizada ao final de um curso ou semestre.</li>
 *     <li>{@code SEMINARIO}: Avaliação baseada na apresentação e discussão de um tema específico em seminário.</li>
 *     <li>{@code AVALIACAO_CONTINUA}: Avaliação contínua que ocorre ao longo do curso, com múltiplas atividades e tarefas.</li>
 *     <li>{@code AVALIACAO_DIAGNOSTICA}: Avaliação realizada no início de um curso para identificar o nível de conhecimento prévio dos alunos.</li>
 *     <li>{@code AVALIACAO_FORMATIVA}: Avaliação que visa monitorar o progresso do aluno e fornecer feedback contínuo.</li>
 *     <li>{@code AVALIACAO_SUMATIVA}: Avaliação final que visa resumir o aprendizado do aluno ao final de um período.</li>
 *     <li>{@code AUTO_AVALIACAO}: Autoavaliação, onde o próprio aluno avalia seu desempenho e aprendizado.</li>
 *     <li>{@code AVALIACAO_PARES}: Avaliação realizada pelos colegas, onde os alunos avaliam uns aos outros.</li>
 * </ul>
 */
public enum TipoAvaliacao {
    /**
     * Avaliação tradicional que testa o conhecimento do aluno em uma matéria específica.
     */
    PROVA,

    /**
     * Avaliação baseada em trabalhos escritos ou pesquisas realizadas pelo aluno.
     */
    TRABALHO,

    /**
     * Avaliação que envolve a execução de um projeto prático ou teórico.
     */
    PROJETO,

    /**
     * Avaliação baseada em apresentações orais realizadas pelo aluno.
     */
    APRESENTACAO,

    /**
     * Avaliação da participação do aluno em sala de aula ou em atividades escolares.
     */
    PARTICIPACAO,

    /**
     * Avaliação de atividades práticas realizadas em laboratório ou em campo.
     */
    ATIVIDADE_PRATICA,

    /**
     * Avaliação abrangente realizada ao final de um curso ou semestre.
     */
    EXAME_FINAL,

    /**
     * Avaliação baseada na apresentação e discussão de um tema específico em seminário.
     */
    SEMINARIO,

    /**
     * Avaliação contínua que ocorre ao longo do curso, com múltiplas atividades e tarefas.
     */
    AVALIACAO_CONTINUA,

    /**
     * Avaliação realizada no início de um curso para identificar o nível de conhecimento prévio dos alunos.
     */
    AVALIACAO_DIAGNOSTICA,

    /**
     * Avaliação que visa monitorar o progresso do aluno e fornecer feedback contínuo.
     */
    AVALIACAO_FORMATIVA,

    /**
     * Avaliação final que visa resumir o aprendizado do aluno ao final de um período.
     */
    AVALIACAO_SUMATIVA,

    /**
     * Autoavaliação, onde o próprio aluno avalia seu desempenho e aprendizado.
     */
    AUTO_AVALIACAO,

    /**
     * Avaliação realizada pelos colegas, onde os alunos avaliam uns aos outros.
     */
    AVALIACAO_PARES
}
