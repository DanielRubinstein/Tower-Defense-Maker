package ModificationFromUser;

import backEnd.Model;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeException;

public class Modification_RemoveComponentAttribute implements ModificationFromUser {


	private Component myComp;
	private Attribute myAttribute;
	public static final String DESCRIPTION = "Remove Component Attribute";
	
	public Modification_RemoveComponentAttribute(Component myComp, Attribute removeAttr) {
		this.myComp = myComp;
		this.myAttribute = removeAttr;

	}

	@Override
	public void invoke(Model myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			//FIXME Component needs appropriate methods
			myComp.getAttributeData().removeAttribute(myAttribute);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}		
	}


}
