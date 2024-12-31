package org.example;

public class Move {
    private final String name;
    private final String type;
    private final String category;
    private final int power;
    private final String target; // If it is a status move, is it applied on itself ("self") or the opponent ("opp")?
    private final String targetStat; // What stat does the move modify?
    private final String modifier; // Does it raise ("1") or lower ("-1") targeted stat?
    private final int maxPP;
    private int currentPP;

    public Move(String[] args) {
        this.name = args[0];
        this.type = args[1];
        this.category = args[2];
        this.power = Integer.parseInt(args[3]);
        this.target = args[4];
        this.targetStat = args[5];
        this.modifier = args[6];
        this.maxPP = Integer.parseInt(args[7]);
        this.currentPP = Integer.parseInt(args[7]);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public String getCategory() {
        return category;
    }

    public String getTarget() {
        return target;
    }

    public String getTargetStat() {
        return targetStat;
    }

    public String getModifier() {
        return modifier;
    }

    public int getPP() {
        return currentPP;
    }

    public void use() {
        currentPP--;
    }

    @Override
    public String toString() {
        return name + " - " + type + " (" + currentPP + "/" + maxPP + ")";
    }
}
