package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.Component;
import backEnd.Mode.ModeException;

public class Modification_RemoveComponentAttribute implements ModificationFromUser {


	private Component myComp;
	private AttributeImpl myAttribute;
	public static final String DESCRIPTION = "Remove Component Attribute";
	
	public Modification_RemoveComponentAttribute(Component myComp, AttributeImpl removeAttr) {
		this.myComp = myComp;
		this.myAttribute = removeAttr;

	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			//FIXME Component needs appropriate methods
			myComp.getAttributeData().removeAttribute(myAttribute);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}		
	}


}
