import org.example.Move;
import org.example.Pokemon;
import org.example.PokemonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonTest {
    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new PokemonFactory().createPokemon("pidgey");
    }

    @Test
    void testPokemonBaseValues() {
        assertEquals("pidgey", pokemon.getName());
        assertIterableEquals(Arrays.asList("normal", "flying"), Arrays.asList(pokemon.getTypes()));
        assertEquals(31, pokemon.getHP());
        assertEquals(15, pokemon.getATT().getValue());
        assertEquals(14, pokemon.getDEF().getValue());
        assertEquals(13, pokemon.getSPATT().getValue());
        assertEquals(13, pokemon.getSPDEF().getValue());
        assertEquals(18, pokemon.getSPD().getValue());
        assertIterableEquals(Arrays.asList("1. gust - flying (35/35)", "2. tackle - normal (35/35)"), pokemon.getMoveset());
        assertFalse(pokemon.isFainted());
        assertInstanceOf(String.class, pokemon.getStatsAsString());
    }

    @Test
    void testPokemonTakeDamage() {
        pokemon.takeDamage(16);
        assertEquals(15, pokemon.getHP());
        assertFalse(pokemon.isFainted());
    }

    @Test
    void testPokemonTakeFatalDamage() {
        pokemon.takeDamage(31);
        assertEquals(0, pokemon.getHP());
        assertTrue(pokemon.isFainted());
    }

    @Test
    void testPokemonSelectMove() {
        assertInstanceOf(Move.class, pokemon.selectMove(0));
    }
}
