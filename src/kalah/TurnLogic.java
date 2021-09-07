package kalah;

import com.qualitascorpus.testsupport.IO;

public class TurnLogic {

    private Kalah kalah;
    private EndGameLogic endGame;
    private Gameboard gameboard;
    private ValidateInput valInput;
    private IO io;

    public TurnLogic(Kalah kalah, IO io) {
        this.kalah = kalah;
        this.io = io;
        this.gameboard = new Gameboard(io, kalah.players, kalah);
        this.endGame = new EndGameLogic(io, gameboard, kalah.players);
        this.valInput = new ValidateInput(io, gameboard);
    }

    private Player getCurrentPlayer(boolean isPlayer1Turn) {
        if (isPlayer1Turn) {
            return kalah.players[0]; // player 1
        } else {
            return kalah.players[1]; // player 2
        }
    }

    private int[] getCurrentRow(boolean isPlayer1Row) {
        if (isPlayer1Row) {
            return kalah.players[0].getHouses(); // player 1's row
        } else {
            return kalah.players[1].getHouses(); // player 2's row
        }
    }

    private int[] getOtherRow(boolean isPlayer1Row) {
        if (isPlayer1Row) {
            return kalah.players[1].getHouses(); // player 2's row
        } else {
            return kalah.players[0].getHouses(); // player 1's row
        }

    }

    public boolean takeTurn(boolean isPlayer1Turn) {
        gameboard.print();
        Player currentPlayer = getCurrentPlayer(isPlayer1Turn);
        boolean isPlayer1Row = isPlayer1Turn;
        int[] currentRow = getCurrentRow(isPlayer1Row);
        int selectedHouseIndex;

        if (housesAreEmpty(currentPlayer)) return false;

        if(kalah.isUsingBot && currentPlayer.getPlayerNumber() == 2){ // Player 2 = BMF Robot
            selectedHouseIndex = kalah.robot.bestMoveFirst();
        }else{ // Player 2 = human player
            io.print("Player P" + currentPlayer.getPlayerNumber() + "'s turn - Specify house number or 'q' to quit: ");
            selectedHouseIndex = valInput.validateInputLogic(currentPlayer); // range  [-1,0,1,2,3,4,5,6]
        }

        if (checkPlayerQuit(selectedHouseIndex)) return false; // Exit the game if the player quits

        int seedsInHand = currentRow[selectedHouseIndex]; // 'Pick up' seeds from target house

        currentRow[selectedHouseIndex] = 0; // Player grabs seeds from house, set num seeds in house to 0
        int nextPlantingHouseIndex = selectedHouseIndex + 1; // Adding a seed to each neighbouring house
        int lastPlantingHouseIndex = selectedHouseIndex;

        while (seedsInHand != 0) { // While the player has seeds

        /*
            If the following house is within the player's own row, we want to decrease the amount
            of seeds in the payer's hand, move to the next house (increment) and increase each
            subsequent house by one seed.
         */
            if (nextPlantingHouseIndex < currentRow.length) { // If next planting house index is in current row
                seedsInHand--;
                currentRow[nextPlantingHouseIndex]++;
                lastPlantingHouseIndex++; // Last house where a seed was placed
                nextPlantingHouseIndex++; // Next house where a seed WILL be placed
            } else { // We are entering a store
                if (isPlayer1Row == isPlayer1Turn) { // If the current player is in their OWN store
                    seedsInHand--;
                    currentPlayer.setStore(currentPlayer.getStore() + 1); // Add one seed into the player's store
                    if(seedsInHand == 0){ // If the seed planted in the store was the last one you get another turn
                        return takeTurn(isPlayer1Turn); // Additional move
                    }
                }
                isPlayer1Row = !isPlayer1Row; // Swap current row
                currentRow = getCurrentRow(isPlayer1Row); // Set the current row to the player 2's row
                lastPlantingHouseIndex = -1;
                nextPlantingHouseIndex = 0; // Start seeding from the FIRST house of the other row
            }
        }
        /*
            Check conditions for a capture.
            The last seed must have been planted on the current player's row
         */
        if (isPlayer1Row == isPlayer1Turn && lastPlantingHouseIndex != -1 && currentRow[lastPlantingHouseIndex] == 1){
            // Only computing the parts required for the third condition if the first two are met
            checkAndCapture(currentPlayer, isPlayer1Row, currentRow, lastPlantingHouseIndex);
        }
        return true;
    }

    // Condition 1 for a game ending - A player quits
    private boolean checkPlayerQuit(int selectedHouseIndex) {
        if (selectedHouseIndex == -1) { // Player quits - end game and don't print score
            endGame.printFinalResult(false); // Don't print the board after a player quits
            return true;
        }
        return false;
    }


    // Condition 2 for game end - All houses in a player's row are empty
    private boolean housesAreEmpty(Player currentPlayer) {
        boolean housesHaveSeeds = endGame.housesHaveSeeds(currentPlayer); // checks if the houses are empty
        if (!housesHaveSeeds) { // Player has no seeds
            endGame.printFinalResult(true); // End game and print score
            return true;
        }
        return false;
    }


    private void checkAndCapture(Player currentPlayer, boolean isPlayer1Row, int[] currentRow, int lastPlantingHouseIndex) {
        int oppositeHouseIndex = (currentRow.length - 1) - lastPlantingHouseIndex;
        int[] otherRow = getOtherRow(isPlayer1Row);
        int oppositeHouseSeeds = otherRow[oppositeHouseIndex];
        int storeValueAfterCapture = currentPlayer.getStore();

        // Condition for capture - There must be seeds in the house directly opposite the last house seeded.
        if (oppositeHouseSeeds > 0) {
            storeValueAfterCapture += currentRow[lastPlantingHouseIndex]; // Put the one seed in the last house in your store
            currentRow[lastPlantingHouseIndex] = 0; // Set current player house to zero
            storeValueAfterCapture += otherRow[oppositeHouseIndex]; // Put all the seeds from opponent house in store
            otherRow[oppositeHouseIndex] = 0; // Set other player house to zero
            currentPlayer.setStore(storeValueAfterCapture); // Set current player's store to the updated value
        }
    }


}

