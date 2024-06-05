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

/**
 * Repositório para a entidade Frequencia.
 * <p>
 * Fornece métodos para interagir com os dados de frequências no banco de dados.
 */
@Repository
public interface FrequenciaRepositorio extends JpaRepository<Frequencia, Long> {

    /**
     * Consulta para encontrar frequências associadas a um encontro específico.
     *
     * @param encontro O encontro para o qual as frequências devem ser encontradas.
     * @return Lista de frequências associadas ao encontro especificado.
     */
    @Query("SELECT f FROM Frequencia AS f WHERE f.encontro = :encontro")
    List<Frequencia> buscaPorEncontro(@Param("encontro") Encontro encontro);

    /**
     * Consulta para encontrar frequências associadas a um encontro específico e de um tipo específico.
     *
     * @param encontro O encontro para o qual as frequências devem ser encontradas.
     * @param tipo O tipo de frequência a ser encontrado.
     * @return Lista de frequências associadas ao encontro e tipo especificados.
     */
    @Query("SELECT f FROM Frequencia AS f WHERE f.encontro = :encontro AND f.tipoFrequencia = :tipo")
    List<Frequencia> buscaPorEncontroETipoFrequencia(@Param("encontro") Encontro encontro, @Param("tipo") TipoFrequencia tipo);

    /**
     * Consulta para encontrar frequências associadas a um aluno específico.
     *
     * @param aluno O aluno para o qual as frequências devem ser encontradas.
     * @return Lista de frequências associadas ao aluno especificado.
     */
    @Query("SELECT f FROM Frequencia AS f WHERE f.aluno = :aluno")
    List<Frequencia> buscaPorAluno(@Param("aluno") Aluno aluno);

    /**
     * Consulta para encontrar frequências associadas a um aluno específico e de um tipo específico.
     *
     * @param aluno O aluno para o qual as frequências devem ser encontradas.
     * @param tipo O tipo de frequência a ser encontrado.
     * @return Lista de frequências associadas ao aluno e tipo especificados.
     */
    @Query("SELECT f FROM Frequencia AS f WHERE f.aluno = :aluno AND f.tipoFrequencia = :tipo")
    List<Frequencia> buscaPorAlunoETipoFrequencia(@Param("aluno") Aluno aluno, @Param("tipo") TipoFrequencia tipo);
}

