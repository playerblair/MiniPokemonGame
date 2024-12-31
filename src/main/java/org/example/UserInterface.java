package org.example;

import java.util.*;

public class UserInterface {
    private final Scanner scanner;
    private final PokemonFactory factory;
    private final Random random;
    private final Map<String, Map<String, String[]>> typeChart;
    private Trainer trainer1;
    private Trainer trainer2;

    public UserInterface(Scanner scanner, PokemonFactory factory, Random random) {
        this.scanner = scanner;
        this.factory = factory;
        this.random = random;
        typeChart = new HashMap<>();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void start() {
        populateTypeChart();
        trainer1 = createTrainer(1);
        trainer2 = createTrainer(2);
        draftPokemon(trainer1);
        draftPokemon(trainer2);
        sendOutPokemon(trainer1);
        sendOutPokemon(trainer2);
        MAIN_LOOP: while (true) {
            clearScreen();
            if (!(trainer1.hasAvailablePokemon() && trainer2.hasAvailablePokemon())) {
                break MAIN_LOOP;
            }
            if (trainer1.getPokemon() == null) {
                sendOutPokemon(trainer1);
            }
            if (trainer2.getPokemon() == null) {
                sendOutPokemon(trainer2);
            }
            displayInfo();
            displayMenu(trainer1);
            takeCommand(trainer1);
            displayInfo();
            displayMenu(trainer2);
            takeCommand(trainer2);
            if (trainer1.getPokemon().getSPD().getValue() > trainer2.getPokemon().getSPD().getValue()) {
                battle(trainer1, trainer2);
                if (hasPokemonFainted(trainer2)) {
                    continue MAIN_LOOP;
                }
                battle(trainer2, trainer1);
                hasPokemonFainted(trainer1);
            } else if ((trainer1.getPokemon().getSPD().getValue() < trainer2.getPokemon().getSPD().getValue())) {
                battle(trainer2, trainer1);
                if (hasPokemonFainted(trainer1)) {
                    continue MAIN_LOOP;
                }
                battle(trainer1, trainer2);
                hasPokemonFainted(trainer2);
            } else {
                if (random.nextBoolean()) {
                    battle(trainer1, trainer2);
                    if (hasPokemonFainted(trainer2)) {
                        continue MAIN_LOOP;
                    }
                    battle(trainer2, trainer1);
                    hasPokemonFainted(trainer1);
                } else {
                    battle(trainer2, trainer1);
                    if (hasPokemonFainted(trainer1)) {
                        continue MAIN_LOOP;
                    }
                    battle(trainer1, trainer2);
                    hasPokemonFainted(trainer2);
                }
            }
            this.scanner.nextLine();
        }
    }

    public Trainer createTrainer(int x) {
        displayBanner("=");
        System.out.println("Trainer " + x + " Setup");
        displayBanner("-");
        System.out.print("Enter name: ");
        String name = this.scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Enter name: ");
            name = this.scanner.nextLine().trim();
        }
        return new Trainer(name);
    }

    public void draftPokemon(Trainer trainer) {
        displayBanner("=");
        System.out.println("Trainer: " + trainer.getName());
        displayBanner("-");
        String[] names = this.factory.getPokemonNames();
        int columns = 3;
        for (int i = 0; i < names.length; i++) {
            System.out.printf("%d. %-10s%s", i+1, names[i], ((i + 1) % columns == 0) ? "\n" : "\t");
        }
        System.out.println();
        while (trainer.getPokemonList().size() < 3) {
            int index;
            CHOOSE_POKEMON: while (true) {
                System.out.print("Choose pokemon to add to party: ");
                String choice = this.scanner.nextLine();
                Optional<Integer> choiceAsInteger = validateInteger(choice);
                if (choiceAsInteger.isPresent()) {
                    index = choiceAsInteger.get() - 1;
                    if (index >=0 && index < names.length) {
                        break CHOOSE_POKEMON;
                    }
                }
            }
            Pokemon pokemon = this.factory.createPokemon(names[index]);
            trainer.addPokemon(pokemon);
            System.out.println(trainer.getName() + " added " + pokemon.getName() + " to their party.");
        }
    }

    public void sendOutPokemon(Trainer trainer) {
        displayBanner("=");
        System.out.println("Trainer: " + trainer.getName());
        displayBanner("-");
        System.out.println(trainer.getAvailablePokemonAsString());
        SEND_OUT_POKEMON: while (true) {
            System.out.print("Choose pokemon to send out to battle: ");
            String choice = this.scanner.nextLine();
            Optional<Integer> choiceAsInteger = validateInteger(choice);
            if (choiceAsInteger.isPresent()) {
                int index = choiceAsInteger.get() - 1;
                if (trainer.selectPokemon(index)) {
                    break SEND_OUT_POKEMON;
                }
            }
        }
        displayBanner("-");
        System.out.println(trainer.getName() + " sends out " + trainer.getPokemon().getName());
    }

