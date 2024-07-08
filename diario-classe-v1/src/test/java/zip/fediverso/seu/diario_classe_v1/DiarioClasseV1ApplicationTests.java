package zip.fediverso.seu.diario_classe_v1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiarioClasseV1ApplicationTests {

    @Test
    void main() {
        assertDoesNotThrow(() -> DiarioClasseV1Application.main(new String[] {}));
    }
}
