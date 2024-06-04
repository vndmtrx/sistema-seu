package zip.fediverso.seu.diario_classe_v1.modelos;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_encontro"))
@Table(name="encontros")
public class Encontro extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diario_id", nullable = false)
    private Diario diario;

    @Column
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @Column(length = 200)
    private String resumoAula;
}
