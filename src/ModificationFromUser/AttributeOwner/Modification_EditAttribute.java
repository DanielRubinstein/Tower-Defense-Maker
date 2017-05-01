package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;


public class Modification_EditAttribute<T> implements ModificationFromUser {

	private AttributeOwnerReader myObj;
	private String attributeName;
	private T myNewValue;

	public Modification_EditAttribute(AttributeOwnerReader myOwner, String attName, T newValue) {
		myObj = myOwner;
		attributeName = attName;
		myNewValue = newValue;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		AttributeOwner attributeOwner = myModel.getAttributeOwner(myObj);
		attributeOwner.setAttributeValue(attributeName, myNewValue);
		myModel.getDataController().saveUniversalGameData();
	}

}
