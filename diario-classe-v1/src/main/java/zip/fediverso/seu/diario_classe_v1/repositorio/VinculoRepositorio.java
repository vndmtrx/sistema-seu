package zip.fediverso.seu.diario_classe_v1.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.dominio.Vinculo;

/**
 * Repositório para a entidade Vinculo.
 * <p>
 * Fornece métodos para interagir com os dados de vínculos no banco de dados.
 */
@Repository
public interface VinculoRepositorio extends JpaRepository<Vinculo, Long> {
 /**
     * Encontra um vínculo pelo ID do aluno e pelo ID do diário.
     * 
     * @param alunoId ID do aluno.
     * @param diarioId ID do diário.
     * @return Optional contendo o vínculo encontrado, se existir.
     */
    @Query("SELECT v FROM Vinculo AS v WHERE v.aluno.id = :alunoId AND v.diario.id = :diarioId")
    Optional<Vinculo> buscaPorIdAlunoEIdDiario(@Param("alunoId") Long alunoId, @Param("diarioId") Long diarioId);
}
