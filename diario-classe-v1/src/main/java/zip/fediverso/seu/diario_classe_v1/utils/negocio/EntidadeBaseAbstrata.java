package zip.fediverso.seu.diario_classe_v1.utils.negocio;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe base abstrata para todas as entidades do sistema.
 * <p>
 * Esta classe fornece propriedades comuns para todas as entidades, como id, timestamps de criação e atualização, e controle de versão para concorrência otimista.
 * </p>
 *
 * <p><b>Campos:</b></p>
 * <ul>
 *     <li>{@code id}: Identificador único da entidade, gerado automaticamente.</li>
 *     <li>{@code criadoEm}: Timestamp de criação da entidade. Não atualizável.</li>
 *     <li>{@code alteradoEm}: Timestamp de última atualização da entidade.</li>
 *     <li>{@code versao}: Versão da entidade, utilizada para controle de concorrência otimista.</li>
 * </ul>
 *
 * <p><b>Campos Herdados:</b></p>
 * <ul>
 *     <li>{@code serialVersionUID}: Identificador universal para a serialização da classe.</li>
 * </ul>
 */
@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class EntidadeBaseAbstrata implements Serializable {
    /**
     * Identificador universal para a serialização da classe.
     * <p>
     * Utilizado para assegurar que uma classe serializada pode ser desserializada corretamente, 
     * mantendo a compatibilidade durante modificações na classe.
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único da entidade, gerado automaticamente.
     */
    @Id
    @EqualsAndHashCode.Include
    @Column
    private UUID id;

    /**
     * Timestamp de criação da entidade. Não atualizável.
     */
    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    /**
     * Timestamp de última atualização da entidade.
     */
    @UpdateTimestamp
    @Column(name = "alterado_em")
    private LocalDateTime alteradoEm;

    /**
     * Versão da entidade, utilizada para controle de concorrência otimista.
     */
    @Version
    @Column
    private Instant versao;

    @PrePersist
    @Generated
    private void geraUUID() {
        if (id == null) {
            this.id = UUIDv7.randomUUID();
        }
    }
}
