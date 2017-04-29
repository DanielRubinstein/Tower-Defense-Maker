package ModificationFromUser.AttributeOwner.ReflectionMethods;

import java.util.List;

import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileCorners;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.ModeException;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

public class Modification_Add_ToGrid_Methods{
	private Point2D location;
	private ModelImpl myModel;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";
	private static final StringResourceBundle strResources = new StringResourceBundle();

	public Modification_Add_ToGrid_Methods(ModelImpl model, Point2D location) {
		this.myModel = model;
		this.location = location;
	}
	
	public void add(TileImpl tile) throws ModeException{
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			myModel.getState().getTileGrid().setTileByScreenPosition(tile,location);
			break;

		case "PLAYER":
			throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
		}
	}
	
	public void add(Component component){
		
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			addComponentToGrid(component);
			break;
		case "PLAYER":
			Tile tile = myModel.getState().getTileGrid().getTileByScreenPosition(location);
			double tileWidth = myModel.getState().getTileGrid().getTileWidth();
			double tileHeight = myModel.getState().getTileGrid().getTileHeight();
			List<Component> compList = myModel.getState().getComponentGraph().getComponentsByTileCorners(new TileCorners(location, tileWidth, tileHeight));
			if (compList.size() < tile.<Integer>getAttribute(strResources.getFromAttributeNames("BuildCapacity")).getValue()){
				addComponentToGrid(component);
			}
			else{
				new ModeException(myModel.getMode(), String.format(strResources.getFromErrorMessages("Tile_Not_Buildable"), 
						tile.<Integer>getAttribute(strResources.getFromAttributeNames("BuildCapacity")).getValue()));
			}
			break;
		}	
	}
	
	private void addComponentToGrid(Component component){
		myModel.getState().getComponentGraph().addComponentToGrid(component, location);
	}
}