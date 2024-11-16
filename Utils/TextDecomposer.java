package Utils;

import CardHand.Card;
import CardHand.Hand;

import java.util.Scanner;

public class TextDecomposer {
    private static int numberOfHands = 1;

    /**
     * Créee un objet Hand à partir d'une ligne de texte
     * @param scanner le scanner à utiliser pour lire la ligne
     * @return la main crée
     */
    public static Hand createHand(Scanner scanner) {
        System.out.print("Main "+ numberOfHands +": ");
        String line = scanner.nextLine();
        String[] words = line.split("\\s+");  // Uses "\\s+" to split by one or more spaces
        Hand hand = new Hand();
        for (String word : words) {
            String value = word.substring(word.length() - 2);
            String suit = word.substring(0, word.length() - 2);
            hand.addCard(new Card(suit, value));
        }
        numberOfHands++;
        return hand;
    }
}

