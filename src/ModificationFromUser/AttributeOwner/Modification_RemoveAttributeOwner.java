package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_RemoveAttributeOwner_Methods;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import util.reflection.Reflection;

public class Modification_RemoveAttributeOwner implements ModificationFromUser {
	
	private AttributeOwner toRemove;
	private ModelImpl myModel;
	public static final String TILE_ERROR = "Cannot move tiles";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_RemoveAttributeOwner(AttributeOwner toRemove){
		this.toRemove = toRemove;
	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		Modification_RemoveAttributeOwner_Methods methods = new Modification_RemoveAttributeOwner_Methods(myModel);
		Reflection.callMethod(methods, "remove", toRemove);
		
	}

}
