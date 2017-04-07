package ModificationFromUser;

import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;

public class Modification_AddComponentBehavior implements ModificationFromUser {

	private Component myComp;
	private Behavior myBehavior;
	public static final String ERROR_MESSAGE = "You cannot add behaviors in Player mode!";
	
	public Modification_AddComponentBehavior(Component myComp, Behavior newBehavior) {
		this.myComp = myComp;
		this.myBehavior = newBehavior;

	}

	@Override
	public void invoke(ModeEnum currentMode) {
		switch (currentMode) {
		case AUTHOR:
			myComp.addBehavior(myBehavior);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}

	}

}
