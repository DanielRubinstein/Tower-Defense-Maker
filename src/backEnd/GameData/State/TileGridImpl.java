package backEnd.GameData.State;

import javafx.geometry.Point2D;

/**
 * This is the Grid class that contains the Tile Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class TileGridImpl implements TileGrid {
	private int gridWidth;
	private int gridHeight;
	private Tile[][] tileGrid;
	
	public TileGridImpl(int width, int height){
		gridWidth = width;
		gridHeight = height;
		System.out.println("using width "+width + "and height " +height);
		tileGrid = new Tile[width][height];
	}
	
	@Override
	public Tile getTileByLocation(Point2D location){
		return tileGrid[(int) location.getY()][(int) location.getX()];
	}
	
	@Override
	public void setTile(Tile newTile, Point2D location){
		tileGrid[(int) location.getY()][(int) location.getX()] = newTile;
	}

	@Override
	public Tile getTileByCoord(Double x, Double y) {
		return getTileByLocation(new Point2D(x,y));
	}

	@Override
	public int getMyWidth() {
		return gridWidth;
	}

	@Override
	public int getMyHeight() {
		return gridHeight;
	}

}
