package ModificationFromUser.AttributeOwner.ReflectionMethods;

import java.util.ResourceBundle;

import backEnd.Model;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;
import javafx.geometry.Point2D;

/**
 * The reflection class to move a component or tile for
 * Modification_MoveAttributeOwner
 * 
 * @author Miguel Anderson
 *
 */

public class Modification_MoveAttributeOwner_Methods {
	private Point2D newLoc;
	private Model myModel;
	public static final String TILE_ERROR = "Cannot move tiles";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";
	private final static String DEFAULT_COMPONENT_ATTRIBUTES = "resources/defaultComponentAttributes";
	private static ResourceBundle myAttrNameResources = ResourceBundle.getBundle(DEFAULT_COMPONENT_ATTRIBUTES);

	public Modification_MoveAttributeOwner_Methods(Model model, Point2D newLocation) {
		this.myModel = model;
		this.newLoc = newLocation;
	}

	public void move(TileImpl tile) throws Exception {
		throw new Exception(TILE_ERROR);
	}

	public void move(Component component) {
		myModel.getState().getComponentGraph().removeComponent(component);
		myModel.getState().getComponentGraph().addComponentToGrid(component, newLoc);
		component.setAttributeValue(myAttrNameResources.getString("Position"), newLoc);
	}

}
