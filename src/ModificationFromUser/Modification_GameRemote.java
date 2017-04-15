package ModificationFromUser;

import java.util.function.Consumer;

import backEnd.Model;
import backEnd.ModelImpl;

public enum Modification_GameRemote implements ModificationFromUser {
	PLAY (model -> {
		System.out.println("PLAY GAME");
		//FIXME should ultimately execute animation.play()
		model.play();
		}),
	PAUSE (model -> {
		System.out.println("PAUSE GAME");
		//model.pause();
		}),
	FASTFORWARD (model -> {
		System.out.println("FASTFORWARD");
		//model.fastforward();
		}),
	RESTART (model -> {
		System.out.println("RESTART LEVEL");
		//model.play();
		}),
	NEXTLEVEL (model -> {
		System.out.println("NEXT LEVEL");
		//model.play();
		});

	private Consumer<Model> myConsumer;
	public static final double DEFAULT_STEP_TIME = 1000; 
	
	
	Modification_GameRemote(Consumer<Model> consumer){
		myConsumer = consumer;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		switch (this) {
		case PLAY:
			myConsumer.accept(myModel);
			break;
		default:
			switch (myModel.getMode().getUserMode()) {
			case PLAYER:
				myConsumer.accept(myModel);
				break;
			case AUTHOR:
				// do nothing
				break;
			}
		}

	}

}
