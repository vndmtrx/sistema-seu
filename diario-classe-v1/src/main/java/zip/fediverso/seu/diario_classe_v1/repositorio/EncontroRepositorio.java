package zip.fediverso.seu.diario_classe_v1.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.dominio.Diario;
import zip.fediverso.seu.diario_classe_v1.dominio.Encontro;

/**
 * Repositório para a entidade Encontro.
 * <p>
 * Fornece métodos para interagir com os dados de encontros no banco de dados.
 */
@Repository
public interface EncontroRepositorio extends JpaRepository<Encontro, Long> {
    
    /**
     * Consulta para encontrar encontros associados a um diário específico.
     *
     * @param diario O diário para o qual os encontros devem ser encontrados.
     * @return Lista de encontros associados ao diário especificado.
     */
    @Query("SELECT e FROM Encontro AS e WHERE e.diario = :diario")
    List<Encontro> buscaEncontrosPorDiario(@Param("diario") Diario diario);
}
