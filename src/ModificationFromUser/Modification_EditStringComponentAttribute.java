package ModificationFromUser;

import backEnd.Model;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeException;

public class Modification_EditStringComponentAttribute implements ModificationFromUser {

	private String newVal;
	private Component myComp;
	private String attributeType;
	public static final String DESCRIPTION = "Edit Component Attribute";
	
	//FIXME not sure what attributeType is and 
	// not sure if error checking should be used to make sure attributeType is a valid attribute type
	public Modification_EditStringComponentAttribute(Component myComp, String attributeType, String newVal) {
		this.newVal = newVal;
		this.myComp = myComp;
		this.attributeType = attributeType;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void invoke(Model myModel) throws Exception {
		switch(myModel.getMode().getUserMode()){
		case AUTHOR:
			//FIXME not sure how to solve this error
			Attribute<String> attrToEdit = (Attribute<String>) myComp.getAttribute(attributeType);
			attrToEdit.setValue(newVal);
		case PLAYER:
			throw new ModeException(myModel.getMode(),DESCRIPTION);
		}

	}


	

}