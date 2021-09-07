package kalah;

import com.qualitascorpus.testsupport.IO;

public class ValidateInput {

    private boolean gettingMove;
    private String input;
    private Player currentPlayer;
    private IO io;
    private Gameboard gameboard;
    private int selectedHouseNumber;

    public ValidateInput(IO io, Gameboard gameboard) {
        this.io = io;
        this.gameboard = gameboard;
    }

    public int validateInputLogic(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        gettingMove = true;
        while (gettingMove) {
            input = io.readFromKeyboard("");
            checkForQuit();
            if (!gettingMove) {
                break;
            }
            ;
            checkForInvalidInput();
        }
        return selectedHouseNumber - 1;
    }

    public void checkForInvalidInput() {

        try {
            selectedHouseNumber = Integer.parseInt(input);
            if (selectedHouseNumber < 1 || selectedHouseNumber > 6) {
                throw new IllegalArgumentException();
            }

            // Set the number of seeds in the selected house
            int selectedHouseSeeds = currentPlayer.getHouses()[selectedHouseNumber - 1];

            if (selectedHouseSeeds == 0) {
                throw new EmptyHouseException();
            }

            // End the user's move if input is valid
            gettingMove = false;
        } catch (EmptyHouseException e) {
            io.println("House is empty. Move again.");
            gameboard.print();
            io.print("Player P" + currentPlayer.getPlayerNumber() + "'s turn - Specify house number or 'q' to quit: ");

        } catch (Exception e) {
            io.println("Please enter a valid number between 1-6, or 'q' to quit:");
            gameboard.print();
        }
    }

    public void checkForQuit() {
        if (input.equals("q")) {
            selectedHouseNumber = 0;
            gettingMove = false;
        }
    }
}
