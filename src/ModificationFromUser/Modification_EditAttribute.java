package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;

public class Modification_EditAttribute<T> implements ModificationFromUser{

	private AttributeOwner myObj;
	private Attribute<T> myAtt;
	private T myNewValue;
	
	public Modification_EditAttribute(AttributeOwner obj, Attribute<T> att, T newValue){
		myObj = obj;
		myAtt = att;
		myNewValue = newValue;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myAtt.setValue(myNewValue);
	}

}
