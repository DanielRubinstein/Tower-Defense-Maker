package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_RemoveAttributeOwner_Methods;
import backEnd.Model;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import util.reflection.Reflection;

public class Modification_RemoveAttributeOwner implements ModificationFromUser {
	
	private AttributeOwnerReader toRemoveReader;
	public static final String TILE_ERROR = "Cannot move tiles";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_RemoveAttributeOwner(AttributeOwnerReader toRemove){
		this.toRemoveReader = toRemove;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		AttributeOwner toRemove = myModel.getAttributeOwner(toRemoveReader);
		Modification_RemoveAttributeOwner_Methods methods = new Modification_RemoveAttributeOwner_Methods(myModel);
		Reflection.callMethod(methods, "remove", toRemove);
		
	}

}
