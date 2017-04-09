package ModificationFromUser;

import backEnd.Model;

public class Modification_Pause implements ModificationFromUser {

	@Override
	public void invoke(Model myModel) throws Exception {
		// does nothing in case AUTHOR
		switch(myModel.getMode().getUserMode()){
		case PLAYER:
			pauseGame(myModel);
		case AUTHOR:
			break;
		}

	}
	
	private void pauseGame(Model myModel){
		//FIXME not sure how to pause game engine running
		myModel.getGameProcessController().pause(); //?
	}

}
