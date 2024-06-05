package zip.fediverso.seu.diario_classe_v1.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.modelos.Diario;
import zip.fediverso.seu.diario_classe_v1.modelos.Encontro;

@Repository
public interface EncontroRepositorio extends JpaRepository<Encontro, Long> {
    
    // Consulta para encontrar encontros associados a um diário específico
    @Query("SELECT e FROM Encontro AS e WHERE e.diario = :diario")
    List<Encontro> buscaEncontrosPorDiario(@Param("diario") Diario diario);
}
