package Entity;

public enum Symbol {
    Diamond, Club, Heart, Spade, Circle, Triangle, Square, Hexagon;

    private static final Symbol[] vals = values();

    public Symbol next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}

