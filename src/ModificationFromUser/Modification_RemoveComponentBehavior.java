package ModificationFromUser;

import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;
import main.InteractivityController;

public class Modification_RemoveComponentBehavior implements ModificationFromUser {

	private Component myComp;
	private Behavior myBehavior;
	public static final String ERROR_MESSAGE = "You cannot remove behaviors in Player mode!";
	
	public Modification_RemoveComponentBehavior(Component myComp, Behavior removeBehavior) {
		this.myComp = myComp;
		this.myBehavior = removeBehavior;

	}
	
	
	@Override
	public void invoke(ModeEnum currentMode, InteractivityController myController) {
		switch (currentMode) {
		case AUTHOR:
			myComp.removeBehavior(myBehavior);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}

		
	}

}
