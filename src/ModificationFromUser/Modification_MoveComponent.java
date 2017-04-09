package ModificationFromUser;

import backEnd.Model;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;

public class Modification_MoveComponent implements ModificationFromUser {

	private Component myComp;
	private Behavior myBehavior;
	
	public Modification_MoveComponent(Component myComp, Behavior newBehavior) {
		this.myComp = myComp;
		this.myBehavior = newBehavior;

	}

	@Override
	public void invoke(ModeEnum currentMode, Model myController) {
		

	}

}
