package zip.fediverso.seu.diario_classe_v1.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;

/**
 * Representa um vínculo entre um aluno e um diário no sistema.
 * <p>
 * Esta classe usa Lombok para gerar getters e setters.
 * </p>
 *
 * <p><b>Campos:</b></p>
 * <ul>
 *     <li>{@code aluno}: Aluno associado ao vínculo.</li>
 *     <li>{@code diario}: Diário associado ao vínculo.</li>
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
 *     <li>Restrição de unicidade: {@code AlunoDiarioVinculoUnico} que assegura que um aluno não pode estar vinculado mais de uma vez ao mesmo diário.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_vinculo"))
@Table(name="vinculos", uniqueConstraints = {
    @UniqueConstraint(name = "AlunoDiarioVinculoUnico", columnNames = {"aluno_id", "diario_id"})
})
public class Vinculo extends EntidadeBaseAbstrata {
    /**
     * Identificador universal para a serialização da classe.
     * <p>
     * Utilizado para assegurar que uma classe serializada pode ser desserializada corretamente, 
     * mantendo a compatibilidade durante modificações na classe.
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Aluno associado ao vínculo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    /**
     * Diário associado ao vínculo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diario_id", nullable = false)
    private Diario diario;
}
