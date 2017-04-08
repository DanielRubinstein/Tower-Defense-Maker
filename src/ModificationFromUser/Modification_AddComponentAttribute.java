package ModificationFromUser;

import backEnd.Model;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;

public class Modification_AddComponentAttribute implements ModificationFromUser {

	private Component myComp;
	private Attribute myAttribute;
	public static final String ERROR_MESSAGE = "You cannot add attributes in Player mode!";
	
	public Modification_AddComponentAttribute(Component myComp, Attribute newAttr) {
		this.myComp = myComp;
		this.myAttribute = newAttr;

	}

	
	@Override
	public void invoke(ModeEnum currentMode, Model myController) {
		switch (currentMode) {
		case AUTHOR:
			myComp.getAttributeData().addAttribute(myAttribute);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}

	}
	

}


public enum MoveDirection{
	RIGHT, LEFT, UP, DOWN
}
