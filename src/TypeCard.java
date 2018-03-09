public enum TypeCard {
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    private final Character type;

    TypeCard(Character type){
        this.type = type;
    }

    public Character getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
