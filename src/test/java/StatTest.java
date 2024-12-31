import org.example.Stat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatTest {

    @Test
    void testStatBaseValue() {
        Stat stat = new Stat(20);
        assertEquals(20.0, stat.getValue());
    }

    @Test
    void testStatRaisedValue() {
        Stat stat = new Stat(20);
        stat.raise();
        assertEquals(Math.round((20.0 * (3.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (4.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (5.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (6.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (7.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (8.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (8.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
    }

    @Test
    void testStatLoweredValue() {
        Stat stat = new Stat(20);
        stat.lower();
        assertEquals(Math.round((20.0 * (2.0 / 3.0)) * 100.0) / 100.0, stat.getValue());
        stat.lower();
        assertEquals(Math.round((20.0 * (2.0 / 4.0)) * 100.0) / 100.0, stat.getValue());
        stat.lower();
        assertEquals(Math.round((20.0 * (2.0 / 5.0)) * 100.0) / 100.0, stat.getValue());
        stat.lower();
        assertEquals(Math.round((20.0 * (2.0 / 6.0)) * 100.0) / 100.0, stat.getValue());
        stat.lower();
        assertEquals(Math.round((20.0 * (2.0 / 7.0)) * 100.0) / 100.0, stat.getValue());
        stat.lower();
        assertEquals(Math.round((20.0 * (2.0 / 8.0)) * 100.0) / 100.0, stat.getValue());
    }

    @Test
    void testStatModifiedValue() {
        Stat stat = new Stat(20);
        stat.lower();
        assertEquals(Math.round((20.0 * (2.0 / 3.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (3.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.raise();
        assertEquals(Math.round((20.0 * (4.0 / 2.0)) * 100.0) / 100.0, stat.getValue());
        stat.lower();
        stat.lower();
        assertEquals(Math.round((20.0) * 100.0) / 100.0, stat.getValue());
    }
}
