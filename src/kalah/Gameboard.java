package kalah;

import com.qualitascorpus.testsupport.IO;

public class Gameboard {


    private static final String horizontalBorder = "+----+-------+-------+-------+-------+-------+-------+----+";
    private static final String horizontalSeparatingLine = "|    |-------+-------+-------+-------+-------+-------|   " +
            " |";

    private IO io;
    private Player[] players;
    private Kalah kalah;
    private VerticalBoard vertical;


    public Gameboard(IO io, Player[] players, Kalah kalah) {
        this.io = io;
        this.players = players;
        this.kalah = kalah;
    }


    public void print() {
        if (kalah.isBoardVertical) {
            vertical = new VerticalBoard(io, players, kalah);
            vertical.drawVerticalBoard();
        } else {
            drawHorizontalBoard();
        }
    }


    /*
        Player 1 row methods:
     */
    private void getPlayer1HouseSeeds(int[] player1houses, StringBuilder player1row) {
        for (int i = 0; i < player1houses.length; i++) {
            int numSeeds = player1houses[i];
            int houseDisplayNum = i + 1;
            String numSeedsLabel;

            if (numSeeds <= 9) {
                numSeedsLabel = " " + numSeeds; // Left padding for single-digit house values
            } else {
                numSeedsLabel = String.valueOf(numSeeds);
            }
            player1row.append(" ").append(houseDisplayNum).append("[").append(numSeedsLabel).append("] |");
        }
    }

    private String buildPlayer1Row(int player2StoreSeeds, int[] player1Houses) {
        StringBuilder player1row = new StringBuilder();
        String player2StoreSeedsLabel;

        if (player2StoreSeeds <= 9) {
            player2StoreSeedsLabel = " " + player2StoreSeeds;
        } else {
            player2StoreSeedsLabel = String.valueOf(player2StoreSeeds);
        }
        player1row.append("| ").append(player2StoreSeedsLabel).append(" |");
        getPlayer1HouseSeeds(player1Houses, player1row);
        player1row.append(" ").append("P1").append(" |");
        return player1row.toString();
    }


    private String getPlayer1Row() {
        int[] player1houses = players[0].getHouses();
        int player2StoreSeeds = players[1].getStore();
        return buildPlayer1Row(player2StoreSeeds, player1houses);
    }


    /*
        Player 2 row methods:
     */
    private void getPlayer2HouseSeeds(int[] player2houses, StringBuilder player2row) {
        for (int i = player2houses.length - 1; i >= 0; i--) {
            int numSeeds = player2houses[i];
            int houseDisplayNum = i + 1;
            String numSeedsLabel;
            if (numSeeds <= 9) {
                numSeedsLabel = " " + numSeeds;
            } else {
                numSeedsLabel = String.valueOf(numSeeds);
            }
            player2row.append(" ").append(houseDisplayNum).append("[").append(numSeedsLabel).append("] |");
        }
    }


    private String buildPlayer2Row(int player1StoreSeeds, int[] player2Houses) {
        StringBuilder player2row = new StringBuilder();
        String player1StoreSeedsLabel;

        if (player1StoreSeeds <= 9) {
            player1StoreSeedsLabel = " " + player1StoreSeeds;
        } else {
            player1StoreSeedsLabel = String.valueOf(player1StoreSeeds);
        }

        player2row.append("| ").append("P2").append(" |");
        getPlayer2HouseSeeds(player2Houses, player2row);
        player2row.append(" ").append(player1StoreSeedsLabel).append(" |");
        return player2row.toString();
    }


    private String getPlayer2Row() {
        int[] player2houses = players[1].getHouses();
        int player1StoreSeeds = players[0].getStore();
        return buildPlayer2Row(player1StoreSeeds, player2houses);
    }


    private void drawHorizontalBoard() {

    /*
                    Horizontal Kalah board layout
        +----+-------+-------+-------+-------+-------+-------+----+
        | P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |
        |    |-------+-------+-------+-------+-------+-------|    |
        |  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |
        +----+-------+-------+-------+-------+-------+-------+----+
     */
        io.println(horizontalBorder);
        io.println(getPlayer2Row());
        io.println(horizontalSeparatingLine);
        io.println(getPlayer1Row());
        io.println(horizontalBorder);
    }

}





