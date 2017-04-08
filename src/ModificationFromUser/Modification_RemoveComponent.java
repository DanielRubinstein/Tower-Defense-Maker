package ModificationFromUser;

import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.State.State;
import javafx.geometry.Point2D;
import main.InteractivityController;

public class Modification_RemoveComponent implements ModificationFromUser {
	
	private Component removeComp;

	public Modification_RemoveComponent(Component removeComp){
		this.removeComp = removeComp;
		
	}
	
	@Override
	public void invoke(ModeEnum currentMode, InteractivityController myController) {
		myController.getState().getComponentGraph().removeComponent(removeComp);
		
	}

}
