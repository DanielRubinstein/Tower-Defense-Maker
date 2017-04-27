package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_Add_ToGrid_Methods;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerSerializer;
import javafx.geometry.Point2D;
import util.reflection.Reflection;

/**
 * Used for adding a preset attribute owner to the grid. Preset attribute owner
 * is passed as parameter in constructor
 * 
 * @author Derek
 *
 */
public class Modification_Add_PaletteToGrid implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	private ModelImpl myModel;

	public Modification_Add_PaletteToGrid(AttributeOwner preset, Point2D location) {
		this.newAttrOwn = preset;
		this.location = location;

	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		 
		AttributeOwnerSerializer attributeOwnerSerializer = new AttributeOwnerSerializer();
		AttributeOwner cleanAO = attributeOwnerSerializer.createCopy(newAttrOwn);
		cleanAO.setAttributeValue("Position", location);
		
		Modification_Add_ToGrid_Methods methods = new Modification_Add_ToGrid_Methods(myModel, location);
		Reflection.callMethod(methods, "add", cleanAO);
	}
}