package CardHand;

//import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


class PokerHandTest {
    PokerHand hand1;
    PokerHand hand2;

    private PokerHand createPokerHand(Card card1, Card card2, Card card3, Card card4, Card card5) {
        Hand hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);
        return new PokerHand(hand);
    }

    private boolean equals(int[] tab1,int[] tab2){
        for (int i = 0; i < tab1.length; i++){
            if (tab1[i] != tab2[i]){
                return false;
            }
        }
        return true;
    }

    @Test
    void getStrengthOfHand() {

        PokerHand hand1 = createPokerHand(
                new Card("9","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("9","Co")
        );
        assertEquals(10900.141303,hand1.getStrengthOfHand());

        PokerHand hand2 = createPokerHand(
                new Card("9","Ca"),
                new Card("9","Co"),
                new Card("2","Pi"),
                new Card("2","Tr"),
                new Card("5","Co")
        );
        assertEquals(20902.05,hand2.getStrengthOfHand());

        PokerHand hand3 = createPokerHand(
                new Card("Ro","Ca"),
                new Card("Ro","Co"),
                new Card("Ro","Pi"),
                new Card("2","Tr"),
                new Card("5","Co")
        );
        assertEquals(31300.0502,hand3.getStrengthOfHand());

        PokerHand hand4 = createPokerHand(
                new Card("7","Ca"),
                new Card("8","Co"),
                new Card("9","Pi"),
                new Card("10","Tr"),
                new Card("Va","Co")
        );
        assertEquals(41100,hand4.getStrengthOfHand());

        PokerHand hand5 = createPokerHand(
                new Card("2","Ca"),
                new Card("9","Ca"),
                new Card("4","Ca"),
                new Card("1","Ca"),
                new Card("Ro","Ca")
        );
        //assertEquals(50000.1413090402,hand5.getStrengthOfHand());

        PokerHand hand6 = createPokerHand(
                new Card("Ro","Ca"),
                new Card("Ro","Co"),
                new Card("Ro","Pi"),
                new Card("10","Tr"),
                new Card("10","Co")
        );
        assertEquals(61310,hand6.getStrengthOfHand());

        PokerHand hand7 = createPokerHand(
                new Card("1","Ca"),
                new Card("1","Co"),
                new Card("1","Pi"),
                new Card("1","Tr"),
                new Card("Va","Co")
        );
        assertEquals(71400.11,hand7.getStrengthOfHand());

        PokerHand hand8 = createPokerHand(
                new Card("9","Ca"),
                new Card("10","Ca"),
                new Card("Va","Ca"),
                new Card("Ro","Ca"),
                new Card("Da","Ca")
        );
        assertEquals(81300,hand8.getStrengthOfHand());

        PokerHand hand9 = createPokerHand(
                new Card("10","Co"),
                new Card("Va","Co"),
                new Card("Da","Co"),
                new Card("Ro","Co"),
                new Card("1","Co")
        );
        assertEquals(90000,hand9.getStrengthOfHand());

        /**
         * Le premier chiffre à gauche varie de 0 à 9 et indique la force de la combinaison, les deux suivants donnent la valeur de la carte qui fait
         * la combinaison, les deux suivants donnent la valeur de la carte qui fait la deuxieme combinaison (comme une paire), et les valeurs derrière
         * la virgule correspondent aux valeurs des autres cartes.
         */
    }
    //[1,0,1,0,0,0,0,2,0,0,0,1,1]
    //01 08 00.14 13 3
    //10800.14133



    @Test
    void testFlush(){
        PokerHand hand5 = createPokerHand(
                new Card("2","Ca"),
                new Card("9","Ca"),
                new Card("4","Ca"),
                new Card("1","Ca"),
                new Card("Ro","Ca")
        );
        assertEquals("Couleur, As, Roi, 9, 4, 2",hand5.getPokerHandText());
    }
    @Test
    void getPokerHandText() {

        PokerHand hand1 = createPokerHand(
                new Card("9","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("9","Co")
        );

        PokerHand hand2 = createPokerHand(
                new Card("9","Ca"),
                new Card("9","Co"),
                new Card("2","Pi"),
                new Card("2","Tr"),
                new Card("5","Co")
        );

        PokerHand hand3 = createPokerHand(
                new Card("Ro","Ca"),
                new Card("Ro","Co"),
                new Card("Ro","Pi"),
                new Card("2","Tr"),
                new Card("5","Co")
        );

        PokerHand hand4 = createPokerHand(
                new Card("7","Ca"),
                new Card("8","Co"),
                new Card("9","Pi"),
                new Card("10","Tr"),
                new Card("Va","Co")
        );




        PokerHand hand6 = createPokerHand(
                new Card("Ro","Ca"),
                new Card("Ro","Co"),
                new Card("Ro","Pi"),
                new Card("10","Tr"),
                new Card("10","Co")
        );

        PokerHand hand10 = createPokerHand(
                new Card("Ro","Ca"),
                new Card("Ro","Co"),
                new Card("Ro","Pi"),
                new Card("Ro","Tr"),
                new Card("10","Co")
        );


        PokerHand hand7 = createPokerHand(
                new Card("1","Ca"),
                new Card("1","Co"),
                new Card("1","Pi"),
                new Card("1","Tr"),
                new Card("Va","Co")
        );


        PokerHand hand8 = createPokerHand(
                new Card("9","Ca"),
                new Card("10","Ca"),
                new Card("Va","Ca"),
                new Card("Ro","Ca"),
                new Card("Da","Ca")
        );


        PokerHand hand9 = createPokerHand(
                new Card("10","Co"),
                new Card("Va","Co"),
                new Card("Da","Co"),
                new Card("Ro","Co"),
                new Card("1","Co")
        );



        assertEquals("Paire de 9, As, Roi, 3",hand1.getPokerHandText());
        assertEquals("Double paire de 9 et de 2, 5",hand2.getPokerHandText());
        assertEquals("Brelan de Roi, 5, 2",hand3.getPokerHandText());
        assertEquals("Suite au Valet",hand4.getPokerHandText());


        assertEquals("Full : brelan de Roi et pair de 10",hand6.getPokerHandText());
        assertEquals("Carré de Roi, 10",hand10.getPokerHandText());
        assertEquals("Carré de As, Valet",hand7.getPokerHandText());
        assertEquals("Quinte flush au Roi",hand8.getPokerHandText());
        assertEquals("Quinte flush royale",hand9.getPokerHandText());

    }

    @Test
    void comparePokerHands() {
        PokerHand hand1 = createPokerHand(
                new Card("8","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("8","Co")
        );
        PokerHand hand2= createPokerHand(
                new Card("9","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("9","Co")
        );
        assertTrue(hand2.comparePokerHands(hand1).equals(new ArrayList<>(Arrays.asList("1","Paire de 9"))));
        assertTrue(hand1.comparePokerHands(hand2).equals(new ArrayList<>(Arrays.asList("-1","Paire de 9"))));


        hand1 = createPokerHand(
                new Card("8","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("8","Co")
        );
        hand2= createPokerHand(
                new Card("8","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("8","Co")
        );
        assertTrue(hand2.comparePokerHands(hand1).equals(new ArrayList<>(Arrays.asList("0"))));
    }

    @Test
    void combinationValue() {
        hand1 = createPokerHand(
                new Card("8","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("8","Co")
        );
        assertTrue(hand1.getStrengthOfHand() == 10800.141303);

    }

    @Test
    void findNonComboCards() {
        hand1 = createPokerHand(
                new Card("8","Ca"),
                new Card("1","Co"),
                new Card("3","Pi"),
                new Card("Ro","Tr"),
                new Card("8","Co")
        );
        //test wont work because its done after pokerhand generation, where non combo table is always null
        assertTrue(hand1.getValueTableNonCombo().equals(new ArrayList<>(Arrays.asList(1,0,1,0,0,0,0,0,0,0,0,0,1,1))));
        assertTrue(hand1.getSuitTableNonCombo().equals(new ArrayList<>(Arrays.asList(1,0,1,1))));
    }
}