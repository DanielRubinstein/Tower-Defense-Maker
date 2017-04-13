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
	private int gridWidth;
	private int gridHeight;
	private Tile[][] tileGrid;
	private List<Tile> tileList;
	private double tileWidth=40;
	private double tileHeight=40;
	
	public TileGridImpl(int width, int height){
		gridWidth = width;
		gridHeight = height;
		tileGrid = new Tile[width][height];
	}
	
	@Override
	public Tile getTileByLocation(Point2D location){
		System.out.println("getin tile by point2d");
		return getTileByCoord((int) location.getY(),(int) location.getX()); //Potentially wrong flipped x/y- y
	}
	@Override
	public Tile getTileByScreenLocation(Point2D location){
		double xx = (double) location.getX() / tileWidth;
		double yy = (double) location.getY() /tileHeight;
		int xxx = (int) Math.floor(xx);
		int yyy=  (int) Math.floor(yy);
		return getTileByCoord(yyy,(int) xxx); //Potentially wrong flipped x/y- y
	}
	
	
	@Override
	public void setTile(Tile newTile, Point2D location){
		tileGrid[(int) location.getX()][(int) location.getY()] = newTile; //Potentially wrong flipped x/y?
	}

	@Override
	public int getMyWidth() {
		return gridWidth;
	}

	@Override
	public int getMyHeight() {
		return gridHeight;
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
		System.out.println("getting tile by coord " + x +"   " + y);
		if(x >= getMyWidth()  ||
		   y >= getMyHeight() ){
			double xx = (double) x / tileWidth;
			double yy = (double) y /tileHeight;
			int xxx = (int) Math.ceil(xx);
			int yyy=  (int) Math.ceil(yy);
			return tileGrid[xxx][yyy];
		}
		if( x<0 || y<0) return null;

		return tileGrid[x][y];
	}

}
