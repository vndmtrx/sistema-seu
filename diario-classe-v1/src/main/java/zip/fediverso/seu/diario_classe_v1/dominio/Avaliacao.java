package zip.fediverso.seu.diario_classe_v1.dominio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zip.fediverso.seu.diario_classe_v1.dominio.base.EntidadeBaseAbstrata;
import zip.fediverso.seu.diario_classe_v1.dominio.enums.TipoAvaliacao;

/**
 * Representa uma avaliação no sistema.
 * <p>
 * Esta classe usa Lombok para gerar getters e setters.
 * </p>
 *
 * <p><b>Campos:</b></p>
 * <ul>
 *     <li>{@code aluno}: Aluno associado à avaliação.</li>
 *     <li>{@code encontro}: Encontro associado à avaliação.</li>
 *     <li>{@code tipoAvaliacao}: Tipo de avaliação (prova, trabalho, etc).</li>
 *     <li>{@code criterio}: Critério utilizado na avaliação. Máximo de 5 caracteres.</li>
 * </ul>
 *
 * <p><b>Campos Herdados:</b></p>
 * <ul>
 *     <li>{@code id}: Identificador único da entidade.</li>
 *     <li>{@code criadoEm}: Timestamp de criação da entidade. Não atualizável.</li>
 *     <li>{@code alteradoEm}: Timestamp de última atualização da entidade.</li>
 *     <li>{@code versao}: Versão da entidade, utilizada para controle de concorrência otimista.</li>
 * </ul>
 * 
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>Restrição de unicidade: {@code AlunoEncontroAvaliacaoUnico} que assegura que um aluno não pode ter mais de uma avaliação do mesmo tipo para o mesmo encontro.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_avaliacao"))
@Table(name="avaliacoes", uniqueConstraints = {
    @UniqueConstraint(name = "AlunoEncontroAvaliacaoUnico", columnNames = {"aluno_id", "encontro_id", "tipo_avaliacao"})
})
public class Avaliacao extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

    /**
     * Aluno associado à avaliação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    /**
     * Encontro associado à avaliação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encontro_id", nullable = false)
    private Encontro encontro;

    /**
     * Tipo de avaliação (prova, trabalho, etc).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_avaliacao", nullable = false)
    private TipoAvaliacao tipoAvaliacao;

    /**
     * Critério utilizado na avaliação. Máximo de 5 caracteres.
     */
    @Column(length = 5, nullable = false)
    private String criterio;
}
