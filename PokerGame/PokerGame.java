package PokerGame;

import CardHand.PokerHand;
import Utils.TextDecomposer;

import java.util.ArrayList;
import java.util.Scanner;

public class PokerGame {
    ArrayList<PokerHand> allHands;
    int numberOfPlayers;
    String reasonForWin;

    public PokerGame () {
        allHands = new ArrayList<>();
        numberOfPlayers = 0;
    }

    public int getNumberOfPlayers() {return numberOfPlayers;}
    public void setNumberOfPlayers(int numberOfPlayers) {this.numberOfPlayers = numberOfPlayers;}
    public ArrayList<PokerHand> getAllHands() {return allHands;}

    /**
     * Function to add a poker hand to the game
     * @param hand the poker hand to add
     */
    public void addPokerHand (PokerHand hand) {
        allHands.add(hand);
        numberOfPlayers++;
    }

    /**
     * Function to retrieve a certain number of poker hands
     * @param sc the scanner to use for reading inputs
     */
    public void getHands (Scanner sc) {
        System.out.print("Combien de personnes jouent : ");
        this.numberOfPlayers = sc.nextInt();
        sc.nextLine();
        for (int i = 1; i <= numberOfPlayers ; i++) {
            allHands.add(new PokerHand(TextDecomposer.createHand(sc)));
        }
    }

    /**
     * Function to find the best hands in the game
     * @return a list of the best hands
     */
    public ArrayList<PokerHand> findBestHand () {
        ArrayList<PokerHand> winner = new ArrayList<>();
        winner.add(allHands.getFirst());
        ArrayList<String> compare = winner.getFirst().comparePokerHands(allHands.get(1));
        reasonForWin = compare.getLast();
        for (int i = 1 ; i < numberOfPlayers; i++) {
            compare = winner.getFirst().comparePokerHands(allHands.get(i));
            switch (compare.getFirst()) {
                case "-1" :
                    winner.clear(); //DON'T ADD A BREAK
                    reasonForWin = compare.getLast();
                case "0" :
                    winner.add(allHands.get(i));
                default:
                    break;
            }
        }
        return winner;
    }
    /**
     * Function to display a list of poker hands
     * @param hands the list of poker hands
     */
    public void displayHands (ArrayList<PokerHand> hands) {
        String res = "";
        for (PokerHand hd : hands) {
            for (int i = 0 ; i< allHands.size() ; i++) {
                if (hd.equals(allHands.get(i))) {
                    res += "la main " + (i+1) + " ";
                }
            }
        }
        res += "gagne avec " + reasonForWin;
        System.out.println(res);
    }
 }
