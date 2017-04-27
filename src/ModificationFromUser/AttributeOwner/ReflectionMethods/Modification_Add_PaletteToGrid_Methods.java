package ModificationFromUser.AttributeOwner.ReflectionMethods;

import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.ModeException;
import javafx.geometry.Point2D;

public class Modification_Add_PaletteToGrid_Methods{
	private Point2D location;
	private ModelImpl myModel;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_Add_PaletteToGrid_Methods(ModelImpl model, Point2D location) {
		this.myModel = model;
		this.location = location;
	}
	
	public void add(TileImpl tile){
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			myModel.getState().getTileGrid().setTileByScreenPosition(tile,location);
			break;

		case "PLAYER":
			throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
		}
	}
	
	public void add(Component component){
		myModel.getState().getComponentGraph().addComponentToGrid(component, location);
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			// do nothing
			break;
		case "PLAYER":
			// for future, do something
			// deduct price
			break;
		}	
	}
}