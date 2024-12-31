import org.example.Move;
import org.example.MoveFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {
    private MoveFactory factory;

    @BeforeEach
    void setUp() {
        factory = new MoveFactory();
    }

    @Test
    void testAttackMoveBaseValues() {
        Move move = factory.createMove("tackle");
        assertEquals("tackle", move.getName());
        assertEquals("normal", move.getType());
        assertEquals("physical", move.getCategory());
        assertEquals(40, move.getPower());
        assertTrue(move.getTarget().isEmpty());
        assertTrue(move.getTargetStat().isEmpty());
        assertTrue(move.getModifier().isEmpty());
        assertEquals(35, move.getPP());
    }

    @Test
    void testEffectMoveBaseValues() {
        Move move = factory.createMove("tail whip");
        assertEquals("tail whip", move.getName());
        assertEquals("normal", move.getType());
        assertEquals("status", move.getCategory());
        assertEquals(0, move.getPower());
        assertEquals("opp", move.getTarget());
        assertEquals("def", move.getTargetStat());
        assertEquals("-1", move.getModifier());
        assertEquals(30, move.getPP());
    }

    @Test
    void testMoveOutputString() {
        Move tackle = factory.createMove("tackle");
        Move tailWhip = factory.createMove("tail whip");
        assertEquals("tackle - normal (35/35)", tackle.toString());
        assertEquals("tail whip - normal (30/30)", tailWhip.toString());
    }

    @Test
    void testUseMove() {
        Move tackle = factory.createMove("tackle");
        Move tailWhip = factory.createMove("tail whip");
        tackle.use();
        tailWhip.use();
        assertEquals(34, tackle.getPP());
        assertEquals(29, tailWhip.getPP());
    }
}
