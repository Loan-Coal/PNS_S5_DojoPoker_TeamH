package CardHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import static CardHand.Card.NUMBER_OF_SUITES;
import static CardHand.Card.NUMBER_OF_VALUES;

public class Hand {
    private final List<Card> cards;
    private static final Logger logger = Logger.getLogger(Hand.class.getName());
    private String combinaison;
    //tables before removing the combos
    private final ArrayList<Integer> valueTable;
    private final ArrayList<Integer> suiteTable;


    // Constructeur de la classe Hand
    public Hand() { this.cards = new ArrayList<>(5); // Initialisation de la liste de cartes avec une capacité de 5 cartes
        valueTable = new ArrayList<>(Collections.nCopies(NUMBER_OF_VALUES, 0));
        suiteTable = new ArrayList<>(Collections.nCopies(NUMBER_OF_SUITES, 0));
    }

    public ArrayList<Integer> getSuitTable() { return suiteTable; }
    public ArrayList<Integer> getValueTable() { return valueTable; }
    public String getCombinaison() {return combinaison;}
    public void setCombinaison(String combinaison) {this.combinaison = combinaison;}

    /**
     * Methode who store the cards in the tables
     *
     * @param myHand the hand of the player
     * @param valueTable the table who will contain the number of cards of each value
     * @param suiteTable the table who will contain the number of cards of each suite
     * @throws IndexOutOfBoundsException if the size of the tables is not correct
     */
    public void storeCards(Hand myHand, ArrayList<Integer> valueTable, ArrayList<Integer> suiteTable) {
        if (valueTable.size() != NUMBER_OF_VALUES) {
            throw new IndexOutOfBoundsException("Taille de tableau non valide, " + NUMBER_OF_VALUES + " nécessaire, or a eu " + valueTable.size());
        } else if (suiteTable.size() != NUMBER_OF_SUITES) {
            throw new IndexOutOfBoundsException("Taille de tableau non valide, " + NUMBER_OF_SUITES +" nécessaire");
        } else {

            for (int i = 0; i < myHand.getSize(); i++) {
                Card card = myHand.getCard(i);
                valueTable.set(card.getIntValue() - 1, valueTable.get(card.getIntValue() - 1) + 1);
                //adds the ace into the 0th position as well, because ace is both bottom and top
                if (card.getIntValue() == 1) {
                    valueTable.set(NUMBER_OF_VALUES-1,  valueTable.get(NUMBER_OF_VALUES-1) + 1);
                }
                suiteTable.set(card.getIntSuit() - 1, suiteTable.get(card.getIntSuit() - 1) + 1);
            }
        }
    }

    /**
     *
     * @param valueTable
     * @param suiteTable
     * resets all values of the tables to 0s
     */
    public void resetTables(ArrayList<Integer> valueTable, ArrayList<Integer> suiteTable) {
        valueTable.replaceAll(ignored -> 0);
        suiteTable.replaceAll(ignored -> 0);
    }
    /**
     * Method to add a card to the hand.
     * @param card The card to add to the hand.
     * @log if the hand already contains the card, a warning message is logged.
     */
    public void addCard(Card card) {
        resetTables(valueTable, suiteTable);
        if (cards.contains(card)) {
            logger.log(Level.WARNING, "Carte en double détectée : " + card);
            return;
        }

        if (cards.size() < 5) {
            cards.add(card); // Ajoute la carte si la main contient moins de 5 cartes
        } else {
            logger.log(Level.WARNING, "La main est pleine avec 5 cartes.");
        }
        storeCards(this, valueTable, suiteTable);
    }


    public void removeCard(Card card) {
        if (cards.contains(card)) {
            cards.remove(card);
        } else {
            throw new IllegalArgumentException("La carte n'est pas dans la main : " + card);
        }
    }

    /**
     * Method to get a card at a specific index.
     * @param index The index of the card to get.
     * @return The card at the specified index.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index de carte invalide : " + index);
        }
    }

    /**
     * Method to get the size of the hand.
     * @return The size of the hand.
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * Method to get an immutable list of the cards in the hand.
     * @return An immutable list of the cards in the hand.
     */
    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    /**
     * Method to display the cards in the hand.
     */
    public void showHand() {
        logger.log(Level.INFO, "Main :");
        for (Card card : cards) {
            logger.log(Level.INFO, card.toString());
        }
    }

    /**
     * Method to find the highest card in the hand.
     * @return The highest card in the hand.
     * @throws IllegalStateException If the hand is empty.
     */
    public Card findHighestCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("La main est vide.");
        }

        Card highest = cards.getFirst();
        for (Card card : cards) {
            if (card.getIntValue() > highest.getIntValue()) {
                highest = card;
            }
        }
        /*cards.remove(highest);*/
        return highest;
    }

    /**
     * Method to rearrange the cards to the front of the hand.
     * @param index1 first card to rearrange
     * @param index2 second card to rearrange
     * @throws IndexOutOfBoundsException If the indexes are invalid.
     */
    public void rearrangeCardsToFront(int index1, int index2) {
        if (index1 < 0 || index1 >= cards.size() || index2 < 0 || index2 >= cards.size()) {
            throw new IndexOutOfBoundsException("Indexes invalides fournis pour réarranger les cartes : "
                    + index1 + ", " + index2);
        }

        Card card1 = cards.remove(index1);
        Card card2 = cards.remove(index2 < index1 ? index2 : index2 - 1);

        cards.addFirst(card2);
        cards.addFirst(card1);
    }

    /**
     * Method to check if the hand is full.
     * @return true if the hand is full, false otherwise.
     */
    public boolean isFull() {
        return cards.size() == 5;
    }

    /**
     * Method to clear the hand.
     */
    public void clearHand() {
        logger.log(Level.INFO, "Vidage de la main.");
        cards.clear();
        resetTables(valueTable, suiteTable);
    }

    /**
     * Method to sort the hand by card value.
     */
    public void sortHandByValue() {
        cards.sort((Card c1, Card c2) -> Integer.compare(c1.getIntValue(), c2.getIntValue()));
        logger.log(Level.INFO, "Main triée par valeur de carte.");
    }



}