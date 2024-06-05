package zip.fediverso.seu.diario_classe_v1.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zip.fediverso.seu.diario_classe_v1.modelos.Diario;
import zip.fediverso.seu.diario_classe_v1.modelos.Encontro;

@Repository
public interface EncontroRepositorio extends JpaRepository<Encontro, Long> {
    List<Encontro> findByDiario(Diario diario);
}
