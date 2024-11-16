package CardHand;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class PokerHand extends Hand {

    //variables to get the combination value
    //separation value:
    private static final int SEPARATION = 100;
    //could potentially replace the seperationSquared with a Math.pow(seperation, 2) for ease of reading
    private static final int SEPARATION_SQUARED = SEPARATION * SEPARATION;
    //straight and flush variables
    int inARow = 0;
    int previous = -1;
    int flush = 0;
    int found = 0;
    //stores the value of a pair or triple found, in case we have two pairs or a full house
    int pair = 0;
    int triple = 0;


    //strength of poker hand
    private double strengthOfHand = 0.0;
    public double getStrengthOfHand() {return strengthOfHand;}


    //constants to track the size of the valueTable and suitTable,
    //which store the maximal different values and suites of cards in a deck
    private static final int NUMBER_OF_VALUES = 14;
    private static final int NUMBER_OF_SUITES = 4;


    //the actual hand and card tables to store suites and values
    Hand myPokerHand;


    //table after removing the combos
    private final ArrayList<Integer> valueTableNonCombo;
    public ArrayList<Integer> getValueTableNonCombo() { return valueTableNonCombo; }
    private final ArrayList<Integer> suiteTableNonCombo;
    public ArrayList<Integer> getSuitTableNonCombo() {
        return suiteTableNonCombo;
    }
    private final ArrayList<Integer> flushCardTable = new ArrayList<>(Collections.nCopies(10,0));

    //the string we want to print to represent our Poker Hand
    private String myPokerHandText;
    public String getPokerHandText(){
        return myPokerHandText;
    }

    //an array of strings to represent why a hand won
    private final ArrayList<String> reasonForWin ;
    public ArrayList<String> getReasonForWin() {
        return reasonForWin;
    }


    //constructor for PokerHand class
    public PokerHand(Hand aPokerHand) {
        myPokerHand = aPokerHand;
        reasonForWin = new ArrayList<>();
        suiteTableNonCombo = new ArrayList<>(Collections.nCopies(NUMBER_OF_SUITES, 0));
        valueTableNonCombo = new ArrayList<>(Collections.nCopies(NUMBER_OF_VALUES, 0));
        combinationValue();
    }

    /**
     * method to find the winner between this hand and another
     * @param anotherPokerHand the hand to compare to
     * @return an arraylist of strings, the first field indicates who won or a tie (1,0,-1) and the second why the winner won (string of the combo, or the card)
     */
    public ArrayList<String> comparePokerHands(PokerHand anotherPokerHand) {
        double enemyStrength = anotherPokerHand.getStrengthOfHand();
        ArrayList<String> returnValues = new ArrayList<>();
        if (strengthOfHand > enemyStrength){
            returnValues.add("1");
            for (int decimalShift = 0; decimalShift < 6; decimalShift++){
                if (strengthOfHand - enemyStrength >= 1/(Math.pow(SEPARATION, decimalShift))){
                    //checks if the combo was enough to win, and then iterates over all potential cards outside of the combo that could have caused the win
                    returnValues.add(reasonForWin.get(decimalShift));
                    break;
                }
            }
        }
        else if (strengthOfHand == enemyStrength){
            returnValues.add("0");
        }
        else{
            returnValues.add("-1");
            for (int decimalShift = 0; decimalShift < 6; decimalShift++){
                if (enemyStrength - strengthOfHand >= 1/(Math.pow(SEPARATION, decimalShift))){
                    //checks if the combo was enough to win, and then iterates over all potential cards outside of the combo that could have caused the win
                    returnValues.add(anotherPokerHand.getReasonForWin().get(decimalShift));
                    break;
                }
            }
        }
        return returnValues;
    }

    /**
     * Method who find if there is a royal flush
     * @return true if there is a royal flush, false otherwise, and updates the strength of hand, poker hand text, and reason for win accordingly
     */
    private boolean findRoyalFlush(){
        if (inARow == 5 && previous == 13 && flush == 1){
            strengthOfHand = (double) 9 * SEPARATION_SQUARED;
            myPokerHandText = ("Quinte flush royale");
            reasonForWin.add("Quinte flish royale");
            return true;
        }
        return false;
    }

    /**
     * Method who find if there is a straight flush
     * @return true if there is a straight flush, false otherwise, and updates the strength of hand, poker hand text, and reason for win accordingly
     */
    private boolean findStraightFlush(){
        if (inARow == 5 && flush == 1){
            strengthOfHand = ((double) SEPARATION_SQUARED * 8) + SEPARATION * (previous + 1);
            myPokerHandText = ("Quinte flush au " + Card.convCardIntToStr(previous + 1));
            reasonForWin.add("Quinte flush au " + Card.convCardIntToStr((previous + 1)));
            return true;
        }
        return false;
    }

    /**
     * Method who find if there is a straight
     */
    private void calculateStraight(){
        if (inARow == 5){
            strengthOfHand = ((double) SEPARATION_SQUARED * 4) + SEPARATION * (previous + 1);
            myPokerHandText = ("Suite au " + Card.convCardIntToStr(previous + 1));
            reasonForWin.add("Suite au " + Card.convCardIntToStr(previous + 1));

        }

    }


    private void findNonStraightFlush(){
        if (strengthOfHand == 0 && flush == 1){
            strengthOfHand = (double) 5 * SEPARATION_SQUARED;
            myPokerHandText = ("Couleur");
            reasonForWin.add("Couleur");
            int flushCardsAdded = 0;
            for (int flushCardCounter = 0; flushCardCounter < flushCardTable.size(); flushCardCounter++) {

                if (flushCardsAdded != 5 && (flushCardTable.get(flushCardTable.size() - flushCardCounter - 1) != 0) && (flushCardTable.get(flushCardTable.size() - flushCardCounter - 1) != 1)){
                    strengthOfHand += ((flushCardTable.get(flushCardTable.size() - flushCardCounter - 1)) / (Math.pow(SEPARATION, flushCardsAdded + 1)));
                    reasonForWin.add(", " + Card.convCardIntToStr(flushCardTable.get(flushCardTable.size() - flushCardCounter - 1)));
                    myPokerHandText = myPokerHandText + ", " + Card.convCardIntToStr(flushCardTable.get(flushCardTable.size() - flushCardCounter - 1));
                    flushCardsAdded++;
                }
                else {
                    flushCardCounter++;
                }

            }
        }
    }

    /**
     *
     * @return 1 if there is a flush, 0 otherwise
     */
    private int findFlush(){
        for (int j = 0; j < NUMBER_OF_SUITES; j++) {
            if (myPokerHand.getSuitTable().get(j) == 5) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Method who find if there is a four of a kind
     * @param currentCardValue the value of the current card
     * @return true if there is a four of a kind, false otherwise, and updates the strength of hand, poker hand text, and reason for win accordingly
     */
    private boolean findFourOfAKind(int currentCardValue)
    {
        if (myPokerHand.getValueTable().get(currentCardValue) == 4) {
            strengthOfHand = ((double) 7 * SEPARATION_SQUARED) + (currentCardValue + 1) * SEPARATION;
            myPokerHandText = ("Carré de " + Card.convCardIntToStr(currentCardValue + 1));
            reasonForWin.add("Carré de " + Card.convCardIntToStr(currentCardValue + 1));
            return true;
        }
        return false;
    }

    /**
     * Method who find if there is a three of a kind
     * @param currentCardValue the value of the current card
     * @return true if there is a three of a kind, false otherwise, and updates the strength of hand, poker hand text, and reason for win accordingly
     */
    private boolean findThreeOfAKind(int currentCardValue)
    {
        if (myPokerHand.getValueTable().get(currentCardValue) == 3) {
            triple = currentCardValue + 1;
            if (pair > 0) {
                //means we already have a pair, the value of the pair is in int pair
                strengthOfHand = ((double) SEPARATION_SQUARED * 6) + SEPARATION * (currentCardValue + 1) + pair;
                myPokerHandText = ("Full : brelan de " + Card.convCardIntToStr(currentCardValue + 1) +" et pair de "  + Card.convCardIntToStr(pair));
                reasonForWin.add("Full : brelan de " + Card.convCardIntToStr(currentCardValue + 1) + " et pair de "  + Card.convCardIntToStr(pair));
            }
            else {
                strengthOfHand = ((double) (SEPARATION_SQUARED * 3)) + SEPARATION * (currentCardValue + 1);
                myPokerHandText = ("Brelan de " + Card.convCardIntToStr(currentCardValue + 1));
                reasonForWin.add("Brelan de " + Card.convCardIntToStr(currentCardValue + 1));
            }
            return true;
        }
        return false;
    }

    /**
     * Method who find if there is a two of a kind
     * @param currentCardValue the value of the current card
     * @return true if there is a two of a kind, false otherwise, and updates the strength of hand, poker hand text, and reason for win accordingly
     */
    private boolean findTwoOfAKind( int currentCardValue){
        if (myPokerHand.getValueTable().get(currentCardValue) == 2) {
            if (triple > 0) {
                strengthOfHand = ((double) SEPARATION_SQUARED * 6) + SEPARATION * (triple) + currentCardValue + 1;
                myPokerHandText = ("Full : brelan de " + Card.convCardIntToStr(triple)  +" et pair de "  + Card.convCardIntToStr(currentCardValue + 1));
                reasonForWin.add("Full : brelan de " + Card.convCardIntToStr(triple) + " et pair de "  + Card.convCardIntToStr(currentCardValue + 1));
            } else if (pair > 0) {
                strengthOfHand = SEPARATION_SQUARED * 2 + SEPARATION * (currentCardValue + 1) + pair;
                myPokerHandText = ("Double paire de " + Card.convCardIntToStr(currentCardValue + 1) + " et de " + Card.convCardIntToStr(pair));
                reasonForWin.add("Double paire de " + Card.convCardIntToStr(currentCardValue + 1) + " et de "  + Card.convCardIntToStr(pair));
            } else {
                strengthOfHand = ((double) SEPARATION_SQUARED * 1) + SEPARATION * (currentCardValue + 1);
                pair = currentCardValue + 1;
                myPokerHandText = ("Paire de " + Card.convCardIntToStr(currentCardValue + 1));
                reasonForWin.add("Paire de " + Card.convCardIntToStr(currentCardValue + 1));
            }
            return true;
        }
        return false;
    }

    /**
     * Method who find the straight
     * @param currentCardValue the value of the current card
     *
     * updates variables that store the highest card in a straight and the prescence of a straight
     */
    private void findStraight(int currentCardValue){
        //if (currentCardValue == 1) {
            //storing the values of the cards for the flush, in decreasing order (fill from top of array)
            //TODOOOOOOOOOOOOOOOOOO
            if (inARow == 5){
                return;
            }
            //testing for the straight
            for (int nbOfcardsWithValue = 0; nbOfcardsWithValue < myPokerHand.getValueTable().get(currentCardValue); nbOfcardsWithValue++) {
                flushCardTable.set(found, currentCardValue+1);
                found += 1;
            }
            if (myPokerHand.getValueTable().get(currentCardValue) == 1) {
                if ((previous == -1) || (Math.abs(previous - currentCardValue) != 1)) {
                    previous = currentCardValue;
                    inARow = 1;
                }
                else {
                    previous = currentCardValue;
                    inARow += 1;
                }

            }
            else {
                inARow = 0;
            }


        //}
    }

    /**
     *
     * @param hand the hand of the player

     * updates the strength of hand, poker hand text, and reason for win according to the non combo cards
     */
    private void doNonComboCards(Hand hand) throws IndexOutOfBoundsException{

        Hand nonComboCardsHand = findNonComboCards(strengthOfHand, hand);
        resetTables(valueTableNonCombo, suiteTableNonCombo);
        storeCards(nonComboCardsHand, valueTableNonCombo, suiteTableNonCombo);

        ArrayList<Integer> aValueTable = (ArrayList<Integer>) valueTableNonCombo.clone();
        ArrayList<Integer> aSuiteTable = (ArrayList<Integer>) suiteTableNonCombo.clone();
        //loop until all cards have been added to result:
        int numberOfCardsSeen = 0;
        //index for the table, we start at the top
        int tableIndex = aValueTable.size() - 1;
        while (numberOfCardsSeen < nonComboCardsHand.getSize()){
            if (tableIndex < 0 || tableIndex >= aValueTable.size()) {
                throw new IndexOutOfBoundsException("something went wrong because we either missed a card, or started too high in the array, weird");
            }
            //now we test to see if there is no card of that value
            else if (aValueTable.get(tableIndex) < 1){
                tableIndex -= 1;
            }
            //so we have cards of the value we are looking at, we want to add them to result
            else {
                numberOfCardsSeen += 1;
                //the division here allows us to seperate each 'card' in a bijective way
                strengthOfHand += (tableIndex+1) / (Math.pow(SEPARATION, numberOfCardsSeen));//j'ai ajouté 1
                strengthOfHand = Math.round(strengthOfHand * 1000000.0) / 1000000.0; //par exemple 10800.141302999999 devient 10800141302.999999 et on arrondi au supérieur, cependant je ne suis pas sûr de pourquoi ca fait 9999
                aValueTable.set(tableIndex, aValueTable.get(tableIndex) - 1);
                myPokerHandText = myPokerHandText + ", " + Card.convCardIntToStr(tableIndex+1);
                reasonForWin.add(Card.convCardIntToStr(myPokerHand.getValueTable().get(tableIndex)));
            }
        }
    }

    /**
     * Méthode qui calcule la valeur d'une main en fonction des combinaisons qu'elle contient
     */
    public void combinationValue() {
        //tables in which we store the number of cards in the hand, per value and per suite
        resetTables(myPokerHand.getValueTable(), myPokerHand.getSuitTable());
        storeCards(myPokerHand, myPokerHand.getValueTable(), myPokerHand.getSuitTable());
        strengthOfHand = 0;
        //variables for straights ; this keeps how many in a row we had, and if we have actually seen a card yet

        //variables for flush; tracks if we found a flush, ie 5 of the same suit, in case we also have a straight
        //ArrayList<Integer> flushCards = new ArrayList<>(5);

        /* variable that stores the power of a hand, so that a hand with a higher power always wins
        the power is calculated with the formula
        (str of a combination) * seperation_squared +
        (int value of highest card in the combination) * seperation +
        (int value of highest card in second combination (ie in the (weaker) pair in case of full house or two pair) +
        ...repeat the following line for all cards remaining:
        (int value of ith remaining card) / seperation ** i
        */


        //look for a flush first
        flush = findFlush();
        //iterate over all possible card values, which are stored in the valueTable
        for (int currentCardValue = 0; currentCardValue < NUMBER_OF_VALUES; currentCardValue++) {
            //tests for a flush, cause if we have a flush we can't have a pair, triple, or four of a kind
            if (flush != 1 && currentCardValue >= 1) {
                //finds 4 of a kind, 3 of a kind, pair, full house, and two pairs
                if (findFourOfAKind(currentCardValue))
                {
                    //do nothing
                }
                else if (findThreeOfAKind(currentCardValue))
                {
                    //do nothing
                }
                else if (findTwoOfAKind(currentCardValue))
                {
                   //do nothing
                }
            }
            findStraight(currentCardValue);
        }
        //tests right after we add or reset our sequence, to not have lost the best card value of the straight
        //if we have a straight, testing for straight flush or royal flush
        if (!(findRoyalFlush())){
            if (!(findStraightFlush())) {
                calculateStraight();
            }
        }
        findNonStraightFlush();
        if (strengthOfHand == 0){
            reasonForWin.add("No Combo");
        }
        doNonComboCards(findNonComboCards(strengthOfHand, myPokerHand));

        /*
        we have now found all the cards that are in the combinations within the poker hand
        the next step is to find all the cards outside of the combinations, and add them to the double
        */

        //hand of non combo cards which we will put into an array to store the values in a sorted way


        //we should have gotten here, a value that represents the strength of a poker hand, without ambiguity
        //this will allow us to compare any poker hand with another
    }


    /**
     * Methode who find the cards who are not in the combination
     * @param comboValue the value of the combination
     * @param myHand the hand of the player
     * @return the hand of the player without the combination
     */
    public Hand findNonComboCards(double comboValue, Hand myHand) {
        //this method only works for hand sizes of 5. to increase would need to change the logic, testing for all combos
        Hand handOfNonComboCards = new Hand();
        //get the hundreds to find which combo
        int combo = (int) comboValue / SEPARATION_SQUARED;
        //testing for all combos that take all the cards, in this case we just return an empty hand
        if ((combo == 9) || (combo == 8) || (combo == 6) || (combo == 5) || (combo == 4)) {
            return handOfNonComboCards;
        }

        //test for a 4 of a kind, 3 of a kind, or pair; in this case, find the value of the combo, and return all cards of wrong value
        else if ((combo == 7) || (combo == 3) || (combo == 1)) {
            //get the value of the cards in the combo
            int value1 = (int) ((comboValue - SEPARATION_SQUARED * combo) / SEPARATION);
            value1 = value1 % 13;
            //int value2 = (int) ((comboValue - SEPARATION_SQUARED * combo - SEPARATION * value1) / SEPARATION);
            for (int i = 0; i < myHand.getSize(); i++) {
                if (((myHand.getCard(i).getIntValue()) % 13) != value1) {
                    handOfNonComboCards.addCard(myHand.getCard(i));
                }
            }
        }
        //test for a 2 pair
        else if (combo == 2) {
            //get the value of the first pair
            int firstPairValue = (int) ((comboValue - SEPARATION_SQUARED * combo) / SEPARATION);
            //get the value of the second pair
            int secondPairValue = (int) ((comboValue - SEPARATION_SQUARED * combo - SEPARATION * firstPairValue));
            for (int i = 0; i < myHand.getSize(); i++) {
                if ((myHand.getCard(i).getIntValue() != firstPairValue) && (myHand.getCard(i).getIntValue() != secondPairValue) && (myHand.getCard(i).getIntValue() != firstPairValue+13) && (myHand.getCard(i).getIntValue() != secondPairValue+13) && (myHand.getCard(i).getIntValue() != firstPairValue-13) && (myHand.getCard(i).getIntValue() != secondPairValue-13)) {
                    handOfNonComboCards.addCard(myHand.getCard(i));
                }
            }
        }
        //we have no combinations, so we the non-combo hand is just our normal hand
        else {
            handOfNonComboCards = myHand;
        }
        return handOfNonComboCards;
    }

    public String toString() {
        return getPokerHandText();
    }
}

