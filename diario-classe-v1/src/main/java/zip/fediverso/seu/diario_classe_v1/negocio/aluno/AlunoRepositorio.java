package zip.fediverso.seu.diario_classe_v1.negocio.aluno;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Aluno.
 * <p>
 * Fornece métodos para interagir com os dados de alunos no banco de dados.
 */
@Repository
public interface AlunoRepositorio extends JpaRepository<AlunoEntidade, UUID> {

    /**
     * Busca um aluno pela sua matrícula.
     *
     * @param matricula A matrícula do aluno.
     * @return Um Optional contendo o aluno, se encontrado.
     */
    @Query("SELECT a FROM AlunoEntidade AS a WHERE a.matricula = :matricula")
    Optional<AlunoEntidade> buscaPorMatricula(@Param("matricula") String matricula);
}
