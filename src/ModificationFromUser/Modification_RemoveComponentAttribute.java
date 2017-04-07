package ModificationFromUser;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;

public class Modification_RemoveComponentAttribute implements ModificationFromUser {


	private Component myComp;
	private Attribute myAttribute;
	public static final String ERROR_MESSAGE = "You cannot remove attributes in Player mode!";
	
	public Modification_RemoveComponentAttribute(Component myComp, Attribute removeAttr) {
		this.myComp = myComp;
		this.myAttribute = removeAttr;

	}

	
	@Override
	public void invoke(ModeEnum currentMode) {
		switch (currentMode) {
		case AUTHOR:
			myComp.removeAttribute(myAttribute);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}

	}


}
