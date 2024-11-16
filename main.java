import CardHand.PokerHand;
import PokerGame.PokerGame;

import java.util.ArrayList;
import java.util.Scanner;

                       /*.,,uod8B8bou,,.
        ..,uod8BBBBBBBBBBBBBBBBRPFT?l!i:.
        ,=m8BBBBBBBBBBBBBBBRPFT?!||||||||||||||
        !...:!TVBBBRPFT||||||||||!!^^""'   ||||
        !.......:!?|||||!!^^""'            ||||
        !.........||||                     ||||
        !.........||||  ##                 ||||
        !.........||||                     ||||
        !.........||||                     ||||
        !.........||||                     ||||
        !.........||||                     ||||
        `.........||||                    ,||||
        .;.......||||               _.-!!|||||
        .,uodWBBBBb.....||||       _.-!!|||||||||!:'
        !YBBBBBBBBBBBBBBb..!|||:..-!!|||||||!iof68BBBBBb....
        !..YBBBBBBBBBBBBBBb!!||||||||!iof68BBBBBBRPFT?!::   `.
        !....YBBBBBBBBBBBBBBbaaitf68BBBBBBRPFT?!:::::::::     `.
        !......YBBBBBBBBBBBBBBBBBBBRPFT?!::::::;:!^"`;:::       `.
        !........YBBBBBBBBBBRPFT?!::::::::::^''...::::::;         iBBbo.
`..........YBRPFT?!::::::::::::::::::::::::;iof68bo.      WBBBBbo.
  `..........:::::::::::::::::::::::;iof688888888888b.     `YBBBP^'
        `........::::::::::::::::;iof688888888888888888888b.     `
        `......:::::::::;iof688888888888888888888888888888b.
        `....:::;iof688888888888888888888888888888888899fT!
        `..::!8888888888888888888888888888888899fT|!^"'
        `' !!988888888888888888888888899fT|!^"'
        `!!8888888888888888899fT|!^"'
        `!988888888899fT|!^"'
        `!9899fT|!^"'
        `!^"'*/

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PokerGame game = new PokerGame();
        game.getHands(sc);
        ArrayList< PokerHand> winner = game.findBestHand();
        game.displayHands(winner);
        sc.close();
    }
}

