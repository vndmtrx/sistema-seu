package zip.fediverso.seu.diario_classe_v1.negocio.diario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DiarioDto {
    private UUID id;

    private LocalDateTime criadoEm;
    private LocalDateTime alteradoEm;
    private Instant versao;
    
    @NotEmpty(message = "O nome do curso não pode estar vazio")
    @Size(min = 1, max = 30, message = "O campo curso deve ter entre 1 e 30 caracteres")
    private String curso;
    
    @NotEmpty(message = "A Turma não pode estar vazia")
    @Size(min = 1, max = 30, message = "O campo turma deve ter entre 1 e 30 caracteres")
    private String turma;
    
    @NotEmpty(message = "A componente não pode estar vazia")
    @Size(min = 1, max = 30, message = "O campo componente deve ter entre 1 e 30 caracteres")
    private String componente;
    
    @NotEmpty(message = "O turno não pode estar vazio")
    @Size(min = 1, max = 30, message = "O campo turno deve ter entre 1 e 30 caracteres")
    private String turno;
    
    @NotEmpty(message = "O período não pode estar vazio")
    @Size(min = 1, max = 30, message = "O campo período deve ter entre 1 e 30 caracteres")
    private String periodo;
}
