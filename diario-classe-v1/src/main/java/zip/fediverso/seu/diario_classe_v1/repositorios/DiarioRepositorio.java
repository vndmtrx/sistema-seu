package zip.fediverso.seu.diario_classe_v1.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.modelos.Aluno;
import zip.fediverso.seu.diario_classe_v1.modelos.Diario;

@Repository
public interface DiarioRepositorio extends JpaRepository<Diario, Long> {

    // Consulta para encontrar diários associados a um aluno específico
    @Query("SELECT d FROM Diario AS d JOIN d.vinculos AS v WHERE v.aluno = :aluno")
    List<Diario> buscaDiariosPorAluno(@Param("aluno") Aluno aluno);
}
