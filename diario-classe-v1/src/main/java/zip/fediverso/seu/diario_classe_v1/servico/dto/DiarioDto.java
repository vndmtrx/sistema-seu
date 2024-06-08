package zip.fediverso.seu.diario_classe_v1.servico.dto;

import lombok.Data;

/**
 * Data Transfer Object para Diario.
 */
@Data
public class DiarioDto {
    
    private Long id;
    private String curso;
    private String turma;
    private String componente;
    private String turno;
    private String periodo;
}
