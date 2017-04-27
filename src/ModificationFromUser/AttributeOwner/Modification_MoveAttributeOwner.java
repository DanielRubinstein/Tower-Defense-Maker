package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_MoveAttributeOwner_Methods;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import javafx.geometry.Point2D;
import util.reflection.Reflection;

public class Modification_MoveAttributeOwner implements ModificationFromUser {
	private AttributeOwner myAO;
	private Point2D newLoc;
	private ModelImpl myModel;
	
	public Modification_MoveAttributeOwner(AttributeOwner myAO, Point2D newLocation) {
		this.myAO = myAO;
		this.newLoc = newLocation;
	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		Modification_MoveAttributeOwner_Methods methods = new Modification_MoveAttributeOwner_Methods(myModel, newLoc);
		Reflection.callMethod(methods, "move", myAO);
		
	}
	
}
