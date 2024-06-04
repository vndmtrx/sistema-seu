package zip.fediverso.seu.diario_classe_v1.modelos;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_presenca"))
@Table(name="presencas")
public class Presenca extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

}
