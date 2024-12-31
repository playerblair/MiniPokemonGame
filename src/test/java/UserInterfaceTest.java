import org.example.PokemonFactory;
import org.example.Trainer;
import org.example.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class UserInterfaceTest {
    private UserInterface ui;

    @BeforeEach
    void setUp() {
        ui = new UserInterface(new Scanner(System.in), new PokemonFactory(), new Random());
    }

    @Test
    void testUserInterface() {
        assertInstanceOf(Scanner.class, ui.getScanner());
    }

    @Test
    void testUserInterfaceCreateUser() {
        String input = "Michael\n";
        InputStream in = new ByteArrayInputStream((input.getBytes()));
        System.setIn(in);

        UserInterface sui = new UserInterface(new Scanner(System.in), new PokemonFactory(), new Random());

        Trainer trainer = sui.createTrainer(1);
        assertEquals("Michael", trainer.getName());
    }
}
