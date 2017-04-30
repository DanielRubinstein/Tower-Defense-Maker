package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_Add_ToGrid_Methods;
import backEnd.Model;
import backEnd.Attribute.AttributeOwner;
import javafx.geometry.Point2D;
import util.reflection.Reflection;
import backEnd.Attribute.AttributeOwnerReader;

/**
 * Used for adding a new attribute owner to the grid. New attribute owner is passed as parameter in constructor
 * 
 * @author Derek
 *
 */
public class Modification_Add_StraightToGrid implements ModificationFromUser {
	private AttributeOwnerReader newAttrOwn;
	private Point2D location;
	public Modification_Add_StraightToGrid(AttributeOwnerReader toAdd) {
		this.newAttrOwn = toAdd;
	

	}

	@Override
	public void invoke(Model myModel) throws Exception {
		AttributeOwner attributeOwner = myModel.getAttributeOwner(newAttrOwn);
		this.location = attributeOwner.<Point2D>getAttribute("Position").getValue();
		Modification_Add_ToGrid_Methods methods = new Modification_Add_ToGrid_Methods(myModel, location);
		Reflection.callMethod(methods, "addAttributeOwnerToGrid", newAttrOwn);
	}

}
