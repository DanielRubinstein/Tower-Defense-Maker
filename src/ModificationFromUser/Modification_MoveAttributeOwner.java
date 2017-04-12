package ModificationFromUser;

import java.util.ResourceBundle;

import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;

public class Modification_MoveAttributeOwner implements ModificationFromUser {

	private AttributeOwner myAO;
	private Point2D newLoc;
	public static final String ERROR_DESCRIPTION = "Cannot move tiles";
	private final static String DEFAULT_COMPONENT_ATTRIBUTES = "resources/defaultComponentAttributes";
	private static ResourceBundle myAttrNameResources;
	
	public Modification_MoveAttributeOwner(AttributeOwner myAO, Point2D newLocation) {
		this.myAO = myAO;
		this.newLoc = newLocation;
		myAttrNameResources = ResourceBundle.getBundle(DEFAULT_COMPONENT_ATTRIBUTES);

	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		if(myAO instanceof Tile){
			throw new Exception(ERROR_DESCRIPTION);
		}
		else if(myAO instanceof ComponentImpl){
			myModel.getState().getComponentGraph().removeComponent((ComponentImpl) myAO);
			myModel.getState().getComponentGraph().addComponentToGrid((ComponentImpl) myAO, newLoc);
			Attribute<Point2D> myLoc = (Attribute<Point2D>)((ComponentImpl)myAO).getMyAttributes().get(myAttrNameResources.getString("Position"));
			myLoc.setValue(newLoc);
		}
	}

	
}
