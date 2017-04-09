package ModificationFromUser;

import backEnd.Model;

public class Modification_Play implements ModificationFromUser {

	@Override
	public void invoke(Model myModel) throws Exception {
		switch(myModel.getMode().getUserMode()){
		case AUTHOR:
			myModel.getMode().switchMode();
			playGame(myModel);
		case PLAYER:
			playGame(myModel);
		}

	}
	
	private void playGame(Model myModel){
		//FIXME not sure how to start game engine running
		myModel.getGameProcessController().play(); //?
	}

}