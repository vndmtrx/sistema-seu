package zip.fediverso.seu.diario_classe_v1.modelos;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;

/**
 * Representa um diário no sistema.
 * <p>
 * Esta classe usa Lombok para gerar getters e setters.
 * </p>
 *
 * <p><b>Campos:</b></p>
 * <ul>
 *     <li>{@code curso}: Curso associado ao diário. Máximo de 30 caracteres.</li>
 *     <li>{@code turma}: Turma associada ao diário. Máximo de 30 caracteres.</li>
 *     <li>{@code componente}: Componente curricular associado ao diário. Máximo de 30 caracteres.</li>
 *     <li>{@code turno}: Turno associado ao diário. Máximo de 30 caracteres.</li>
 *     <li>{@code periodo}: Período associado ao diário. Máximo de 30 caracteres.</li>
 *     <li>{@code vinculos}: Vínculos associados ao diário.</li>
 *     <li>{@code encontros}: Encontros associados ao diário.</li>
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
@AttributeOverride(name = "id", column = @Column(name = "id_diario"))
@Table(name="diarios")
public class Diario extends EntidadeBaseAbstrata {
    /**
     * Identificador universal para a serialização da classe.
     * <p>
     * Utilizado para assegurar que uma classe serializada pode ser desserializada corretamente, 
     * mantendo a compatibilidade durante modificações na classe.
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Curso associado ao diário. Máximo de 30 caracteres.
     */
    @Column(length = 30)
    private String curso;

    /**
     * Turma associada ao diário. Máximo de 30 caracteres.
     */
    @Column(length = 30)
    private String turma;

    /**
     * Componente curricular associado ao diário. Máximo de 30 caracteres.
     */
    @Column(length = 30)
    private String componente;

    /**
     * Turno associado ao diário. Máximo de 30 caracteres.
     */
    @Column(length = 30)
    private String turno;

    /**
     * Período associado ao diário. Máximo de 30 caracteres.
     */
    @Column(length = 30)
    private String periodo;

    /**
     * Vínculos associados ao diário.
     */
    @OneToMany(mappedBy = "diario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vinculo> vinculos = new HashSet<>();

    /**
     * Encontros associados ao diário.
     */
    @OneToMany(mappedBy = "diario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Encontro> encontros = new HashSet<>();
}
