package CardHand;

import CardHand.Card;
import CardHand.Hand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    private Hand hand;

    @BeforeEach
    public void setUp() {
        hand = new Hand();
    }

    @Test
    public void testAddCard() {
        Card card1 = new Card("8","Ca");
        hand.addCard(card1);

        assertEquals(1, hand.getCards().size());
        assertEquals(card1, hand.getCard(0));
    }

    @Test
    void storeCards() {
        Hand hand1 = new Hand();
        hand1.addCard(new Card("8","Ca"));
        hand1.addCard( new Card("1","Co"));
        hand1.addCard(new Card("3","Pi"));
        hand1.addCard(new Card("Ro","Tr"));
        hand1.addCard(new Card("8","Co"));

        assertTrue(hand1.getValueTable().equals(new ArrayList<>(Arrays.asList(1,0,1,0,0,0,0,2,0,0,0,0,1,1))));
        assertTrue(hand1.getSuitTable().equals(new ArrayList<>(Arrays.asList(2,1,1,1))));
    }
    @Test
    public void testAddDuplicateCard() {
        Card card1 = new Card("10", "Co");
        hand.addCard(card1);
        hand.addCard(card1);

        assertEquals(1, hand.getCards().size());
    }

    @Test
    public void testAddCardToFullHand() {
        hand.addCard(new Card("10", "Co"));
        hand.addCard(new Card("Va", "Pi"));
        hand.addCard(new Card("Ro", "Tr"));
        hand.addCard(new Card("Da", "Ca"));
        hand.addCard(new Card("9", "Pi"));

        assertTrue(hand.isFull());

        Card card = new Card("8", "Co");
        hand.addCard(card);

        assertFalse(hand.getCards().contains(card));
    }

    @Test
    public void testFindHighestCard() {
        hand.addCard(new Card("5", "Co"));
        hand.addCard(new Card("8", "Pi"));
        hand.addCard(new Card("Va", "Tr"));
        hand.addCard(new Card("Da", "Ca"));
        hand.addCard(new Card("3", "Co"));

        Card highestCard = hand.findHighestCard();
        assertEquals("Da", highestCard.getValue());
        assertEquals("Ca", highestCard.getSuit());
    }

    @Test
    public void testClearHand() {
        hand.addCard(new Card("5", "Co"));
        hand.addCard(new Card("8", "Pi"));

        assertFalse(hand.getCards().isEmpty());
        hand.clearHand();
        assertTrue(hand.getCards().isEmpty());
    }

    @Test
    public void testSortHandByValue() {
        hand.addCard(new Card("5", "Co"));
        hand.addCard(new Card("8", "Pi"));
        hand.addCard(new Card("Va", "Tr"));
        hand.addCard(new Card("Da", "Ca"));
        hand.addCard(new Card("3", "Co"));

        hand.sortHandByValue();

        List<Card> sortedCards = hand.getCards();
        assertEquals(3, sortedCards.get(0).getIntValue());
        assertEquals(5, sortedCards.get(1).getIntValue());
        assertEquals(8, sortedCards.get(2).getIntValue());
        assertEquals(11, sortedCards.get(3).getIntValue());
        assertEquals(12, sortedCards.get(4).getIntValue());
    }

    @Test
    public void testGetCardInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> hand.getCard(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> hand.getCard(6));
    }

    @Test
    public void testRearrangeCardsInvalidIndex() {
        hand.addCard(new Card("5", "Co"));
        hand.addCard(new Card("8", "Pi"));

        assertThrows(IndexOutOfBoundsException.class, () -> hand.rearrangeCardsToFront(2, 0));
    }

    /*@Test
    public void testRemoveCard() {
        Card card1 = new Card("10", "Coeur");
        Card card2 = new Card("Valet", "Pique");
        hand.addCard(card1);
        hand.addCard(card2);

        hand.getCards().remove(card1);

        assertEquals(1, hand.getCards().size());
        assertFalse(hand.getCards().contains(card1));
        assertTrue(hand.getCards().contains(card2));
    }*/
}