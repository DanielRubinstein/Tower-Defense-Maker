package backEnd.Interaction;

import backEnd.Mode.ModeEnum;
import backEnd.State.State;
import javafx.geometry.Point2D;
import backEnd.GameEngine.*;

public class Interaction_AddComponent implements UserInteraction{
	
	private State myState;
	private Component newComp;
	private Point2D location;
	
	public Interaction_AddComponent(State myState, Component newComp, Point2D loc ){
		this.myState = myState;
		this.newComp = newComp;
		this.location = loc;
		
	}

	@Override
	public void invoke(ModeEnum currentMode) {
		myState.getComponentGraph().addComponentToGrid(newComp, location);
		
	}
}
