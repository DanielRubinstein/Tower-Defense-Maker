package ModificationFromUser;

import backEnd.Mode.ModeEnum;
import javafx.geometry.Point2D;
import backEnd.Model;
import backEnd.GameData.State.State;
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
	public void invoke(ModeEnum currentMode, Model myController) {
		myState.getComponentGraph().addComponentToGrid(newComp, location);
		
	}
}
