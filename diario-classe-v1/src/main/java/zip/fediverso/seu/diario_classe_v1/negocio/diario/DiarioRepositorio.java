package zip.fediverso.seu.diario_classe_v1.negocio.diario;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.negocio.aluno.AlunoEntidade;

/**
 * Repositório para a entidade Diario.
 * <p>
 * Fornece métodos para interagir com os dados de diários no banco de dados.
 */
@Repository
public interface DiarioRepositorio extends JpaRepository<Diario, UUID> {

    /**
     * Consulta para encontrar diários associados a um aluno específico.
     *
     * @param aluno O aluno para o qual os diários devem ser encontrados.
     * @return Lista de diários associados ao aluno especificado.
     */
    @Query("SELECT d FROM Diario AS d JOIN d.vinculos AS v WHERE v.aluno = :aluno")
    List<Diario> buscaPorAluno(@Param("aluno") AlunoEntidade aluno);
}
