import org.example.Pokemon;
import org.example.PokemonFactory;
import org.example.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer("player");
    }

    @Test
    void testUser() {
        assertEquals("player", trainer.getName());
    }

    @Test
    void testAddingPokemonToUser() {
        trainer.addPokemon(new PokemonFactory().createPokemon("pidgey"));
        assertEquals(1, trainer.getPokemonList().size());
        assertInstanceOf(Pokemon.class, trainer.getPokemonList().get(0));
    }

    @Test
    void testSelectingCurrentPokemon() {
        trainer.addPokemon(new PokemonFactory().createPokemon("pidgey"));
        trainer.selectPokemon(0);
        assertInstanceOf(Pokemon.class, trainer.getPokemon());
        assertNotNull(trainer.getPokemon());
    }

    @Test
    void testSelectingCurrentMove() {
        trainer.addPokemon(new PokemonFactory().createPokemon("pidgey"));
        trainer.selectPokemon(0);
        Pokemon pokemon = trainer.getPokemon();
        trainer.setMove(pokemon.selectMove(0));
        assertNotNull(trainer.getMove());
        assertEquals("gust", trainer.getMove().getName());
    }
}
