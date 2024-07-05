package zip.fediverso.seu.diario_classe_v1.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import zip.fediverso.seu.diario_classe_v1.negocio.aluno.*;
import zip.fediverso.seu.diario_classe_v1.utils.negocio.UUIDv7;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@ComponentScan(basePackages = "zip.fediverso.seu.diario_classe_v1")
class AlunoServicoTests {

    @SpyBean
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoServico alunoServico;

    private AlunoDto alunoDto1;
    private AlunoDto alunoRepetidoDto;
    private AlunoDto alunoDto2;

    private UUID uuid1;

    @BeforeEach
    void setUp() {
        uuid1 = UUIDv7.randomUUID();

        alunoDto1 = AlunoDto.builder().matricula("20210001").nome("João Silva").build();
        alunoRepetidoDto = AlunoDto.builder().matricula("20210001").nome("Jorge Amado").build();
        alunoDto2 = AlunoDto.builder().matricula("20210002").nome("Maria Oliveira").build();
    }

    @Test
    void testObterTodosAlunos() {
        alunoRepositorio.saveAndFlush(alunoMapper.paraEntidade(alunoDto1));
        alunoRepositorio.saveAndFlush(alunoMapper.paraEntidade(alunoDto2));

        List<AlunoDto> alunos = alunoServico.obterTodosAlunos();

        assertEquals(2, alunos.size());
        assertEquals("20210001", alunos.get(0).getMatricula());
        assertEquals("João Silva", alunos.get(0).getNome());
        assertEquals("20210002", alunos.get(1).getMatricula());
        assertEquals("Maria Oliveira", alunos.get(1).getNome());
    }

    @Test
    void testObterAlunoPorId() {
        AlunoDto alunoSalvoDto = alunoServico.criarAluno(alunoDto1);

        AlunoDto resultado = alunoServico.obterAlunoPorId(alunoSalvoDto.getId());
        
        assertNotNull(resultado);
        assertEquals("20210001", resultado.getMatricula());
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    void testObterAlunoIdNaoExiste() {
        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.obterAlunoPorId(uuid1);
        });

