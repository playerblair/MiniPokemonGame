package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PokemonFactory {
    private Map<String, String[]> pokemon;
    private String[] pokemonNames;
    private MoveFactory moveFactory;

    public PokemonFactory() {
        pokemon = new HashMap<>();
        moveFactory = new MoveFactory();
        try (Scanner reader = new Scanner(PokemonFactory.class.getClassLoader().getResourceAsStream("pokemon.csv"))) {
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                pokemon.put(parts[0], parts);
            }
            pokemonNames = pokemon.keySet().toArray(new String[0]);
        } catch (Exception e) {
            System.out.println("Error (PokemonFactory):" + e.getMessage());
        }
    }

    public Pokemon createPokemon(String pkmn) {
        return new Pokemon(pokemon.get(pkmn), moveFactory);
    }

    public Map<String, String[]> getPokemon() {
        return pokemon;
    }

    public String[] getPokemonNames() {
        return pokemonNames;
    }
}
