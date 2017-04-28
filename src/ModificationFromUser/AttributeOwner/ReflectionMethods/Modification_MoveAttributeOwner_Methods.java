package ModificationFromUser.AttributeOwner.ReflectionMethods;

import java.util.ResourceBundle;

import backEnd.ModelImpl;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.TileImpl;
import javafx.geometry.Point2D;

public class Modification_MoveAttributeOwner_Methods{
	private Point2D newLoc;
	private ModelImpl myModel;
	public static final String TILE_ERROR = "Cannot move tiles";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";
	private final static String DEFAULT_COMPONENT_ATTRIBUTES = "resources/defaultComponentAttributes";
	private static ResourceBundle myAttrNameResources;
	
	public Modification_MoveAttributeOwner_Methods(ModelImpl model, Point2D newLocation) {
		this.myModel = model;
		this.newLoc = newLocation;
		myAttrNameResources = ResourceBundle.getBundle(DEFAULT_COMPONENT_ATTRIBUTES);

	}

	
	public void move(TileImpl tile) throws Exception{
		throw new Exception(TILE_ERROR);
	}
	
	public void move(ComponentImpl component){
		myModel.getState().getComponentGraph().removeComponent(component);
		myModel.getState().getComponentGraph().addComponentToGrid(component, newLoc);
		component.setAttributeValue(myAttrNameResources.getString("Position"), newLoc);
	}

	
}
