package zip.fediverso.seu.diario_classe_v1.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.modelos.Aluno;
import zip.fediverso.seu.diario_classe_v1.modelos.Encontro;
import zip.fediverso.seu.diario_classe_v1.modelos.Frequencia;
import zip.fediverso.seu.diario_classe_v1.modelos.enums.TipoFrequencia;

@Repository
public interface FrequenciaRepositorio extends JpaRepository<Frequencia, Long> {
    List<Frequencia> findByEncontro(Encontro encontro);

    List<Frequencia> findByEncontroAndTipoFrequencia(Encontro encontro, TipoFrequencia tipo);

    List<Frequencia> findByAluno(Aluno aluno);

    List<Frequencia> findByAlunoAndTipoFrequencia(Aluno aluno, TipoFrequencia tipo);
}
