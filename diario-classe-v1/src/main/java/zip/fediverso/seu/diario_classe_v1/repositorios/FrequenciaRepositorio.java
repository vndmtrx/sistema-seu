package zip.fediverso.seu.diario_classe_v1.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.modelos.Aluno;
import zip.fediverso.seu.diario_classe_v1.modelos.Encontro;
import zip.fediverso.seu.diario_classe_v1.modelos.Frequencia;
import zip.fediverso.seu.diario_classe_v1.modelos.enums.TipoFrequencia;

@Repository
public interface FrequenciaRepositorio extends JpaRepository<Frequencia, Long> {

    @Query("SELECT f FROM Frequencia AS f WHERE f.encontro = :encontro")
    List<Frequencia> buscaPorEncontro(@Param("encontro") Encontro encontro);

    @Query("SELECT f FROM Frequencia AS f WHERE f.encontro = :encontro AND f.tipoFrequencia = :tipo")
    List<Frequencia> buscaPorEncontroETipoFrequencia(@Param("encontro") Encontro encontro, @Param("tipo") TipoFrequencia tipo);

    @Query("SELECT f FROM Frequencia AS f WHERE f.aluno = :aluno")
    List<Frequencia> buscaPorAluno(@Param("aluno") Aluno aluno);

    @Query("SELECT f FROM Frequencia AS f WHERE f.aluno = :aluno AND f.tipoFrequencia = :tipo")
    List<Frequencia> buscaPorAlunoETipoFrequencia(@Param("aluno") Aluno aluno, @Param("tipo") TipoFrequencia tipo);
}

