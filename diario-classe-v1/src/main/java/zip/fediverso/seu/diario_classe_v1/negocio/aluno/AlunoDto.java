package zip.fediverso.seu.diario_classe_v1.negocio.aluno;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AlunoDto {
    private UUID id;

    private LocalDateTime criadoEm;
    private LocalDateTime alteradoEm;
    private Instant versao;

    @NotEmpty(message = "A matrícula não pode estar vazia")
    @Size(min = 1, max = 20, message = "A matrícula deve ter entre 1 e 20 caracteres")
    private String matricula;
    
    @NotEmpty(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres")
    private String nome;

    @NotNull(message = "O status não pode ser nulo")
    private Boolean status;
}
