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

import zip.fediverso.seu.diario_classe_v1.negocio.diario.*;
import zip.fediverso.seu.diario_classe_v1.utils.negocio.UUIDv7;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@ComponentScan(basePackages = "zip.fediverso.seu.diario_classe_v1")
class DiarioServicoTests {

    @SpyBean
    private DiarioRepositorio diarioRepositorio;

    @Autowired
    private DiarioMapper diarioMapper;

    @Autowired
    private DiarioServico diarioServico;

    private DiarioDto diarioDto1;
    private DiarioDto diarioRepetidoDto;
    private DiarioDto diarioDto2;

    private UUID uuid1;

    @BeforeEach
    void setUp() {
        uuid1 = UUIDv7.randomUUID();

        diarioDto1 = DiarioDto.builder().curso("Matemática").turma("A").componente("Álgebra").turno("Matutino").periodo("2021/1").build();
        diarioRepetidoDto = DiarioDto.builder().curso("Matemática").turma("A").componente("Álgebra").turno("Matutino").periodo("2021/1").build();
        diarioDto2 = DiarioDto.builder().curso("Física").turma("B").componente("Mecânica").turno("Vespertino").periodo("2021/2").build();
    }

    @Test
    void testObterTodosDiarios() {
        diarioRepositorio.saveAndFlush(diarioMapper.paraEntidade(diarioDto1));
        diarioRepositorio.saveAndFlush(diarioMapper.paraEntidade(diarioDto2));

        List<DiarioDto> diarios = diarioServico.obterTodosDiarios();

        assertEquals(2, diarios.size());
        assertEquals("Matemática", diarios.get(0).getCurso());
        assertEquals("Álgebra", diarios.get(0).getComponente());
        assertEquals("Física", diarios.get(1).getCurso());
        assertEquals("Mecânica", diarios.get(1).getComponente());
    }

    @Test
    void testObterDiarioPorId() {
        DiarioDto diarioSalvoDto = diarioServico.criarDiario(diarioDto1);

        DiarioDto resultado = diarioServico.obterDiarioPorId(diarioSalvoDto.getId());
        
        assertNotNull(resultado);
        assertEquals("Matemática", resultado.getCurso());
        assertEquals("Álgebra", resultado.getComponente());
    }

