package zip.fediverso.seu.diario_classe_v1.negocio.frequencia;

/**
 * Enumeração que representa os diferentes tipos de frequência no sistema.
 *
 * <p><b>Tipos de Frequência:</b></p>
 * <ul>
 *     <li>{@code PRESENCA}: Presença do aluno no encontro.</li>
 *     <li>{@code AUSENCIA}: Ausência do aluno no encontro.</li>
 *     <li>{@code JUSTIFICATIVA}: Ausência justificada do aluno no encontro.</li>
 * </ul>
 */
public enum TipoFrequenciaEnum {
    /**
     * Presença do aluno no encontro.
     */
    PRESENCA,

    /**
     * Ausência do aluno no encontro.
     */
    AUSENCIA,

    /**
     * Ausência justificada do aluno no encontro.
     */
    JUSTIFICATIVA
}
