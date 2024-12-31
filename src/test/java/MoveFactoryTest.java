import org.example.MoveFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MoveFactoryTest {
    private MoveFactory factory;

    @BeforeEach
    void setUp() {
        factory = new MoveFactory();
    }

    @Test
    void testMoveFactory() {
        assertFalse(factory.getMoves().keySet().isEmpty());
    }

    @Test
    void testMoveFactoryCreateMove() {
        assertEquals("pound", factory.createMove("pound").getName());
    }
}
