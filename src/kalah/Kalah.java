package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;


public class Kalah {

	public Player[] players;
	public Robot robot;
	private boolean isGameRunning = true;
	public boolean isBoardVertical;
	public boolean isUsingBot;


	public static void main(String[] args) {
		new Kalah().play(new MockIO(), false, true);
	}


	public void runGame(IO io){
		boolean isPlayer1Turn = true;
		TurnLogic logic = new TurnLogic(this, io);
		// Continue playing the until someone wins or player enters 'q'
		while (isGameRunning) {
			/*
			 Cycle through player turns until a winner is declared or player quits.
			 Returns false if the game needs to be stopped for any state, and it has
			 already printed the game board.
			*/
			isGameRunning = logic.takeTurn(isPlayer1Turn);
			isPlayer1Turn = !isPlayer1Turn;
		}
	}

	public void play(IO io, boolean vertical, boolean bmf) {
		isBoardVertical = vertical;
		isUsingBot = bmf;
		players = new Player[]{new Player(1, 4), new Player(2, 4)};
		if(isUsingBot){
			robot = new Robot(io, players, this);
		}
		runGame(io);
	}



}
