package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;


public class Modification_EditAttribute<T> implements ModificationFromUser {

	private AttributeOwner myObj;
	private Attribute<T> myAtt;
	private T myNewValue;

	// FIXME in the future the parameter will be AttributeOwnerReader and this
	// class will get the modifiable AttributeOwner using IDs
	public Modification_EditAttribute(AttributeOwner myOwner, Attribute<T> att, T newValue) {
		myObj = myOwner;
		myAtt = att;
		myNewValue = newValue;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myObj.setAttributeValue(myAtt.getName(), myNewValue);
		//System.out.println(myAtt.getName());
	}

}
