package unit;

import net.paoloambrosio.dssim.slowdown.ConstantSlowdownStrategy;
import net.paoloambrosio.dssim.slowdown.LinearSlowdownStrategy;
import net.paoloambrosio.dssim.slowdown.SlowdownStrategyFactory;
import org.junit.Test;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SlowdownStrategyFactoryTest {

    @Test
    public void descriptionMustBeProvided() {
        try {
            SlowdownStrategyFactory.fromDescription("");
            fail("it should have thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Strategy name required", e.getMessage());
        }
    }

    @Test
    public void buildsWithoutParameters() {
        ConstantSlowdownStrategy constantSlowdownStrategy = (ConstantSlowdownStrategy) SlowdownStrategyFactory.fromDescription("constant");
        assertEquals(Duration.ofSeconds(1), constantSlowdownStrategy.getSlowdown());
    }

    @Test
    public void buildsConstantSlowdownWithParameters() {
        ConstantSlowdownStrategy constantSlowdownStrategy = (ConstantSlowdownStrategy) SlowdownStrategyFactory.fromDescription("constant:3s");
        assertEquals(Duration.ofSeconds(3), constantSlowdownStrategy.getSlowdown());
    }

    @Test
    public void buildsLinearSlowdownWithParameters() {
        LinearSlowdownStrategy linearSlowdownStrategy = (LinearSlowdownStrategy) SlowdownStrategyFactory.fromDescription("linear:1s:15s");
        assertEquals(Duration.ofSeconds(1), linearSlowdownStrategy.getMaxSlowdown());
        assertEquals(15, linearSlowdownStrategy.getLoadRecorder().getWindowSize());
    }
}