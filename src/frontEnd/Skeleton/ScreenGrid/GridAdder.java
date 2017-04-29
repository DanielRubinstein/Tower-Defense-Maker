package frontEnd.Skeleton.ScreenGrid;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;

/**
 * This class is used to add AttributeOwners to the grid. This is done to make the use of Reflection
 * much easier.
 * @author Tim
 *
 */
public class GridAdder {
	
	private TileGridVisual myTileGrid;
	private ComponentGridVisual myComponentGrid;
	
	public GridAdder(TileGridVisual tileGrid, ComponentGridVisual componentGrid){
		myTileGrid = tileGrid;
		myComponentGrid = componentGrid;
			
	}
	
	/**
	 * Adds a Tile to the TileGridVisual
	 * @param tile to be added
	 * @param position
	 */
	public void addToGrid(Tile tile, Point2D position){
		myTileGrid.addPreset(tile, position);
		
		
	}
	/**
	 * Adds a Component to the ComponentGridVisual
	 * @param component to be added
	 * @param position
	 */
	public void addToGrid(Component component, Point2D position){
		
		
	}
	
	
	
	
	
	
}
