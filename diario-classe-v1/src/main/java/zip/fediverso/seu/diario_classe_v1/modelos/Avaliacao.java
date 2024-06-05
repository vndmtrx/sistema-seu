package zip.fediverso.seu.diario_classe_v1.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;
import zip.fediverso.seu.diario_classe_v1.modelos.enums.TipoAvaliacao;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_avaliacao"))
@Table(name="avaliacoes", uniqueConstraints = {
    @UniqueConstraint(name = "AlunoEncontroAvaliacaoUnico", columnNames = {"aluno_id", "encontro_id", "tipo_avaliacao"})
})
public class Avaliacao extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encontro_id", nullable = false)
    private Encontro encontro;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_avaliacao", nullable = false)
    private TipoAvaliacao tipoAvaliacao;

    @Column(length = 5, nullable = false)
    private String criterio;
}
