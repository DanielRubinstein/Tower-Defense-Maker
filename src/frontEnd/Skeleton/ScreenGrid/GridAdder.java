package frontEnd.Skeleton.ScreenGrid;

import ModificationFromUser.AttributeOwner.Modification_Add_PaletteToGrid;
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
	
	private GridVisual<Tile> myTileGrid;
	private GridVisual<Component> myComponentGrid;
	
	public GridAdder(GridVisual<Tile> myTileGrid2, GridVisual<Component> myComponentGrid2){
		myTileGrid = myTileGrid2;
		myComponentGrid = myComponentGrid2;
			
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
		myComponentGrid.addPreset(component, position);
	}
	
	
	
	
	
	
}
