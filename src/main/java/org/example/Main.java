package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PokemonFactory factory = new PokemonFactory();
        Random random = new Random();
        UserInterface ui = new UserInterface(scanner, factory, random);
        ui.start();
    }
}
