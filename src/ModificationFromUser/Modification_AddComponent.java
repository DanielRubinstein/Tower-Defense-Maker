package ModificationFromUser;

import backEnd.Mode.ModeEnum;
import backEnd.State.State;
import javafx.geometry.Point2D;
import main.InteractivityController;
import backEnd.GameEngine.*;

public class Modification_AddComponent implements ModificationFromUser{
	
	private State myState;
	private Component newComp;
	private Point2D location;
	
	public Modification_AddComponent(Component newComp, Point2D loc ){
		this.newComp = newComp;
		this.location = loc;
		
	}

	@Override
	public void invoke(ModeEnum currentMode, InteractivityController myController) {
		myState.getComponentGraph().addComponentToGrid(newComp, location);
		
	}
}
