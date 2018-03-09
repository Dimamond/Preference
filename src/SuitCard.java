public enum SuitCard {
    DIAMONDS('\u2662'),
    HEARTS('\u2661'),
    SPADES('\u2660'),
    CLUBS('\u2663');

    private final Character c;
    SuitCard(Character c) {
        this.c = c;
    }

    public Character getC() {
        return c;
    }

    @Override
    public String toString() {
        return c.toString();
    }
}
