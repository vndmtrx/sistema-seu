package zip.fediverso.seu.diario_classe_v1.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;
import zip.fediverso.seu.diario_classe_v1.modelos.enums.TipoFrequencia;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_frequencia"))
@Table(name="frequencias", uniqueConstraints = {
    @UniqueConstraint(name = "AlunoEncontroUnico", columnNames = {"aluno_id", "encontro_id"})
})
public class Frequencia extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encontro_id", nullable = false)
    private Encontro encontro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoFrequencia tipoFrequencia;

    @Column(length = 100, nullable = true)
    private String comentario;
}