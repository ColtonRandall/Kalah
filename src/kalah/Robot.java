package kalah;

import com.qualitascorpus.testsupport.IO;


public class Robot {

    private final int rowLength;
    private final Player player1;
    private final Player player2;
    private IO io;
    public Kalah kalah;

    public Robot(IO io, Player[] players, Kalah kalah) {
        this.io = io;
        this.player1 = players[0];
        this.player2 = players[1];
        this.kalah = kalah;
        this.rowLength = kalah.players[1].getHouses().length;
    }

    public int bestMoveFirst() {
        return bmfLogic();
    }


    // Check move options in order of 'best move'
    private int bmfLogic() {
        int chosenHouse;
        chosenHouse = additionalMove();
        if (chosenHouse == -1) {
            chosenHouse = captureMove();
        }
        if (chosenHouse == -1) {
            chosenHouse = legalMove();
        }
        return chosenHouse;
    }


    // BMF condition 1: take a turn that leads to an extra move
    private int additionalMove() {
        int chosenHouse = -1; // return -1 by default since it is outside the array
        for (int i = 0; i < rowLength; i++) {
            int seeds = player2.getHouses()[i];
            if (seeds == 0) { // Don't pick up from a house with no seeds
                continue;
            }
            int targetSeeds = rowLength - i;
            if (seeds % (2 * rowLength + 1) == targetSeeds) {
                chosenHouse = i;
                io.println("Player P2 (Robot) chooses house #" + (chosenHouse + 1) + " because it leads to an " +
                        "extra move");
                break;
            }
        }
        return chosenHouse;
    }


    // BMF condition 2: take a turn that leads to a capture
    private int captureMove() {
        int chosenHouse = -1;


            // Found an empty house
            for (int requiredHouseForCapture = 0; requiredHouseForCapture < rowLength; requiredHouseForCapture++) {
                int distToEnd = (rowLength - 1) - requiredHouseForCapture; // number of houses to store

                for (int emptyHouse = 0; emptyHouse < rowLength; emptyHouse++) {
                    int seeds = player2.getHouses()[emptyHouse];


                    if (seeds != 0) { // Looking for empty house
                        continue;
                    }


                if (emptyHouse == requiredHouseForCapture) {
                    continue;
                }

                // If the required amount of seeds falls before the empty house
                if (requiredHouseForCapture < emptyHouse) {

                    if (player1.getHouses()[rowLength - 1 - emptyHouse] == 0) { // Check opposite house has seeds
                        continue;
                    }

                    // Check all houses smaller (before) empty house
                    if (emptyHouse - requiredHouseForCapture == player2.getHouses()[requiredHouseForCapture]) {
                        // make capture of emptyHouse, using requiredHouseForCapture
                        chosenHouse = requiredHouseForCapture;
                        io.println("Player P2 (Robot) chooses house #" + (chosenHouse + 1) + " because it leads to a capture");
                        return chosenHouse;
                    }
                }

                // If required seeds falls AFTER the empty house
                if (requiredHouseForCapture > emptyHouse) {
                    int requiredSeeds = distToEnd + 7 + (emptyHouse + 1);

                    // Check houses bigger (after) for a wrap-around capture
                    if (requiredSeeds == player2.getHouses()[requiredHouseForCapture]) {
                        // make capture of emptyHouse, using requiredHouseForCapture
                        chosenHouse = requiredHouseForCapture;
                        io.println("Player P2 (Robot) chooses house #" + (chosenHouse + 1) + " because it leads to a capture");
                        return chosenHouse;
                    }
                }
            }
        }
        return chosenHouse;
    }


    private int legalMove() {
        int chosenHouse = -1;
        for (int i = 0; i < rowLength; i++) {
            int seeds = player2.getHouses()[i];
            if (seeds > 0) {
                chosenHouse = i;
                io.println("Player P2 (Robot) chooses house #" + (chosenHouse + 1) + " because it is the first legal move");
                break;
            }
        }
        return chosenHouse;
    }
}
