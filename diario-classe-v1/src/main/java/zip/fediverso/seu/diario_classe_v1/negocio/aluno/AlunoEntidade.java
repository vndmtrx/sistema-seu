package zip.fediverso.seu.diario_classe_v1.negocio.aluno;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zip.fediverso.seu.diario_classe_v1.negocio.vinculo.Vinculo;
import zip.fediverso.seu.diario_classe_v1.utils.negocio.EntidadeBaseAbstrata;

/**
 * Representa um aluno no sistema.
 * <p>
 * Esta classe usa Lombok para gerar getters e setters.
 * </p>
 *
 * <p><b>Campos:</b></p>
 * <ul>
 *     <li>{@code matricula}: Identificador único do aluno. Não pode ser nulo, máximo de 20 caracteres, e deve ser único.</li>
 *     <li>{@code nome}: Nome do aluno. Não pode ser nulo e máximo de 60 caracteres.</li>
 *     <li>{@code vinculos}: Vínculos associados ao aluno.</li>
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
@AttributeOverride(name = "id", column = @Column(name = "id_aluno"))
@Table(name="alunos")
public class AlunoEntidade extends EntidadeBaseAbstrata {
    /**
     * Identificador universal para a serialização da classe.
     * <p>
     * Utilizado para assegurar que uma classe serializada pode ser desserializada corretamente, 
     * mantendo a compatibilidade durante modificações na classe.
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do aluno. Não pode ser nulo, máximo de 20 caracteres, e deve ser único.
     */
    @Column(nullable = false, length = 20, unique = true)
    private String matricula;
    
    /**
     * Nome do aluno. Não pode ser nulo e máximo de 60 caracteres.
     */
    @Column(nullable = false, length = 60)
    private String nome;

    /**
     * Vínculos associados ao aluno.
     */
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vinculo> vinculos = new HashSet<>();
}
