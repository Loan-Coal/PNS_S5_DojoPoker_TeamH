package CardHand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    Card card ;
    @BeforeEach
    void setUp() {
        card = new Card("As", "Co");
    }

    @Test
    void testBuild () {
        assertEquals("As", card.getValue());
        assertEquals("Co", card.getSuit());
    }

    @Test
    void testSetAndGetInt () {
        assertEquals(14,card.getIntValue());
        assertEquals(1, card.getIntSuit());
        card.setValue("8");
        card.setSuit("Tr");
        assertEquals(8, card.getIntValue());
        assertEquals(4, card.getIntSuit());
    }

    @Test
    void testIsHigher () {
        Card card2 = new Card("Ro", "Ca");
        assertTrue(card.isHigher(card2));
        assertFalse(card2.isHigher(card));
    }

    @Test
    void testToString () {
        assertEquals("AsCo", card.toString());
    }

    /*@Test
    void testInvalidCardValue() {
        assertThrows(NumberFormatException.class, () -> new Card("Invalid", "Co"));
    }*/

    @Test
    void testInvalidCardSuit() {
        Card invalidSuitCard = new Card("10", "Invalid");
        assertEquals(0, invalidSuitCard.getIntSuit());
    }

    @Test
    void testEdgeCasesInGetIntValue() {
        Card lowValueCard = new Card("2", "Co");
        assertEquals(2, lowValueCard.getIntValue());

        Card highValueCard = new Card("As", "Co");
        assertEquals(14, highValueCard.getIntValue());
    }

    @Test
    void testEdgeCasesInGetIntSuit() {
        Card lowSuitCard = new Card("10", "Co");
        assertEquals(1, lowSuitCard.getIntSuit());

        Card highSuitCard = new Card("10", "Tr");
        assertEquals(4, highSuitCard.getIntSuit());
    }

    @Test
    void testConvCardIntToStr() {
        assertEquals("As", Card.convCardIntToStr(14));
        assertEquals("Roi", Card.convCardIntToStr(13));
        assertEquals("Dame", Card.convCardIntToStr(12));
        assertEquals("Valet", Card.convCardIntToStr(11));
        assertEquals("10", Card.convCardIntToStr(10));
    }

}