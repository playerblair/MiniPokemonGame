package org.example;

import java.util.*;

public class MoveFactory {
    private final Map<String, String[]> moves;

    public MoveFactory() {
        moves = new HashMap<>();
        try (Scanner reader = new Scanner(MoveFactory.class.getClassLoader().getResourceAsStream("moves.csv"))) {
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                moves.put(parts[0], parts);
            }
        } catch (Exception e) {
            System.out.println("Error (MoveFactory): " + e.getMessage());
        }
    }

    public Move createMove(String move) {
        return new Move(moves.get(move));
    }

    public Map<String, String[]> getMoves() {
        return moves;
    }
}
