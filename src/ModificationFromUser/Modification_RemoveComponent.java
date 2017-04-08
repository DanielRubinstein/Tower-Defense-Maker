package ModificationFromUser;

import backEnd.Model;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import javafx.geometry.Point2D;

public class Modification_RemoveComponent implements ModificationFromUser {
	
	private Component removeComp;

	public Modification_RemoveComponent(Component removeComp){
		this.removeComp = removeComp;
	}
	
	@Override
	public void invoke(ModeEnum currentMode, Model myController) {
		myController.getState().getComponentGraph().removeComponent(removeComp);
		
	}

}
