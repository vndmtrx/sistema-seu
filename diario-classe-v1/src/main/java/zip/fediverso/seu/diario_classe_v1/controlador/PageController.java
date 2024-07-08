package zip.fediverso.seu.diario_classe_v1.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para a página inicial do projeto.
 * Mapeia a URL base ("/") para o template "index".
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Bem-vindo ao Diário de Classe!");
        return "index";
    }
}
