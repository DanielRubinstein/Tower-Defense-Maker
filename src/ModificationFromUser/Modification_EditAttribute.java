package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.Attribute;
<<<<<<< HEAD
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;

public class Modification_EditAttribute implements ModificationFromUser{
=======
import backEnd.Attribute.AttributeOwnerReader;

public class Modification_EditAttribute<T> implements ModificationFromUser{
>>>>>>> master
	private AttributeOwnerReader myObj;
	private Attribute<?> myAtt;
	private T myNewValue;
	
<<<<<<< HEAD
	public Modification_EditAttribute(AttributeOwnerReader obj, Attribute<?> att, String newValue){
=======
	public Modification_EditAttribute(AttributeOwnerReader obj, Attribute<?> att, T newValue){
>>>>>>> master
		myObj = obj;
		myAtt = att;
		myNewValue = newValue;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
