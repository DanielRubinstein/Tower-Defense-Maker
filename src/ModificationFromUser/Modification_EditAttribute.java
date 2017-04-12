package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;

public class Modification_EditAttribute<T> implements ModificationFromUser{


	private AttributeOwnerReader myObj;
	private Attribute<T> myAtt;
	private T myNewValue;
	
	public Modification_EditAttribute(AttributeOwnerReader myOwner, Attribute<T> att, T newValue){
		myObj = myOwner;
		myAtt = att;
		myNewValue = newValue;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myAtt.setValue(myNewValue);
	}

}
