package zip.fediverso.seu.diario_classe_v1.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.modelos.Vinculo;

@Repository
public interface VinculoRepositorio extends JpaRepository<Vinculo, Long> {
    
}