    private Optional<Integer> validateInteger(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void displayMenu(Trainer trainer) {
        displayBanner("-");
        System.out.println("Trainer: " + trainer.getName());
        displayBanner("-");
        List<String> options = trainer.getPokemon().getMoveset();
        options.add("s. Show stats");
        options.add("c. Change pokemon");
        System.out.println("Available moves:");
        for (String line: trainer.getPokemon().getMoveset()) {
            System.out.println("\t" + line);
        }
        System.out.println("Additional options:");
        System.out.println("    s. Show stats");
        System.out.println("    c. Change pokemon");
        displayBanner("-");
    }

    private void takeCommand(Trainer trainer) {
        Pokemon pokemon = trainer.getPokemon();
        boolean done;
        PROCESS_COMMANDS: while (true) {
            System.out.print("Select option (1, 2, 3, 4, s, c) : ");
            String input = this.scanner.nextLine();
            switch (input) {
                case "1":
                    done = trainer.setMove(pokemon.selectMove(0));
                    if (done) { break PROCESS_COMMANDS; }
                case "2":
                    done = trainer.setMove(pokemon.selectMove(1));
                    if (done) { break PROCESS_COMMANDS; }
                case "3":
                    done = trainer.setMove(pokemon.selectMove(2));
                    if (done) { break PROCESS_COMMANDS; }
                case "4":
                    done = trainer.setMove(pokemon.selectMove(3));
                    if (done) { break PROCESS_COMMANDS; }
                case "s":
                    System.out.println(pokemon.getStatsAsString());
                    continue PROCESS_COMMANDS;
                case "c":
                    sendOutPokemon(trainer);
                    if (trainer.getPokemon() != pokemon) { break PROCESS_COMMANDS; }
            }
        }
    }

    public void battle(Trainer i, Trainer j) {
        displayBanner("-");
        Move move = i.getMove();
        if (move == null) {
            return;
        }
        String category = move.getCategory();
        double modifier = 1.0;
        if (category.equals("status")) {
            Stat stat;
            if (move.getTarget().equals("self")) {
                stat = i.getPokemon().getStat(move.getTargetStat());
            } else {
                stat = j.getPokemon().getStat(move.getTargetStat());
            }

            if (move.getModifier().equals("-1")) {
                stat.lower();
            } else {
                stat.raise();
            }
        } else {
            double att = i.getPokemon().getStat(category.equals("physical") ? "att" : "spatt").getValue();
            double def = j.getPokemon().getStat(category.equals("physical") ? "def" : "spdef").getValue();
            double base = calcDamage(move.getPower(), att, def);
            modifier = getTypeModifier(move.getType(), j.getPokemon().getTypes());
            double randomModifier = 0.85 + (random.nextDouble() * 0.15);
            double damage = base * modifier * randomModifier;
            j.getPokemon().takeDamage(damage);
        }
        move.use();
        System.out.println(i.getPokemon().getName() + " used " + move.getName());
        displayEffectiveness(modifier);
    }

    public double calcDamage(int power, double att, double def) {
        double part1 = 1.0 * (2 * Pokemon.getLevel() + 10) / 250;
        double part2 = att / def;
        return part1 * part2 * power;
    }

    public void displayInfo() {
        displayBanner("=");
        System.out.println("Trainer: " + trainer1.getName() + " - Current Pokemon: " + trainer1.getPokemon().toString());
        System.out.println("vs.");
        System.out.println("Trainer: " + trainer2.getName() + " - Current Pokemon: " + trainer2.getPokemon().toString());
    }

    public void displayBanner(String str) {
        System.out.println(str.repeat(50));
    }

    public void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("-".repeat(50));
            System.out.println();
        }
    }

    public boolean hasPokemonFainted(Trainer trainer) {
        if (trainer.getPokemon().isFainted()) {
            System.out.println(trainer.getPokemon().getName() + " has fainted.");
            trainer.pokemonHasFainted();
            return true;
        }
        return false;
    }

    public void populateTypeChart() {
        String[] categories = new String[]{"strong", "weak", "null"};
        for (String category: categories) {
            String fileName = "type-" + category + ".csv";
            try (Scanner reader = new Scanner(UserInterface.class.getClassLoader().getResourceAsStream(fileName))) {
                Map<String, String[]> map = new HashMap<>();
                while (reader.hasNext()) {
                    String line = reader.nextLine();
                    String[] parts = line.split(",");
                    String[] array;
                    if (parts.length > 1) {
                        array = Arrays.copyOfRange(parts, 1, parts.length);
                    } else {
                        array = new String[0];
                    }
                    map.put(parts[0], array);
                }
                typeChart.put(category, map);
            } catch (Exception e) {
                System.out.println("Error (UserInterface):" + e.getMessage());
            }
        }
    }

    public double getTypeModifier(String moveType, String[] pokemonTypes) {
        double modifier = 1.0;
        for (String type: pokemonTypes) {
            if (Arrays.asList(typeChart.get("strong").get(moveType)).contains(type)) {
                modifier *= 2.0;
                continue;
            }
            if (Arrays.asList(typeChart.get("weak").get(moveType)).contains(type)) {
                modifier *= 0.5;
                continue;
            }
            if (Arrays.asList(typeChart.get("null").get(moveType)).contains(type)) {
                modifier *= 0.0;
            }
        }
        return modifier;
    }

    public void displayEffectiveness(double modifier) {
        if (modifier <= 0) {
            System.out.println("It had no effect.");
        } else if (modifier < 1.0) {
            System.out.println("It wasn't very effective.");
        } else if (modifier >= 2.0) {
            System.out.println("It was super effective.");
        }
    }
}
