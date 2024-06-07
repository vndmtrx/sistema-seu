package zip.fediverso.seu.diario_classe_v1.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.dominio.Aluno;
import zip.fediverso.seu.diario_classe_v1.dominio.Diario;

/**
 * Repositório para a entidade Diario.
 * <p>
 * Fornece métodos para interagir com os dados de diários no banco de dados.
 */
@Repository
public interface DiarioRepositorio extends JpaRepository<Diario, Long> {

    /**
     * Consulta para encontrar diários associados a um aluno específico.
     *
     * @param aluno O aluno para o qual os diários devem ser encontrados.
     * @return Lista de diários associados ao aluno especificado.
     */
    @Query("SELECT d FROM Diario AS d JOIN d.vinculos AS v WHERE v.aluno = :aluno")
    List<Diario> buscaPorAluno(@Param("aluno") Aluno aluno);
}
