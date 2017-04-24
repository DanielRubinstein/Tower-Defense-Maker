package ModificationFromUser.AttributeOwner;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;
import javafx.geometry.Point2D;

public class Modification_MoveAttributeOwner implements ModificationFromUser {

	private AttributeOwner myAO;
	private Point2D newLoc;
	private ModelImpl myModel;
	public static final String TILE_ERROR = "Cannot move tiles";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";
	private final static String DEFAULT_COMPONENT_ATTRIBUTES = "resources/defaultComponentAttributes";
	private static ResourceBundle myAttrNameResources;
	
	public Modification_MoveAttributeOwner(AttributeOwner myAO, Point2D newLocation) {
		this.myAO = myAO;
		this.newLoc = newLocation;
		myAttrNameResources = ResourceBundle.getBundle(DEFAULT_COMPONENT_ATTRIBUTES);

	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		try {
			Method move = Modification_MoveAttributeOwner.class.getDeclaredMethod("move", myAO.getClass());
			move.setAccessible(true);
			move.invoke(this, myAO);
		} catch (NoSuchMethodException e) {
			throw new Exception(DESCRIPTION_ERROR);
		}
		
	}
	
	private void move(TileImpl tile) throws Exception{
		throw new Exception(TILE_ERROR);
	}
	
	private void move(Component component){
		myModel.getState().getComponentGraph().removeComponent(component);
		myModel.getState().getComponentGraph().addComponentToGrid(component, newLoc);
		component.setAttributeValue(myAttrNameResources.getString("Position"), newLoc);
	}

	
}
