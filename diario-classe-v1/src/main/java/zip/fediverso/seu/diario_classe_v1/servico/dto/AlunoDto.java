package zip.fediverso.seu.diario_classe_v1.servico.dto;

import lombok.Data;

/**
 * Data Transfer Object para Aluno.
 */
@Data
public class AlunoDto {

    private Long id;
    private String matricula;
    private String nome;
}
