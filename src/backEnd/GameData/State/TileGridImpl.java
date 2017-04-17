package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.InvalidationListener;
import javafx.geometry.Point2D;

/**
 * This is the Grid class that contains the Tile Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class TileGridImpl extends Observable implements TileGrid {
	private int numColsInGrid;
	private int numRowsInGrid;
	private Map<Point2D, Tile> tileGrid;
	private List<Tile> tileList;
	private double tileWidth;
	private double tileHeight;
	
	public TileGridImpl(int colsInGrid, int rowsInGrid){
		numColsInGrid = colsInGrid;
		numRowsInGrid = rowsInGrid;
		tileGrid = new HashMap<>();
	}
	
	@Override
	public void setTileSize(double tileWidth, double tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	
	@Override
	public Tile getTileByGridPosition(int column, int row){
		checkAgainstBounds(column, row);
		return tileGrid.get(new Point2D(column, row));
	}

	private void checkAgainstBounds(int column, int row) {
		if (column >= getNumColsInGrid() || row >= getNumRowsInGrid()) {
			throw new IndexOutOfBoundsException();
		}
		if (column < 0 || row < 0){
			throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public Tile getTileByScreenLocation(Point2D screenLocation){		
		int column = (int) Math.floor(screenLocation.getX() / tileWidth);
		int row = (int) Math.floor(screenLocation.getY() / tileHeight);
		return getTileByGridPosition(column, row);
	}
	
	@Override
	public void setTileByScreenPosition(Tile newTile, Point2D position) {
		int column = (int) Math.floor(position.getX() / tileWidth);
		int row = (int) Math.floor(position.getY() / tileHeight);
		setTileByGridPosition(newTile, column, row);
	}
	
	@Override
	public void setTileByGridPosition(Tile newTile, int column, int row){
		Point2D posOfNewTile = new Point2D(column, row);
		Boolean initialization = false;
		if(!tileGrid.containsKey(posOfNewTile)){
			initialization = true;
		}
		tileGrid.put(posOfNewTile, newTile);
		if(!initialization){ 
			// do not notify ScreenGrid for each initial Tile, only if changed after intialization
			this.setChanged();
			this.notifyObservers();
		}
	}

	@Override
	public int getNumColsInGrid() {
		return numColsInGrid;
	}

	@Override
	public int getNumRowsInGrid() {
		return numRowsInGrid;
	}
	
	public List<Tile> getAllTiles(){
		tileList=new ArrayList<Tile>();
		for (int col = 0; col < numColsInGrid; col++) {
			for (int row = 0; row < numRowsInGrid; row++) {
				tileList.add(tileGrid.get(new Point2D(col, row)));
			}
		}
		return tileList;
	}

	@Override
	public double getTileWidth() {
		return this.tileWidth;
	}

	@Override
	public double getTileHeight() {
		return this.tileHeight;
	}

	@Override
	public void addAsObserver(Observer o) {
		this.addObserver(o);
	}






}
