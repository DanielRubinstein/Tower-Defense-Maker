package ModificationFromUser;

import backEnd.Model;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeException;

public class Modification_AddComponentAttribute<T> implements ModificationFromUser {

	private Component myComp;
	private Attribute<T> myAttribute;
	public static final String DESCRIPTION = "Add Component Attribute";
	
	public Modification_AddComponentAttribute(Component myComp, Attribute<T> newAttr) {
		this.myComp = myComp;
		this.myAttribute = newAttr;

	}



	@Override
	public void invoke(Model myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			//FIXME Component needs appropriate methods
			myComp.getAttributeData().addAttribute(myAttribute);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}
		
	}
	

}



