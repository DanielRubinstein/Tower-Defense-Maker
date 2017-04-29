package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_MoveAttributeOwner_Methods;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;
import util.reflection.Reflection;

public class Modification_MoveAttributeOwner implements ModificationFromUser {
	private AttributeOwnerReader myAOReader;
	private Point2D newLoc;
	
	//FIXME it seems like this is never used, but if it will be used I'm not sure
	//if the user should be allowed to move AttributeOwner in Player
	public Modification_MoveAttributeOwner(AttributeOwnerReader myAOReader, Point2D newLocation) {
		this.myAOReader = myAOReader;
		this.newLoc = newLocation;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		AttributeOwner myAO = myModel.getAttributeOwner(myAOReader);
		Modification_MoveAttributeOwner_Methods methods = new Modification_MoveAttributeOwner_Methods(myModel, newLoc);
		Reflection.callMethod(methods, "move", myAO);
		
	}
	
}
