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
	public static final String ERROR_DESCRIPTION = "Cannot move tiles";
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
			System.out.println("in Modification_MoveAttributeOwner, No method found, ugh");
			// do nothing
			// this means the thing being put in attribute command center is a tile
		} catch (Exception e) {
			// something went wrong
			System.out.println("Something went wrong in Modification_MoveAttributeOwner");
			// TODO add exception?
		}
		
	}
	
	private void move(TileImpl tile) throws Exception{
		throw new Exception(ERROR_DESCRIPTION);
	}
	
	private void move(Component component){
		myModel.getState().getComponentGraph().removeComponent(component);
		myModel.getState().getComponentGraph().addComponentToGrid(component, newLoc);
		component.setAttributeValue(myAttrNameResources.getString("Position"), newLoc);
		//Attribute<Point2D> myLoc = (Attribute<Point2D>)(component).getMyAttributes().get(myAttrNameResources.getString("Position"));
		//myLoc.setValue(newLoc);
	}

	
}
