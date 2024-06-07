package zip.fediverso.seu.diario_classe_v1.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;
import zip.fediverso.seu.diario_classe_v1.modelos.enums.TipoFrequencia;

/**
 * Representa uma frequência no sistema.
 * <p>
 * Esta classe usa Lombok para gerar getters e setters.
 * </p>
 *
 * <p><b>Campos:</b></p>
 * <ul>
 *     <li>{@code aluno}: Aluno associado à frequência.</li>
 *     <li>{@code encontro}: Encontro associado à frequência.</li>
 *     <li>{@code tipoFrequencia}: Tipo de frequência (presença, ausência, justificativa).</li>
 *     <li>{@code comentario}: Comentário adicional sobre a frequência. Máximo de 100 caracteres.</li>
 * </ul>
 *
 * <p><b>Campos Herdados:</b></p>
 * <ul>
 *     <li>{@code serialVersionUID}: Identificador universal para a serialização da classe.</li>
 *     <li>{@code id}: Identificador único da entidade.</li>
 *     <li>{@code criadoEm}: Timestamp de criação da entidade. Não atualizável.</li>
 *     <li>{@code alteradoEm}: Timestamp de última atualização da entidade.</li>
 *     <li>{@code versao}: Versão da entidade, utilizada para controle de concorrência otimista.</li>
 * </ul>
 * 
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>Restrição de unicidade: {@code AlunoEncontroFrequenciaUnico} que assegura que um aluno não pode ter mais de uma frequência registrada para o mesmo encontro.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_frequencia"))
@Table(name="frequencias", uniqueConstraints = {
    @UniqueConstraint(name = "AlunoEncontroFrequenciaUnico", columnNames = {"aluno_id", "encontro_id"})
})
public class Frequencia extends EntidadeBaseAbstrata {
    /**
     * Identificador universal para a serialização da classe.
     * <p>
     * Utilizado para assegurar que uma classe serializada pode ser desserializada corretamente, 
     * mantendo a compatibilidade durante modificações na classe.
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Aluno associado à frequência.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    /**
     * Encontro associado à frequência.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encontro_id", nullable = false)
    private Encontro encontro;

    /**
     * Tipo de frequência (presença, ausência, justificativa).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_frequencia", nullable = false)
    private TipoFrequencia tipoFrequencia;

    /**
     * Comentário adicional sobre a frequência. Máximo de 100 caracteres.
     */
    @Column(length = 100, nullable = true)
    private String comentario;
}
