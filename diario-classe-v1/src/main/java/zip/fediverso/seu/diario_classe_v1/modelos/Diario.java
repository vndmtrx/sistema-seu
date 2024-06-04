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
@AttributeOverride(name = "id", column = @Column(name = "id_diario"))
@Table(name="diarios")
public class Diario extends EntidadeBaseAbstrata {
    private static final long serialVersionUID = 1L;

    @Column(length = 30)
    private String curso;

    @Column(length = 30)
    private String turma;

    @Column(length = 30)
    private String componente;

    @Column(length = 30)
    private String turno;

    @Column(length = 30)
    private String periodo;

    @OneToMany(mappedBy = "diario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Inscricao> inscricoes = new HashSet<>();

    @OneToMany(mappedBy = "diario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Encontro> encontros = new HashSet<>();
}
