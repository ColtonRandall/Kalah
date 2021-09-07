package kalah;

import com.qualitascorpus.testsupport.IO;

public class EndGameLogic {

    private IO io;
    private Gameboard gameboard;
    private Player[] players;

    public EndGameLogic(IO io, Gameboard gameboard, Player[] players) {
        this.io = io;
        this.gameboard = gameboard;
        this.players = players;
    }

    // If houses have seeds, don't end the game
    public boolean housesHaveSeeds(Player currentPlayer) {
        int[] playerHouses = currentPlayer.getHouses();
        int numSeedsInPlayerHouses = 0;
        for (int seedsInHouse : playerHouses) {
            numSeedsInPlayerHouses += seedsInHouse;
        }
        return !(numSeedsInPlayerHouses == 0); // Game is running if there are seeds in any house (sum is non-zero)
    }


    public void printFinalResult(boolean printScore) {
        io.println("Game over");
        gameboard.print();

        if (printScore) {
            int p1Score = players[0].getScore();
            int p2Score = players[1].getScore();
            io.println("\tplayer 1:" + p1Score);
            io.println("\tplayer 2:" + p2Score);

            if (p1Score > p2Score) {
                io.println("Player 1 wins!");
            } else if (p1Score < p2Score) {
                io.println("Player 2 wins!");
            } else {
                io.println("A tie!");
            }
        }
    }
}
