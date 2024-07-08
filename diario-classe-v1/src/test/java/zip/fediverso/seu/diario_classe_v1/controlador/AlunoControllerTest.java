package zip.fediverso.seu.diario_classe_v1.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import zip.fediverso.seu.diario_classe_v1.negocio.aluno.AlunoDto;
import zip.fediverso.seu.diario_classe_v1.negocio.aluno.AlunoExcecao;
import zip.fediverso.seu.diario_classe_v1.negocio.aluno.AlunoServico;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoServico alunoServico;

    @Mock
    private BindingResult bindingResult;

    private AlunoDto alunoDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        alunoDto = new AlunoDto();
        alunoDto.setId(UUID.randomUUID());
        alunoDto.setMatricula("123456");
        alunoDto.setNome("Teste Aluno");
    }

    @Test
    public void testListarAlunos() throws Exception {
        List<AlunoDto> alunos = new ArrayList<>();
        alunos.add(alunoDto);

        when(alunoServico.obterTodosAlunos()).thenReturn(alunos);

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/lista"))
                .andExpect(model().attributeExists("alunos"))
                .andExpect(model().attribute("alunos", alunos));
    }

    @Test
    public void testExibirFormularioDeCriacao() throws Exception {
        mockMvc.perform(get("/alunos/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/formulario"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testExibirFormularioDeEdicao() throws Exception {
        when(alunoServico.obterAlunoPorId(any(UUID.class))).thenReturn(alunoDto);

        mockMvc.perform(get("/alunos/editar/" + alunoDto.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/formulario"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().attribute("aluno", alunoDto));
    }

    @Test
    public void testExibirDetalhesDoAluno() throws Exception {
        when(alunoServico.obterAlunoPorId(any(UUID.class))).thenReturn(alunoDto);

        mockMvc.perform(get("/alunos/detalhes/" + alunoDto.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/detalhes"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().attribute("aluno", alunoDto));
    }

    @Test
    public void testCriarAlunoComExcecao() throws Exception {
        when(alunoServico.criarAluno(any(AlunoDto.class))).thenThrow(new AlunoExcecao("Erro ao criar aluno", null));

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("matricula", "123456")
                .param("nome", "Teste Aluno"))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/formulario"))
                .andExpect(model().attributeExists("erro"))
                .andExpect(model().attribute("erro", "Erro ao criar aluno"));
    }

    @Test
    public void testAtualizarAlunoComExcecao() throws Exception {
        when(alunoServico.atualizarAluno(any(AlunoDto.class))).thenThrow(new AlunoExcecao("Erro ao atualizar aluno", null));

        mockMvc.perform(post("/alunos/editar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", alunoDto.getId().toString())
                .param("matricula", "123456")
                .param("nome", "Teste Aluno"))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/formulario"))
                .andExpect(model().attributeExists("erro"))
                .andExpect(model().attribute("erro", "Erro ao atualizar aluno"));
    }

    @Test
    public void testCriarAluno() throws Exception {
        when(alunoServico.criarAluno(any(AlunoDto.class))).thenReturn(alunoDto);

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("matricula", "123456")
                .param("nome", "Teste Aluno"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/alunos*"));
    }

    @Test
    public void testCriarAlunoComErroValidacao() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("matricula", "")
                .param("nome", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/formulario"));
    }

    @Test
    public void testAtualizarAluno() throws Exception {
        when(alunoServico.atualizarAluno(any(AlunoDto.class))).thenReturn(alunoDto);

        mockMvc.perform(post("/alunos/editar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", alunoDto.getId().toString())
                .param("matricula", "123456")
                .param("nome", "Teste Aluno"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/alunos*"));
    }

    @Test
    public void testAtualizarAlunoComErroValidacao() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/alunos/editar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", alunoDto.getId().toString())
                .param("matricula", "")
                .param("nome", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("negocio/alunos/formulario"));
    }

    @Test
    public void testAtualizarAlunoRedirecionaParaDetalhes() throws Exception {
        String referer = "detalhes";
        when(alunoServico.atualizarAluno(any(AlunoDto.class))).thenReturn(alunoDto);
    
        mockMvc.perform(post("/alunos/editar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", alunoDto.getId().toString())
                .param("matricula", "123456")
                .param("nome", "Teste Aluno")
                .param("referer", referer))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/alunos/detalhes/*"));
    }    

    @Test
    public void testRemoverAluno() throws Exception {
        doNothing().when(alunoServico).deletarAluno(any(AlunoDto.class));

        mockMvc.perform(get("/alunos/remover/" + alunoDto.getId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/alunos*"));
    }
}
