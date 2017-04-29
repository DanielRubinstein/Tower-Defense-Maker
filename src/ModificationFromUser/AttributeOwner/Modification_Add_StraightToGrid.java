package ModificationFromUser.AttributeOwner;

import java.util.List;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_Add_ToGrid_Methods;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileCorners;
import backEnd.Mode.ModeException;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;
import util.reflection.Reflection;

/**
 * Used for adding a new attribute owner to the grid. New attribute owner is passed as parameter in constructor
 * 
 * @author Derek
 *
 */
public class Modification_Add_StraightToGrid implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	private ModelImpl myModel;

	
	public Modification_Add_StraightToGrid(AttributeOwner toAdd) {
		this.newAttrOwn = toAdd;
		this.location = toAdd.<Point2D>getAttribute("Position").getValue();

	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		Modification_Add_ToGrid_Methods methods = new Modification_Add_ToGrid_Methods(myModel, location);
		Reflection.callMethod(methods, "addAttributeOwnerToGrid", newAttrOwn);
	}

}
