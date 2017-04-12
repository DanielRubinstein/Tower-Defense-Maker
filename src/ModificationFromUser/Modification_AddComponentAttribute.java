package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.Component;
import backEnd.Mode.ModeException;


public class Modification_AddComponentAttribute<T> implements ModificationFromUser {

	private Component myComp;
	private AttributeImpl<T> myAttribute;
	public static final String DESCRIPTION = "Add Component Attribute";
	
	@SuppressWarnings("unchecked")
	public Modification_AddComponentAttribute(Component myComp, AttributeImpl newAttr) {
		this.myComp = myComp;
		this.myAttribute = newAttr;

	}



	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			//FIXME Component needs appropriate methods
			myComp.getAttributeData().addAttribute(myAttribute);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}
		
	}
	

}

