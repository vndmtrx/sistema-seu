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
import zip.fediverso.seu.diario_classe_v1.repositorio.AlunoRepositorio;
import zip.fediverso.seu.diario_classe_v1.servico.dto.AlunoDto;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlunoServicoTests {

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @InjectMocks
    private AlunoServico alunoServico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterTodosAlunos() {
        Aluno aluno1 = new Aluno();
        aluno1.setMatricula("20210001");
        aluno1.setNome("João Silva");
        
        Aluno aluno2 = new Aluno();
        aluno2.setMatricula("20210002");
        aluno2.setNome("Maria Oliveira");
        
        when(alunoRepositorio.findAll()).thenReturn(Arrays.asList(aluno1, aluno2));
        
        List<AlunoDto> alunos = alunoServico.obterTodosAlunos();
        
        assertEquals(2, alunos.size());
        assertEquals("20210001", alunos.get(0).getMatricula());
        assertEquals("João Silva", alunos.get(0).getNome());
        assertEquals("20210002", alunos.get(1).getMatricula());
        assertEquals("Maria Oliveira", alunos.get(1).getNome());
    }

    @Test
    void testObterAlunoPorId() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setMatricula("20210001");
        aluno.setNome("João Silva");
        
        when(alunoRepositorio.findById(1L)).thenReturn(Optional.of(aluno));
        
        AlunoDto alunoDTO = alunoServico.obterAlunoPorId(1L);
        
        assertNotNull(alunoDTO);
        assertEquals("20210001", alunoDTO.getMatricula());
        assertEquals("João Silva", alunoDTO.getNome());
    }

    @Test
    void testCriarAluno() {
        AlunoDto alunoDTO = new AlunoDto();
        alunoDTO.setMatricula("20210001");
        alunoDTO.setNome("João Silva");
        
        Aluno aluno = new Aluno();
        aluno.setMatricula("20210001");
        aluno.setNome("João Silva");
        
        when(alunoRepositorio.saveAndFlush(any(Aluno.class))).thenReturn(aluno);
        
        AlunoDto criado = alunoServico.criarAluno(alunoDTO);
        
        assertNotNull(criado);
        assertEquals("20210001", criado.getMatricula());
        assertEquals("João Silva", criado.getNome());
    }

    @Test
    void testAtualizarAluno() {
        AlunoDto alunoDTO = new AlunoDto();
        alunoDTO.setMatricula("20210001");
        alunoDTO.setNome("João Silva Atualizado");

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setMatricula("20210001");
        aluno.setNome("João Silva");

        Aluno alunoAtualizado = new Aluno();
        alunoAtualizado.setId(1L);
        alunoAtualizado.setMatricula("20210001");
        alunoAtualizado.setNome("João Silva Atualizado");

        when(alunoRepositorio.existsById(1L)).thenReturn(true);
        when(alunoRepositorio.saveAndFlush(any(Aluno.class))).thenReturn(alunoAtualizado);
        
        AlunoDto atualizado = alunoServico.atualizarAluno(1L, alunoDTO);
        
        assertNotNull(atualizado);
        assertEquals("20210001", atualizado.getMatricula());
        assertEquals("João Silva Atualizado", atualizado.getNome());
    }

    @Test
    void testAtualizarAlunoNaoExistente() {
        AlunoDto alunoDTO = new AlunoDto();
        alunoDTO.setMatricula("20210001");
        alunoDTO.setNome("João Silva Atualizado");

        when(alunoRepositorio.existsById(1L)).thenReturn(false);

        AlunoDto atualizado = alunoServico.atualizarAluno(1L, alunoDTO);

        assertNull(atualizado);
        verify(alunoRepositorio, never()).saveAndFlush(any(Aluno.class));
    }

    @Test
    void testDeletarAluno() {
        doNothing().when(alunoRepositorio).deleteById(1L);
        
        alunoServico.deletarAluno(1L);
        
        verify(alunoRepositorio, times(1)).deleteById(1L);
    }

    @Test
    void testObterAlunoPorMatricula() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setMatricula("20210001");
        aluno.setNome("João Silva");

        when(alunoRepositorio.buscaPorMatricula("20210001")).thenReturn(Optional.of(aluno));
        
        AlunoDto alunoDTO = alunoServico.obterAlunoPorMatricula("20210001");

        assertNotNull(alunoDTO);
        assertEquals("20210001", alunoDTO.getMatricula());
        assertEquals("João Silva", alunoDTO.getNome());
    }
}
