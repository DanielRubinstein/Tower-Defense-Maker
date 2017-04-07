package ModificationFromUser;

import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;
import backEnd.State.State;
import javafx.geometry.Point2D;

public class Modification_CreateNewPresetComponent implements ModificationFromUser {

	private Component newComp;
	public static final String ERROR_MESSAGE = "You cannot create preset components in player mode!";
	
	public Modification_CreateNewPresetComponent(Component newComp){
		this.newComp = newComp;
		
	}

	
	@Override
	public void invoke(ModeEnum currentMode) {
		switch (currentMode) {
		case AUTHOR:
			componentBank.addPreset(newComp);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}
		
		
	}

	
}
