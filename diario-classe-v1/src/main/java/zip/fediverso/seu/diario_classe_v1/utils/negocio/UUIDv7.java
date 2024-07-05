package zip.fediverso.seu.diario_classe_v1.utils.negocio;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * Componente para gerar UUID versão 7 com base no timestamp atual e bytes aleatórios.
 * 
 */
@Component
public class UUIDv7 {
    private static final ThreadLocal<SecureRandom> randomThreadLocal = ThreadLocal.withInitial(SecureRandom::new);

    /**
     * Gera um UUID aleatório (versão 7) baseado no timestamp atual e bytes aleatórios.
     * 
     * @return um objeto UUID (versão 7).
     */
    public static UUID randomUUID() {
        byte[] valor = randomBytes();
        ByteBuffer buf = ByteBuffer.wrap(valor);
        long alto = buf.getLong();
        long baixo = buf.getLong();
        return new UUID(alto, baixo);
    }

    /**
     * Gera um array de bytes aleatórios com o timestamp atual e valores aleatórios.
     * 
     * @return um array de bytes de 16 bytes.
     */
    public static byte[] randomBytes() {
        byte[] valor = new byte[16];
        SecureRandom random = randomThreadLocal.get();
        random.nextBytes(valor);

        long timestamp = System.currentTimeMillis();

        // timestamp
        valor[0] = (byte) ((timestamp >> 40) & 0xFF);
        valor[1] = (byte) ((timestamp >> 32) & 0xFF);
        valor[2] = (byte) ((timestamp >> 24) & 0xFF);
        valor[3] = (byte) ((timestamp >> 16) & 0xFF);
        valor[4] = (byte) ((timestamp >>  8) & 0xFF);
        valor[5] = (byte)  (timestamp        & 0xFF);

        // Define a versão (UUID versão 7)
        valor[6] = (byte) ((valor[6] & 0x0F) | 0x70);

        // Define a variante (variante IETF)
        valor[8] = (byte) ((valor[8] & 0x3F) | 0x80);

        return valor;
    }
}
