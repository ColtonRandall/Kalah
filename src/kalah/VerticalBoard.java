package kalah;

import com.qualitascorpus.testsupport.IO;

public class VerticalBoard {

    private static final String verticalOuterBorder = "+---------------+";
    private static final String verticalInnerBorder = "+-------+-------+";

    private IO io;
    private Player[] players;
    public Kalah kalah;

    public VerticalBoard(IO io, Player[] players, Kalah kalah) {
        this.io = io;
        this.players = players;
        this.kalah = kalah;
    }

    /*
        Print P2 label and store
            +---------------+
            |       | P2  0 |
            +-------+-------+
     */

    private void printUpperVerticalBoard(int player2StoreSeeds) {
        io.println(verticalOuterBorder);
        // Use ternary operator to determine spacing required for single and double-digit values
        io.println("|       | P2 " + (player2StoreSeeds >= 10 ? "" : " ") + player2StoreSeeds + " |");
        io.println(verticalInnerBorder);
    }


    /*
       Print player houses and seeds

        | 1[ 4] | 6[ 4] |
        | 2[ 4] | 5[ 4] |
        | 3[ 4] | 4[ 4] |
        | 4[ 4] | 3[ 4] |
        | 5[ 4] | 2[ 4] |
        | 6[ 4] | 1[ 4] |
     */


    private void printMiddleVerticalBoard(int houseRowLength, int[] player1houses, int[] player2houses) {

        for (int i = 0; i < houseRowLength; i++) {
            int max_index = (houseRowLength - 1);
            int p2Index = max_index - i;
            int player1Seeds = player1houses[i];
            int player2Seeds = player2houses[p2Index];
            int p1HouseNum = i + 1;
            int p2HouseNum = p2Index + 1;

            // Ternary operator used to determine spacing for single and double-digit seed values
            io.println("| " + p1HouseNum + "[" + (player1Seeds >= 10 ? "" : " ") + player1Seeds + "] | " + p2HouseNum +
                    "[" + (player2Seeds >= 10 ? "" : " ") + player2Seeds + "] |");
        }
    }


    /*
        Print P1 label and store
            +-------+-------+
            | P1  1 |       |
            +---------------+
     */
    private void printLowerVerticalBoard(int player1StoreSeeds) {
        io.println(verticalInnerBorder);
        io.println("| P1 " + (player1StoreSeeds >= 10 ? "" : " ") + +player1StoreSeeds + " |       |");
        io.println(verticalOuterBorder);
    }


    public void drawVerticalBoard() {

    /*
        Vertical Kalah board layout
            +---------------+
            |       | P2  0 |
            +-------+-------+
            | 1[ 4] | 6[ 4] |
            | 2[ 4] | 5[ 4] |
            | 3[ 4] | 4[ 4] |
            | 4[ 4] | 3[ 4] |
            | 5[ 4] | 2[ 4] |
            | 6[ 4] | 1[ 4] |
            +-------+-------+
            | P1  0 |       |
            +---------------+
     */

        int[] player1houses = players[0].getHouses();
        int[] player2houses = players[1].getHouses();
        int player1StoreSeeds = players[0].getStore();
        int player2StoreSeeds = players[1].getStore();
        int houseRowLength = player1houses.length;

        printUpperVerticalBoard(player2StoreSeeds);
        printMiddleVerticalBoard(houseRowLength, player1houses, player2houses);
        printLowerVerticalBoard(player1StoreSeeds);

    }
}
