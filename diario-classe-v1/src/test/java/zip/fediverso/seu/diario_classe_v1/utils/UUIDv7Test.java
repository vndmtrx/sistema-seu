package zip.fediverso.seu.diario_classe_v1.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import zip.fediverso.seu.diario_classe_v1.utils.negocio.UUIDv7;

@ActiveProfiles("test")
public class UUIDv7Test {
    private static final int ITERATIONS = 10;

    private long getProcessCpuTime() {
        OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
        if (bean instanceof com.sun.management.OperatingSystemMXBean) {
            return ((com.sun.management.OperatingSystemMXBean) bean).getProcessCpuTime();
        }
        return 0;
    }

    @Test
    public void testRandomBytes() {
        Instant start = Instant.now();
        long initialCpuTime = getProcessCpuTime();
        for (int i = 0; i < ITERATIONS; i++) {
            UUID valor = UUIDv7.randomUUID();
            System.out.println("- UUID 1: " + valor);
        }
        long finalCpuTime = getProcessCpuTime();
        Instant end = Instant.now();
        long durationMillis = Duration.between(start, end).toMillis();
        long cpuTimeMillis = (finalCpuTime - initialCpuTime) / 1_000_000;

        System.out.println("randomBytes: Tempo total (ms) = " + durationMillis);
        System.out.println("randomBytes: Tempo de CPU (ms) = " + cpuTimeMillis);

        assertTrue(true);
    }
}

