package zip.fediverso.seu.diario_classe_v1.modelos;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zip.fediverso.seu.diario_classe_v1.modelos.base.EntidadeBaseAbstrata;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "id_aluno"))
@Table(name="alunos")
public class Aluno extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 20, unique = true)
    private String matricula;
    
    @Column(nullable = false, length = 60)
    private String nome;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Inscricao> inscricoes = new HashSet<>();
}
