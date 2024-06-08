package zip.fediverso.seu.diario_classe_v1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiarioClasseV1ApplicationTests {

	@Test
    void contextLoads() {
        assertTrue(true);  // Verifica se o contexto Spring é carregado sem erros
    }

    @Test
    void main() {
        DiarioClasseV1Application.main(new String[] {});
        assertTrue(true);  // Verifica se a aplicação inicializa sem erros
    }

}
