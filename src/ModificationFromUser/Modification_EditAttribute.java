package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;

public class Modification_EditAttribute implements ModificationFromUser{
	private AttributeOwner myObj;
	private Attribute<?> myAtt;
	private String myNewValue;
	
	public Modification_EditAttribute(AttributeOwner obj, Attribute<?> att, String newValue){
		myObj = obj;
		myAtt = att;
		myNewValue = newValue;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
