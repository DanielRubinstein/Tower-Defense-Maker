package ModificationFromUser;

import java.util.function.Consumer;

import backEnd.Model;

public enum Modification_GameRemote implements ModificationFromUser {
	PLAY (model -> {
		System.out.println("PLAY GAME");
		//model.getGameProcessController().play();
		}),
	PAUSE (model -> {
		System.out.println("PAUSE GAME");
		//model.getGameProcessController().pause();
		}),
	FASTFORWARD (model -> {
		System.out.println("FASTFORWARD");
		//model.getGameProcessController().fastforward();
		}),
	RESTART (model -> {
		System.out.println("RESTART LEVEL");
		//model.getGameProcessController().play();
		}),
	NEXTLEVEL (model -> {
		System.out.println("NEXT LEVEL");
		//model.getGameProcessController().play();
		});

	private Consumer<Model> myConsumer;
	
	Modification_GameRemote(Consumer<Model> consumer){
		myConsumer = consumer;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		switch (this) {
		case PLAY:
			myConsumer.accept(myModel);
		default:
			switch (myModel.getMode().getUserMode()) {
			case PLAYER:
				myConsumer.accept(myModel);
			case AUTHOR:
				// do nothing
			}
		}

	}

}
