import org.example.Pokemon;
import org.example.PokemonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonFactoryTest {
    private PokemonFactory factory;

    @BeforeEach
    void setUp() {
        factory = new PokemonFactory();
    }

    @Test
    void testPokemonFactory() {
        assertFalse(factory.getPokemon().keySet().isEmpty());
    }

    @Test
    void testPokemonFactoryCreatePokemon() {
        Pokemon pokemon = factory.createPokemon("charmander");
        assertEquals("charmander", pokemon.getName());
        assertIterableEquals(Arrays.asList("fire", ""), Arrays.asList(pokemon.getTypes()));
        assertEquals(31, pokemon.getHP());
        assertEquals(3, pokemon.getMoveset().size());
    }
}
