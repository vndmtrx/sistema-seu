package zip.fediverso.seu.diario_classe_v1.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_inscricao"))
@Table(name="inscricoes", uniqueConstraints = {
    @UniqueConstraint(name = "AlunoDiarioUnico", columnNames = {"aluno_id", "diario_id"})
})
public class Inscricao extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diario_id", nullable = false)
    private Diario diario;
}
