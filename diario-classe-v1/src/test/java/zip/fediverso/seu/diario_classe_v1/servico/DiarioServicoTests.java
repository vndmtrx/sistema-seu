package zip.fediverso.seu.diario_classe_v1.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import zip.fediverso.seu.diario_classe_v1.dominio.Aluno;
import zip.fediverso.seu.diario_classe_v1.dominio.Diario;
import zip.fediverso.seu.diario_classe_v1.dominio.Vinculo;
import zip.fediverso.seu.diario_classe_v1.repositorio.AlunoRepositorio;
import zip.fediverso.seu.diario_classe_v1.repositorio.DiarioRepositorio;
import zip.fediverso.seu.diario_classe_v1.repositorio.VinculoRepositorio;
import zip.fediverso.seu.diario_classe_v1.servico.dto.DiarioDto;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DiarioServicoTests {

    @Mock
    private DiarioRepositorio diarioRepositorio;

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @Mock
    private VinculoRepositorio vinculoRepositorio;

    @InjectMocks
    private DiarioServico diarioServico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterTodosDiarios() {
        Diario diario1 = new Diario();
        diario1.setCurso("Curso A");
        diario1.setTurma("Turma A");
        diario1.setComponente("Componente A");
        diario1.setTurno("Manhã");
        diario1.setPeriodo("2021.1");

        Diario diario2 = new Diario();
        diario2.setCurso("Curso B");
        diario2.setTurma("Turma B");
        diario2.setComponente("Componente B");
        diario2.setTurno("Tarde");
        diario2.setPeriodo("2021.2");

        when(diarioRepositorio.findAll()).thenReturn(Arrays.asList(diario1, diario2));

        List<DiarioDto> diarios = diarioServico.obterTodosDiarios();

        assertEquals(2, diarios.size());
        assertEquals("Curso A", diarios.get(0).getCurso());
        assertEquals("Turma A", diarios.get(0).getTurma());
        assertEquals("Curso B", diarios.get(1).getCurso());
        assertEquals("Turma B", diarios.get(1).getTurma());
    }

    @Test
    void testObterDiarioPorId() {
        Diario diario = new Diario();
        diario.setId(1L);
        diario.setCurso("Curso A");
        diario.setTurma("Turma A");
        diario.setComponente("Componente A");
        diario.setTurno("Manhã");
        diario.setPeriodo("2021.1");

        when(diarioRepositorio.findById(1L)).thenReturn(Optional.of(diario));

        DiarioDto diarioDTO = diarioServico.obterDiarioPorId(1L);

        assertNotNull(diarioDTO);
        assertEquals("Curso A", diarioDTO.getCurso());
        assertEquals("Turma A", diarioDTO.getTurma());
    }

    @Test
    void testCriarDiario() {
        DiarioDto diarioDTO = new DiarioDto();
        diarioDTO.setCurso("Curso A");
        diarioDTO.setTurma("Turma A");
        diarioDTO.setComponente("Componente A");
        diarioDTO.setTurno("Manhã");
        diarioDTO.setPeriodo("2021.1");

        Diario diario = new Diario();
        diario.setCurso("Curso A");
        diario.setTurma("Turma A");
        diario.setComponente("Componente A");
        diario.setTurno("Manhã");
        diario.setPeriodo("2021.1");

        when(diarioRepositorio.save(any(Diario.class))).thenReturn(diario);

        DiarioDto criado = diarioServico.criarDiario(diarioDTO);

        assertNotNull(criado);
        assertEquals("Curso A", criado.getCurso());
        assertEquals("Turma A", criado.getTurma());
    }

    @Test
    void testAtualizarDiario() {
        DiarioDto diarioDTO = new DiarioDto();
        diarioDTO.setCurso("Curso A");
        diarioDTO.setTurma("Turma A Atualizada");
        diarioDTO.setComponente("Componente A");
        diarioDTO.setTurno("Manhã");
        diarioDTO.setPeriodo("2021.1");

        Diario diario = new Diario();
        diario.setId(1L);
        diario.setCurso("Curso A");
        diario.setTurma("Turma A");
        diario.setComponente("Componente A");
        diario.setTurno("Manhã");
        diario.setPeriodo("2021.1");

        Diario diarioAtualizado = new Diario();
        diarioAtualizado.setId(1L);
        diarioAtualizado.setCurso("Curso A");
        diarioAtualizado.setTurma("Turma A Atualizada");
        diarioAtualizado.setComponente("Componente A");
        diarioAtualizado.setTurno("Manhã");
        diarioAtualizado.setPeriodo("2021.1");

        when(diarioRepositorio.existsById(1L)).thenReturn(true);
        when(diarioRepositorio.saveAndFlush(any(Diario.class))).thenReturn(diarioAtualizado);

        DiarioDto atualizado = diarioServico.atualizarDiario(1L, diarioDTO);

        assertNotNull(atualizado);
        assertEquals("Curso A", atualizado.getCurso());
        assertEquals("Turma A Atualizada", atualizado.getTurma());
    }

    @Test
    void testAtualizarDiarioNaoExistente() {
        DiarioDto diarioDTO = new DiarioDto();
        diarioDTO.setCurso("Curso A");
        diarioDTO.setTurma("Turma A Atualizada");
        diarioDTO.setComponente("Componente A");
        diarioDTO.setTurno("Manhã");
        diarioDTO.setPeriodo("2021.1");

        when(diarioRepositorio.existsById(1L)).thenReturn(false);

        DiarioDto atualizado = diarioServico.atualizarDiario(1L, diarioDTO);

        assertNull(atualizado);
        verify(diarioRepositorio, never()).saveAndFlush(any(Diario.class));
    }

    @Test
    void testDeletarDiario() {
        doNothing().when(diarioRepositorio).deleteById(1L);

        diarioServico.deletarDiario(1L);

        verify(diarioRepositorio, times(1)).deleteById(1L);
    }

    @Test
    void testAdicionarAluno() {
        Diario diario = new Diario();
        diario.setId(1L);
        diario.setCurso("Curso A");

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João Silva");

        when(diarioRepositorio.findById(1L)).thenReturn(Optional.of(diario));
        when(alunoRepositorio.findById(1L)).thenReturn(Optional.of(aluno));
        when(vinculoRepositorio.save(any(Vinculo.class))).thenReturn(new Vinculo());

        diarioServico.adicionarAluno(1L, 1L);

        verify(vinculoRepositorio, times(1)).save(any(Vinculo.class));
    }

    @Test
    void testAdicionarAlunoDiarioOuAlunoNaoExistente() {
        when(diarioRepositorio.findById(1L)).thenReturn(Optional.empty());
        when(alunoRepositorio.findById(1L)).thenReturn(Optional.empty());

        diarioServico.adicionarAluno(1L, 1L);

        verify(vinculoRepositorio, never()).save(any(Vinculo.class));

        Diario diario = new Diario();
        diario.setId(1L);
        diario.setCurso("Curso A");

        when(diarioRepositorio.findById(1L)).thenReturn(Optional.of(diario));
        when(alunoRepositorio.findById(1L)).thenReturn(Optional.empty());

        diarioServico.adicionarAluno(1L, 1L);

        verify(vinculoRepositorio, never()).save(any(Vinculo.class));
    }

    @Test
    void testRemoverAluno() {
        Vinculo vinculo = new Vinculo();
        vinculo.setId(1L);

        when(vinculoRepositorio.buscaPorIdAlunoEIdDiario(1L, 1L)).thenReturn(Optional.of(vinculo));
        doNothing().when(vinculoRepositorio).delete(vinculo);

        diarioServico.removerAluno(1L, 1L);

        verify(vinculoRepositorio, times(1)).delete(vinculo);
    }
}
