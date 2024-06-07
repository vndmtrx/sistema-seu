package zip.fediverso.seu.diario_classe_v1.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.dominio.Aluno;
import zip.fediverso.seu.diario_classe_v1.dominio.Avaliacao;
import zip.fediverso.seu.diario_classe_v1.dominio.Encontro;
import zip.fediverso.seu.diario_classe_v1.dominio.enums.TipoAvaliacao;

/**
 * Repositório para a entidade Avaliacao.
 * <p>
 * Fornece métodos para interagir com os dados de avaliações no banco de dados.
 */
@Repository
public interface AvaliacaoRepositorio extends JpaRepository<Avaliacao, Long> {

    /**
     * Consulta para encontrar avaliações associadas a um encontro específico.
     *
     * @param encontro O encontro para o qual as avaliações devem ser encontradas.
     * @return Lista de avaliações associadas ao encontro especificado.
     */
    @Query("SELECT a FROM Avaliacao AS a WHERE a.encontro = :encontro")
    List<Avaliacao> buscaPorEncontro(@Param("encontro") Encontro encontro);

    /**
     * Consulta para encontrar avaliações associadas a um encontro específico e de um tipo específico.
     *
     * @param encontro O encontro para o qual as avaliações devem ser encontradas.
     * @param tipo O tipo de avaliação a ser encontrado.
     * @return Lista de avaliações associadas ao encontro e tipo especificados.
     */
    @Query("SELECT a FROM Avaliacao AS a WHERE a.encontro = :encontro AND a.tipoAvaliacao = :tipo")
    List<Avaliacao> buscaPorEncontroETipoAvaliacao(@Param("encontro") Encontro encontro, @Param("tipo") TipoAvaliacao tipo);

    /**
     * Consulta para encontrar avaliações associadas a um aluno específico.
     *
     * @param aluno O aluno para o qual as avaliações devem ser encontradas.
     * @return Lista de avaliações associadas ao aluno especificado.
     */
    @Query("SELECT a FROM Avaliacao AS a WHERE a.aluno = :aluno")
    List<Avaliacao> buscaPorAluno(@Param("aluno") Aluno aluno);

    /**
     * Consulta para encontrar avaliações associadas a um aluno específico e de um tipo específico.
     *
     * @param aluno O aluno para o qual as avaliações devem ser encontradas.
     * @param tipo O tipo de avaliação a ser encontrado.
     * @return Lista de avaliações associadas ao aluno e tipo especificados.
     */
    @Query("SELECT a FROM Avaliacao AS a WHERE a.aluno = :aluno AND a.tipoAvaliacao = :tipo")
    List<Avaliacao> buscaPorAlunoETipoAvaliacao(@Param("aluno") Aluno aluno, @Param("tipo") TipoAvaliacao tipo);
}