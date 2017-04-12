package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeException;

/**
 * Used when user creates a new preset component
 * 
 * @author Derek
 *
 */
public class Modification_CreateNewPresetComponent implements ModificationFromUser {

	private Component newComp;
	private String newCompName;
	public static final String DESCRIPTION = "Create Preset Component";	
	
	public Modification_CreateNewPresetComponent(String newCompName, Component newComp){
		this.newComp = newComp;
		this.newCompName = newCompName;
	}

	//FIXME currently the new preset will overwrite an existing preset with the same name, 
	// based on the implementation of addNewComponent()
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myModel.getBankController().addNewComponent(newCompName, newComp);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}
		
	}

	
}
