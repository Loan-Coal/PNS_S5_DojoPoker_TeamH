package PokerGame;

import CardHand.Card;
import CardHand.Hand;
import CardHand.PokerHand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PokerGameTest {

    PokerGame game;

    @BeforeEach
    void setUp() {
        game = new PokerGame();

        Hand hand2 = new Hand();
        hand2.addCard(new Card("2", "Ca"));
        hand2.addCard(new Card("4", "Co"));
        hand2.addCard(new Card("6", "Pi"));
        hand2.addCard(new Card("8", "Tr"));
        hand2.addCard(new Card("10", "Co"));
        game.addPokerHand(new PokerHand(hand2));

        Hand hand3 = new Hand();
        hand3.addCard(new Card("2", "Co"));
        hand3.addCard(new Card("4", "Ca"));
        hand3.addCard(new Card("6", "Tr"));
        hand3.addCard(new Card("8", "Pi"));
        hand3.addCard(new Card("10", "Ca"));
        game.addPokerHand(new PokerHand(hand3));
    }

    @Test
    void testCreation () {
        assertEquals(2, game.getAllHands().size());
        assertEquals(2, game.getNumberOfPlayers());
    }

    @Test
    void testFindBestHandsWinner () {
        Hand hand1 = new Hand();
        hand1.addCard(new Card("8","Ca"));
        hand1.addCard(new Card("1","Co"));
        hand1.addCard(new Card("3","Pi"));
        hand1.addCard(new Card("Ro","Tr"));
        hand1.addCard(new Card("8","Co"));
        game.addPokerHand(new PokerHand(hand1));

        ArrayList<PokerHand> expected= new ArrayList<>();
        ArrayList<PokerHand> get = game.findBestHand();
        System.out.println(get);
        expected.add(game.getAllHands().getLast());
        System.out.println(expected);
        assertEquals(expected, get);
    }

    @Test
    void testFindBestHandsEqual () {
        ArrayList<PokerHand> expected= new ArrayList<>();
        ArrayList<PokerHand> get = game.findBestHand();
        expected.add(game.getAllHands().getFirst());
        expected.add(game.getAllHands().getLast());
        assertEquals(expected, get);
    }


}