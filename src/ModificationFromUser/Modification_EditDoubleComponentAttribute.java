package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeException;

public class Modification_EditDoubleComponentAttribute implements ModificationFromUser {

	private double newVal;
	private Component myComp;
	private String attributeType;
	public static final String DESCRIPTION = "Edit Component Attribute";
	
	//FIXME not sure what attributeType is and 
	// not sure if error checking should be used to make sure attributeType is a valid attribute type
	public Modification_EditDoubleComponentAttribute(Component myComp, String attributeType, double newVal) {
		this.newVal = newVal;
		this.myComp = myComp;
		this.attributeType = attributeType;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void invoke(ModelImpl myModel) throws Exception {
		switch(myModel.getMode().getUserMode()){
		case AUTHOR:
			//FIXME not sure how to solve this error
			AttributeImpl<Double> attrToEdit = (AttributeImpl<Double>) myComp.getAttribute(attributeType);
			attrToEdit.setValue(newVal);
		case PLAYER:
			throw new ModeException(myModel.getMode(),DESCRIPTION);
		}

	}


	

}
