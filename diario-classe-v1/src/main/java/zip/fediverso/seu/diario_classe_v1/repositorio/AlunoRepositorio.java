package zip.fediverso.seu.diario_classe_v1.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.dominio.Aluno;
import zip.fediverso.seu.diario_classe_v1.dominio.Diario;

/**
 * Repositório para a entidade Aluno.
 * <p>
 * Fornece métodos para interagir com os dados de alunos no banco de dados.
 */
@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {

    /**
     * Consulta para encontrar alunos associados a um diário específico.
     *
     * @param diario O diário para o qual os alunos devem ser encontrados.
     * @return Lista de alunos associados ao diário especificado.
     */
    @Query("SELECT a FROM Aluno AS a JOIN a.vinculos AS v WHERE v.diario = :diario")
    List<Aluno> buscaAlunosPorDiario(@Param("diario") Diario diario);
}
