package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.GameData.State.Tile;
import backEnd.Mode.ModeException;

public class Modification_ChangeMode implements ModificationFromUser {

	@Override
	public void invoke(ModelImpl myModel) {
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			myModel.getMode().toggleUserMode();		
			break;
		case "PLAYER":
			myModel.getGameProcessController().pause();
			myModel.getMode().toggleUserMode();
			break;
		}	
	}
	
	

}
