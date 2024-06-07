package zip.fediverso.seu.diario_classe_v1.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.dominio.Vinculo;

/**
 * Repositório para a entidade Vinculo.
 * <p>
 * Fornece métodos para interagir com os dados de vínculos no banco de dados.
 */
@Repository
public interface VinculoRepositorio extends JpaRepository<Vinculo, Long> {
    
}