    @Test
    void testObterDiarioIdNaoExiste() {
        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.obterDiarioPorId(uuid1);
        });

        assertEquals("Diario não existe.", excecao.getMessage());
    }

    @Test
    void testDiarioDtoNull() {
        Exception excecao1 = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.criarDiario(null);
        });

        assertEquals("DiarioDto não pode ser NULL.", excecao1.getMessage());

        Exception excecao2 = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.atualizarDiario(null);
        });

        assertEquals("DiarioDto não pode ser NULL.", excecao2.getMessage());

        Exception excecao3 = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.deletarDiario(null);
        });

        assertEquals("DiarioDto não pode ser NULL.", excecao3.getMessage());
    }

    @Test
    void testCriarDiario() {
        DiarioDto diarioSalvoDto = diarioServico.criarDiario(diarioDto1);

        assertNotNull(diarioSalvoDto);
        assertEquals("Matemática", diarioSalvoDto.getCurso());
        assertEquals("Álgebra", diarioSalvoDto.getComponente());
    }

    @Test
    void testCriarDiarioIdJaExiste() {
        DiarioDto diarioSalvoDto = diarioServico.criarDiario(diarioDto1);

        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.criarDiario(diarioSalvoDto);
        });

        assertEquals(String.format("Diario já existe. ID: %s", diarioSalvoDto.getId()), excecao.getMessage());
    }

    @Test
    void testCriarDiarioIdNaoExiste() {
        diarioDto1.setId(uuid1);

        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.criarDiario(diarioDto1);
        });

        assertEquals("Diario não deve conter Id no ato de criação, pois este valor é gerado automaticamente.", excecao.getMessage());
    }

    @Test
    void testCriarDiarioIdJaExisteUniqueConstraint() {
        doThrow(new JpaSystemException(new RuntimeException("UNIQUE constraint failed"))).when(diarioRepositorio).saveAndFlush(any(DiarioEntidade.class));

        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.criarDiario(diarioDto1);
        });

        assertTrue(excecao.getMessage().contains("ID já existe"));
    }

    @Test
    void testAtualizarDiario() {
        DiarioDto diarioSalvoDto = diarioServico.criarDiario(diarioDto1);

        diarioSalvoDto.setComponente("Geometria");

        DiarioDto atualizado = diarioServico.atualizarDiario(diarioSalvoDto);

        assertNotNull(atualizado);
        assertEquals("Matemática", atualizado.getCurso());
        assertEquals("Geometria", atualizado.getComponente());
    }

    @Test
    void testAtualizarDiarioSemId() {
        diarioServico.criarDiario(diarioDto1); // Necessário para criação das tabelas

        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.atualizarDiario(diarioDto2);
        });

        assertEquals("Não é possível atualizar sem Id do Diario.", excecao.getMessage());
    }

    @Test
    void testAtualizarDiarioNaoExiste() {
        diarioServico.criarDiario(diarioDto1);

        diarioDto2.setId(uuid1);

        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.atualizarDiario(diarioDto2);
        });

        assertEquals("Diario não existe.", excecao.getMessage());
    }

    @Test
    void testAtualizarDiarioIdJaExisteUniqueConstraint() {
        DiarioDto diarioSalvoDto = diarioServico.criarDiario(diarioDto1);

        doThrow(new JpaSystemException(new RuntimeException("UNIQUE constraint failed"))).when(diarioRepositorio).saveAndFlush(any(DiarioEntidade.class));

        diarioSalvoDto.setComponente("Geometria");

        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.atualizarDiario(diarioSalvoDto);
        });

        assertTrue(excecao.getMessage().contains("ID já existe"));
    }

    @Test
    void testDeletarDiario() {
        DiarioDto diarioSalvoDto1 = diarioServico.criarDiario(diarioDto1);

        diarioServico.deletarDiario(diarioSalvoDto1);
        
        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.obterDiarioPorId(diarioSalvoDto1.getId());
        });

        assertEquals("Diario não existe.", excecao.getMessage());
    }

    @Test
    void testDeletarDiarioSemId() {
        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.deletarDiario(diarioDto1);
        });

        assertEquals("Não é possível deletar sem Id do Diario.", excecao.getMessage());
    }

    @Test
    void testDeletarDiarioNaoExiste() {
        diarioDto1.setId(uuid1);

        Exception excecao = assertThrows(DiarioExcecao.class, () -> {
            diarioServico.deletarDiario(diarioDto1);
        });

        assertEquals("Diario não existe.", excecao.getMessage());
    }

    @Test
    void testCriarDiarioGeraJpaSystemExceptionGenerica() {
        doThrow(new JpaSystemException(new RuntimeException("Erro genérico do JPA")))
                .when(diarioRepositorio).saveAndFlush(any(DiarioEntidade.class));

        JpaSystemException exception = assertThrows(JpaSystemException.class, () -> {
            diarioServico.criarDiario(diarioDto1);
        });

        assertTrue(exception.getMessage().contains("Erro genérico do JPA"));
    }

    @Test
    void testAlterarDiarioGeraJpaSystemExceptionGenerica() {
        DiarioDto diarioSalvoDto1 = diarioServico.criarDiario(diarioDto1);
        
        doThrow(new JpaSystemException(new RuntimeException("Erro genérico do JPA")))
                .when(diarioRepositorio).saveAndFlush(any(DiarioEntidade.class));

        JpaSystemException exception = assertThrows(JpaSystemException.class, () -> {
            diarioServico.atualizarDiario(diarioSalvoDto1);
        });

        assertTrue(exception.getMessage().contains("Erro genérico do JPA"));
    }

    @Test
    void testObterVinculosNaoImplementado() {
        Exception excecao = assertThrows(UnsupportedOperationException.class, () -> {
            diarioServico.obterVinculos();
        });

        assertEquals("Método não implementado", excecao.getMessage());
    }

    @Test
    void testObterEncontrosNaoImplementado() {
        Exception excecao = assertThrows(UnsupportedOperationException.class, () -> {
            diarioServico.obterEncontros();
        });

        assertEquals("Método não implementado", excecao.getMessage());
    }
}