        assertEquals("Aluno não existe.", excecao.getMessage());
    }

    @Test
    void testObterAlunoPorMatricula() {
        alunoServico.criarAluno(alunoDto1);
        
        AlunoDto resultado = alunoServico.obterAlunoPorMatricula(alunoDto1.getMatricula());

        assertNotNull(resultado);
        assertEquals("20210001", resultado.getMatricula());
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    void testObterAlunoMatriculaNaoExiste() {
        alunoServico.criarAluno(alunoDto1);
                    
        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.obterAlunoPorMatricula(alunoDto2.getMatricula());
        });

        assertEquals("Matrícula não existe.", excecao.getMessage());
    }

    @Test
    void testAlunoDtoNull() {
        Exception excecao1 = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.criarAluno(null);
        });

        assertEquals("AlunoDto não pode ser NULL.", excecao1.getMessage());

        Exception excecao2 = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.atualizarAluno(null);
        });

        assertEquals("AlunoDto não pode ser NULL.", excecao2.getMessage());

        Exception excecao3 = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.deletarAluno(null);
        });

        assertEquals("AlunoDto não pode ser NULL.", excecao3.getMessage());
    }

    @Test
    void testCriarAluno() {
        AlunoDto alunoSalvoDto = alunoServico.criarAluno(alunoDto1);

        assertNotNull(alunoSalvoDto);
        assertEquals("20210001", alunoSalvoDto.getMatricula());
        assertEquals("João Silva", alunoSalvoDto.getNome());
    }

    @Test
    void testCriarAlunoIdJaExiste() {
        AlunoDto alunoSalvoDto = alunoServico.criarAluno(alunoDto1);

        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.criarAluno(alunoSalvoDto);
        });

        assertEquals(String.format("Aluno já existe. ID: %s", alunoSalvoDto.getId()), excecao.getMessage());
    }

    @Test
    void testCriarAlunoIdNaoExiste() {
        alunoDto1.setId(uuid1);

        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.criarAluno(alunoDto1);
        });

        assertEquals("Aluno não deve conter Id no ato de criação, pois este valor é gerado automaticamente.", excecao.getMessage());
    }

    @Test
    void testCriarAlunoMesmaMatricula() {
        alunoServico.criarAluno(alunoDto1);

        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.criarAluno(alunoRepetidoDto);
        });

        assertEquals(String.format("Matrícula já existe: %s", alunoDto1.getMatricula()), excecao.getMessage());
    }

    @Test
    void testAtualizarAluno() {
        AlunoDto alunoSalvoDto = alunoServico.criarAluno(alunoDto1);

        alunoSalvoDto.setNome("João Silva Atualizado");

        AlunoDto atualizado = alunoServico.atualizarAluno(alunoSalvoDto);

        assertNotNull(atualizado);
        assertEquals("20210001", atualizado.getMatricula());
        assertEquals("João Silva Atualizado", atualizado.getNome());
    }

    @Test
    void testAtualizarAlunoSemId() {
        alunoServico.criarAluno(alunoDto1); // Necessário para criação das tabelas

        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.atualizarAluno(alunoDto2);
        });

        assertEquals("Não é possível atualizar sem Id do Aluno.", excecao.getMessage());
    }

    @Test
    void testAtualizarAlunoNaoExiste() {
        alunoServico.criarAluno(alunoDto1);

        alunoDto2.setId(uuid1);

        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.atualizarAluno(alunoDto2);
        });

        assertEquals("Aluno não existe.", excecao.getMessage());
    }

    @Test
    void testAtualizarAlunoMesmaMatricula() {
        AlunoDto alunoSalvoDto1 = alunoServico.criarAluno(alunoDto1);
        AlunoDto alunoSalvoDto2 = alunoServico.criarAluno(alunoDto2);
        
        alunoSalvoDto2.setMatricula(alunoSalvoDto1.getMatricula());

        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.atualizarAluno(alunoSalvoDto2);
        });

        assertEquals(String.format("Matrícula já existe: %s", alunoDto1.getMatricula()), excecao.getMessage());
    }

    @Test
    void testDeletarAluno() {
        AlunoDto alunoSalvoDto1 = alunoServico.criarAluno(alunoDto1);

        alunoServico.deletarAluno(alunoSalvoDto1);
        
        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.obterAlunoPorId(alunoSalvoDto1.getId());
        });

        assertEquals("Aluno não existe.", excecao.getMessage());
    }

    @Test
    void testDeletarAlunoSemId() {
        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.deletarAluno(alunoDto1);
        });

        assertEquals("Não é possível deletar sem Id do Aluno.", excecao.getMessage());
    }

    @Test
    void testDeletarAlunoNaoExiste() {
        alunoDto1.setId(uuid1);

        Exception excecao = assertThrows(AlunoExcecao.class, () -> {
            alunoServico.deletarAluno(alunoDto1);
        });

        assertEquals("Aluno não existe.", excecao.getMessage());
    }

    @Test
    void testCriarAlunoGeraJpaSystemExceptionGenerica() {
        doThrow(new JpaSystemException(new RuntimeException("Erro genérico do JPA")))
                .when(alunoRepositorio).saveAndFlush(any(AlunoEntidade.class));

        JpaSystemException exception = assertThrows(JpaSystemException.class, () -> {
            alunoServico.criarAluno(alunoDto1);
        });

        assertTrue(exception.getMessage().contains("Erro genérico do JPA"));
    }

    @Test
    void testAlterarAlunoGeraJpaSystemExceptionGenerica() {
        AlunoDto alunoSalvoDto1 = alunoServico.criarAluno(alunoDto1);
        
        doThrow(new JpaSystemException(new RuntimeException("Erro genérico do JPA")))
                .when(alunoRepositorio).saveAndFlush(any(AlunoEntidade.class));

        JpaSystemException exception = assertThrows(JpaSystemException.class, () -> {
            alunoServico.atualizarAluno(alunoSalvoDto1);
        });

        assertTrue(exception.getMessage().contains("Erro genérico do JPA"));
    }
}
