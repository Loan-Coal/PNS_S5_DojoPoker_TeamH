package CardHand;

public class Card {
    private String value;
    private String suit;
    //constants to track the size of the valueTable and suitTable,
    //which store the maximal different values and suites of cards in a deck
    public static final int NUMBER_OF_VALUES = 14;
    public static final int NUMBER_OF_SUITES = 4;

    public Card(String value, String suit) {
        setValue(value);
        setSuit(suit);
    }

    public void setSuit(String suit){
        this.suit = suit;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() { return value; }

    /**
     * The value of a card ranges from 2 to 14, with the jack, queen, and king taking values 11, 12, and 13 respectively. The ace takes the value 14.
     * @return the value of the card as an int
     */
    public int getIntValue() {
        return switch (this.value) {
            case "Va" -> 11;
            case "Da" -> 12;
            case "Ro" -> 13;
            case "As" -> 14;
            default -> Integer.parseInt(this.value);
        };
    }

    /**
     * Methode who convert the card value to a string
     * @param value the value of the card
     * @return the string of the card
     */
    public static String convCardIntToStr (int value) {
        return switch (value) {
            case 14 -> "As" ;
            case 13 -> "Roi" ;
            case 12 -> "Dame" ;
            case 11 -> "Valet";
            default -> "" + value ;
        };
    }

    public String getSuit() { return suit; }

    /**
     * The suit of a card is represented by an integer ranging from 1 to 4, corresponding respectively to Hearts, Diamonds, Spades, and Clubs.
     * @return the suit of the card as an int
     */
    public int getIntSuit() {

        return switch (this.suit) {
            case "Co" -> 1;
            case "Ca" -> 2;
            case "Pi" -> 3;
            case "Tr" -> 4;
            default -> 0;
        };
    }

    /**
     * Method that compares the value of two cards
     * @param card the card to compare with
     * @return true if this card is higher, false otherwise
     */
    public boolean isHigher(Card card) {
        return this.getIntValue() > card.getIntValue();
    }

    @Override
    public String toString() {
        return getValue() + getSuit();
    }
}
