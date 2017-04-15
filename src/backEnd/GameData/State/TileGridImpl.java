package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

/**
 * This is the Grid class that contains the Tile Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class TileGridImpl implements TileGrid {
	private int numColsInGrid;
	private int numRowsInGrid;
	private Tile[][] tileGrid;
	private List<Tile> tileList;
	private double tileWidth;
	private double tileHeight;
	
	public TileGridImpl(int colsInGrid, int rowsInGrid){
		numColsInGrid = colsInGrid;
		numRowsInGrid = rowsInGrid;
		tileGrid = new Tile[colsInGrid][rowsInGrid];
	}
	
	@Override
	public void setTileSize(double tileWidth, double tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
	}

	
	@Override
	public Tile getTileByLocation(Point2D location){
		//System.out.println("Getting Tile at this location:" + location.toString());
		return getTileByCoord((int) location.getY(),(int) location.getX()); //Potentially wrong flipped x/y- y
	}
	
	@Override
	public Tile getTileByScreenLocation(Point2D location){
		double xx = (double) location.getX() / tileWidth;
		double yy = (double) location.getY() /tileHeight;
		int xxx = (int) Math.floor(xx);
		int yyy=  (int) Math.floor(yy);
		return getTileByCoord(yyy, xxx); //Potentially wrong flipped x/y- y
	}
	
	
	
	@Override
	public void setTile(Tile newTile, Point2D location){
		//System.out.println(tileGrid.length);
		//System.out.println((int) location.getX());
		//System.out.println(location.getY());
		tileGrid[(int) location.getX()][(int) location.getY()] = newTile; //Potentially wrong flipped x/y?
		newTile.setAttributeValue("Position", location);
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
		for (int i=0; i<tileGrid.length; i++){
			for (int j=0; j<tileGrid[i].length; j++){
				tileList.add(tileGrid[i][j]);
			}
		}
		return tileList;
	}

	@Override
	public Tile getTileByCoord(int x, int y) {
		//convert from 50,50 to coordinates
		//System.out.println(String.format("Getting Tile by Coordinate (%d, %d)", x, y));
		if(x >= getNumColsInGrid()  ||
		   y >= getNumRowsInGrid() ){
			return null;
		}
		if( x<0 || y<0) return null;
		//System.out.println("is returning a tile");
		return tileGrid[x][y];
	}

	@Override
	public double getTileWidth() {
		return this.tileWidth;
	}

	@Override
	public double getTileHeight() {
		return this.tileHeight;
	}


}
