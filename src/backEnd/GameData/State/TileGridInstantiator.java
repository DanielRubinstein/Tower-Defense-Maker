package backEnd.GameData.State;

import java.util.Map;

import javafx.geometry.Point2D;

public class TileGridInstantiator {
	
	private int numCols, numRows;
	private Map<Point2D, Tile> tileGrid;
	
	public TileGridInstantiator(int numRows, int numCols, Map<Point2D, Tile> tileGrid)
	{
		setNumCols(numCols);
		setNumRows(numRows);
		
		setTileGrid(tileGrid);
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public Map<Point2D, Tile> getTileGrid() {
		return tileGrid;
	}

	public void setTileGrid(Map<Point2D, Tile> tileGrid) {
		this.tileGrid = tileGrid;
	}

}
