package ModificationFromUser;

import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.State.State;
import javafx.geometry.Point2D;

public class Modification_RemoveComponent implements ModificationFromUser {
	
	private Component removeComp;
	private State myState;

	public Modification_RemoveComponent(State myState, Component removeComp){
		this.myState = myState;
		this.removeComp = removeComp;
		
	}
	
	@Override
	public void invoke(ModeEnum currentMode) {
		myState.getComponentGraph().removeComponent(removeComp);
		
	}

}
