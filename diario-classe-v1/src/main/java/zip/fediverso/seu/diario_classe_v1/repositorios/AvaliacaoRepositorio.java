package zip.fediverso.seu.diario_classe_v1.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.modelos.Aluno;
import zip.fediverso.seu.diario_classe_v1.modelos.Avaliacao;
import zip.fediverso.seu.diario_classe_v1.modelos.Encontro;
import zip.fediverso.seu.diario_classe_v1.modelos.enums.TipoAvaliacao;

@Repository
public interface AvaliacaoRepositorio extends JpaRepository<Avaliacao, Long> {

    @Query("SELECT a FROM Avaliacao AS a WHERE a.encontro = :encontro")
    List<Avaliacao> buscaPorEncontro(@Param("encontro") Encontro encontro);

    @Query("SELECT a FROM Avaliacao AS a WHERE a.encontro = :encontro AND a.tipoAvaliacao = :tipo")
    List<Avaliacao> buscaPorEncontroETipoAvaliacao(@Param("encontro") Encontro encontro, @Param("tipo") TipoAvaliacao tipo);

    @Query("SELECT a FROM Avaliacao AS a WHERE a.aluno = :aluno")
    List<Avaliacao> buscaPorAluno(@Param("aluno") Aluno aluno);

    @Query("SELECT a FROM Avaliacao AS a WHERE a.aluno = :aluno AND a.tipoAvaliacao = :tipo")
    List<Avaliacao> buscaPorAlunoETipoAvaliacao(@Param("aluno") Aluno aluno, @Param("tipo") TipoAvaliacao tipo);
}