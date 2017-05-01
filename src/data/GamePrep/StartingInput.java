package data.GamePrep;


public class StartingInput {
	private int numberOfTileCols;
	private int numberOfTileRows;
	private String gameName;


	public void setTilesWide(int tilesWide) {
		this.numberOfTileCols = tilesWide;
	}

	public void setTilesHigh(int tilesHigh) {
		this.numberOfTileRows = tilesHigh;
	}
	public void setGameName(String name){
		gameName=name;
	}
	public int getNumCols() {
		return numberOfTileCols;
	}
	public int getNumRows() {
		return numberOfTileRows;
	}
	public String getGameName(){
		return gameName;
	}
	
	
}
