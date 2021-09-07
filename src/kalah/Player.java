package kalah;

/*
    Store player's information
 */
public class Player {

    protected final int playerNumber;
    protected int[] houses;
    protected int store;


    public Player(int playerNumber, int numSeedsPerHouse) {
        this.playerNumber = playerNumber;
        // Houses that store a starting value depending on input
        this.houses = new int[]{numSeedsPerHouse, numSeedsPerHouse, numSeedsPerHouse, numSeedsPerHouse,
                numSeedsPerHouse, numSeedsPerHouse};
        this.store = 0;
    }


    public int getScore() {

        int numSeedsInPlayerHouses = 0;

        for (int seedsInHouse : this.houses) {
            numSeedsInPlayerHouses += seedsInHouse;
        }
        return numSeedsInPlayerHouses + this.store;
    }


    public int getPlayerNumber() {
        return playerNumber;
    }

    public int[] getHouses() {
        return houses;
    }

    public void setHouses(int[] houses) {
        this.houses = houses;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

}
