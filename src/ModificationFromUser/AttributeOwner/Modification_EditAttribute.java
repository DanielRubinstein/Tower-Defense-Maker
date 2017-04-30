package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;


public class Modification_EditAttribute<T> implements ModificationFromUser {

	private AttributeOwnerReader myObj;
	private String attributeName;
	private T myNewValue;

	// FIXME in the future the parameter will be AttributeOwnerReader and this
	// class will get the modifiable AttributeOwner using IDs
	public Modification_EditAttribute(AttributeOwnerReader myOwner, String attName, T newValue) {
		myObj = myOwner;
		attributeName = attName;
		myNewValue = newValue;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		AttributeOwner attributeOwner = myModel.getAttributeOwner(myObj);
		attributeOwner.setAttributeValue(attributeName, myNewValue);
		myModel.getDataController().saveUniversalGameData();
	}

}
