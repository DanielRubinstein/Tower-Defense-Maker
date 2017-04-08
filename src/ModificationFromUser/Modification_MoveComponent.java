package ModificationFromUser;

import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;
import main.InteractivityController;

public class Modification_MoveComponent implements ModificationFromUser {

	private Component myComp;
	private Behavior myBehavior;
	
	public Modification_MoveComponent(Component myComp, Behavior newBehavior) {
		this.myComp = myComp;
		this.myBehavior = newBehavior;

	}

	@Override
	public void invoke(ModeEnum currentMode, InteractivityController myController) {
		

	}

}
