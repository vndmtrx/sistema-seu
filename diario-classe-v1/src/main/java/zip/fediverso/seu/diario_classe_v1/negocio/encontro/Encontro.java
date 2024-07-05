package zip.fediverso.seu.diario_classe_v1.negocio.encontro;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zip.fediverso.seu.diario_classe_v1.negocio.diario.Diario;
import zip.fediverso.seu.diario_classe_v1.utils.negocio.EntidadeBaseAbstrata;

/**
 * Representa um encontro no sistema.
 * <p>
 * Esta classe usa Lombok para gerar getters e setters.
 * </p>
 *
 * <p><b>Campos:</b></p>
 * <ul>
 *     <li>{@code diario}: Diário associado ao encontro.</li>
 *     <li>{@code data}: Data do encontro.</li>
 *     <li>{@code resumoAula}: Resumo da aula realizada no encontro. Máximo de 200 caracteres.</li>
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
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_encontro"))
@Table(name="encontros")
public class Encontro extends EntidadeBaseAbstrata {
    /**
     * Identificador universal para a serialização da classe.
     * <p>
     * Utilizado para assegurar que uma classe serializada pode ser desserializada corretamente, 
     * mantendo a compatibilidade durante modificações na classe.
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Diário associado ao encontro.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diario_id", nullable = false)
    private Diario diario;

    /**
     * Data do encontro.
     */
    @Column
    @Temporal(TemporalType.DATE)
    private Date data;
    
    /**
     * Resumo da aula realizada no encontro. Máximo de 200 caracteres.
     */
    @Column(length = 200)
    private String resumoAula;
}
