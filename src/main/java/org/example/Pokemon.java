package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private final static int LEVEL = 12;
    private final String name;
    private final String[] types;
    private final double maxHP;
    private double currentHP;
    private final Stat att;
    private final Stat def;
    private final Stat spatt;
    private final Stat spdef;
    private final Stat spd;
    private final List<Move> moveset;
    private boolean fainted;

    public Pokemon(String[] args, MoveFactory factory) {
        this.name = args[0];
        this.types = new String[]{args[1], args[2]};
        this.maxHP = Integer.parseInt(args[3]);
        this.currentHP = Integer.parseInt(args[3]);
        this.att = new Stat(Integer.parseInt(args[4]));
        this.def = new Stat(Integer.parseInt(args[5]));
        this.spatt = new Stat(Integer.parseInt(args[6]));
        this.spdef = new Stat(Integer.parseInt(args[7]));
        this.spd = new Stat(Integer.parseInt(args[8]));
        this.moveset = new ArrayList<>();
        for (int i = 9; i < args.length; i++) {
            this.moveset.add(factory.createMove(args[i]));
        }
        this.fainted = false;
    }

    public String getName() {
        return name;
    }

    public String[] getTypes() {
        return types;
    }

    public double getHP() {
        return currentHP;
    }

    public Stat getATT() {
        return att;
    }

    public Stat getDEF() {
        return def;
    }

    public Stat getSPATT() {
        return spatt;
    }

    public Stat getSPDEF() {
        return spdef;
    }

    public Stat getSPD() {
        return spd;
    }

    public void takeDamage(double damage) {
        this.currentHP -= damage;
        if (this.currentHP <= 0) {
            this.fainted = true;
            this.currentHP = 0;
        }
    }

    public List<String> getMoveset() {
        List<String> movesetStrings = new ArrayList<>();
        for (int i = 0; i < moveset.size(); i++) {
            movesetStrings.add((i + 1) + ". " + moveset.get(i).toString());
        }
        return movesetStrings;
    }

    //
    public String getStatsAsString() {
        List<String> list = new ArrayList<>();
        list.add("Type: " + types[0] + (types[1].isEmpty() ? "" : " - " + types[1]));
        list.add("HP: " + (Math.round(currentHP * 100.0) / 100.0) + "/" + maxHP);
        list.add("ATT: " + att.getValue());
        list.add("DEF: " + def.getValue());
        list.add("SPATT: " + spatt.getValue());
        list.add("SPDEF: " + spdef.getValue());
        list.add("SPD: " + spd.getValue());
        return String.join(" | ", list);
    }

    public Stat getStat(String stat) {
        switch (stat) {
            case "att":
                return this.att;
            case "def":
                return this.def;
            case "spatt":
                return this.spatt;
            case "spdef":
                return this.spdef;
            default:
                return null;
        }
    }

    public Move selectMove(int index) {
        if (index > moveset.size() - 1) {
            return null;
        }
        return moveset.get(index);
    }

    public boolean isFainted() {
        return fainted;
    }

    public static int getLevel() {
        return LEVEL;
    }

    @Override
    public String toString() {
        return name + " | Type: " + types[0] + (types[1].isEmpty() ? "" : " - " + types[1]) + " | HP: " + (Math.round(currentHP * 100.0) / 100.0) + "/" + maxHP;
    }
}
