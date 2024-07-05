package zip.fediverso.seu.diario_classe_v1.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import zip.fediverso.seu.diario_classe_v1.negocio.aluno.AlunoDto;
import zip.fediverso.seu.diario_classe_v1.negocio.aluno.AlunoExcecao;
import zip.fediverso.seu.diario_classe_v1.negocio.aluno.AlunoServico;

/**
 * Controlador para gerenciar operações relacionadas aos alunos.
 */
@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoServico alunoServico;

    @GetMapping
    public String listarAlunos(Model model) {
        List<AlunoDto> alunos = alunoServico.obterTodosAlunos();
        model.addAttribute("alunos", alunos);
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String exibirFormularioDeCriacao(Model model) {
        model.addAttribute("aluno", new AlunoDto());
        return "alunos/formulario";
    }

    @PostMapping
    public String criarAluno(@Valid @ModelAttribute("alunoDto") AlunoDto alunoDto, Model model) {
        try {
            alunoServico.criarAluno(alunoDto);
            return "redirect:/alunos";
        } catch (AlunoExcecao e) {
            model.addAttribute("aluno", alunoDto);
            model.addAttribute("erro", e.getMessage());
            return "alunos/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioDeEdicao(@PathVariable UUID id, Model model) {
        AlunoDto aluno = alunoServico.obterAlunoPorId(id);
        model.addAttribute("aluno", aluno);
        return "alunos/formulario";
    }

    @PostMapping("/editar")
    public String atualizarAluno(@ModelAttribute AlunoDto alunoDto, Model model) {
        AlunoDto alunoAtualizado = alunoServico.atualizarAluno(alunoDto);
        if (alunoAtualizado == null) {
            model.addAttribute("erro", "Aluno não encontrado.");
            return "alunos/formulario";
        }
        return "redirect:/alunos";
    }

    @GetMapping("/remover/{id}")
    public String removerAluno(@PathVariable UUID id) {
        AlunoDto alunoDto = new AlunoDto();
        alunoDto.setId(id);
        alunoServico.deletarAluno(alunoDto);
        return "redirect:/alunos";
    }
}
