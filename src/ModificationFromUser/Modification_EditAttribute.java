package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;

public class Modification_EditAttribute <T> implements ModificationFromUser{
	private AttributeOwnerReader myObj;
	private Attribute<?> myAtt;
	private String myNewValue;
	
	public Modification_EditAttribute(AttributeOwnerReader obj, Attribute<T> att, String newValue){
		myObj = obj;
		myAtt = att;
		myNewValue = newValue;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
