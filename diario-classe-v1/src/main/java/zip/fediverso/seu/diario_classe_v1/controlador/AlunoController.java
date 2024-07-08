package zip.fediverso.seu.diario_classe_v1.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        return "negocio/alunos/lista";
    }

    @GetMapping("/novo")
    public String exibirFormularioDeCriacao(Model model) {
        model.addAttribute("aluno", new AlunoDto());
        return "negocio/alunos/formulario";
    }

    @PostMapping
        public String criarAluno(@Valid @ModelAttribute("aluno") AlunoDto alunoDto, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("aluno", alunoDto);
            return "negocio/alunos/formulario";
        }

        try {
            alunoServico.criarAluno(alunoDto);
            return "redirect:/alunos";
        } catch (AlunoExcecao e) {
            model.addAttribute("aluno", alunoDto);
            model.addAttribute("erro", e.getMessage());
            return "negocio/alunos/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioDeEdicao(@PathVariable UUID id, @RequestParam(name = "referer", required = false) String referer, Model model) {
        AlunoDto aluno = alunoServico.obterAlunoPorId(id);
        model.addAttribute("aluno", aluno);
        model.addAttribute("referer", referer);
        return "negocio/alunos/formulario";
    }

    @PostMapping("/editar")
    public String atualizarAluno(@Valid @ModelAttribute("aluno") AlunoDto alunoDto, BindingResult br, @RequestParam(name = "referer", required = false) String referer, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("aluno", alunoDto);
            model.addAttribute("referer", referer);
            return "negocio/alunos/formulario";
        }

        try {
            alunoServico.atualizarAluno(alunoDto);
            if ("detalhes".equals(referer)) {
                return "redirect:/alunos/detalhes/" + alunoDto.getId();
            }
            return "redirect:/alunos";
        } catch (AlunoExcecao e) {
            model.addAttribute("aluno", alunoDto);
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("referer", referer);
            return "negocio/alunos/formulario";
        }
    }

    @GetMapping("/detalhes/{id}")
    public String exibirDetalhesDoAluno(@PathVariable UUID id, Model model) {
        AlunoDto aluno = alunoServico.obterAlunoPorId(id);
        model.addAttribute("aluno", aluno);
        return "negocio/alunos/detalhes";
    }

    @GetMapping("/remover/{id}")
    public String removerAluno(@PathVariable UUID id) {
        AlunoDto alunoDto = new AlunoDto();
        alunoDto.setId(id);
        alunoServico.deletarAluno(alunoDto);
        return "redirect:/alunos";
    }
}
