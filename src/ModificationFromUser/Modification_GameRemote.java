package ModificationFromUser;

import java.util.function.Consumer;

import backEnd.Model;
import backEnd.GameEngine.Engine.GameProcessController;

/**
 * This class can be seen as a game remote. It holds the invokables to play and
 * pause the game / game process controller / engine. It also can support other
 * invokables that control the time loop or gameplay of the user
 * 
 * @author Miguel Anderson
 *
 */

public enum Modification_GameRemote implements ModificationFromUser {
	PLAY(engine -> {
		System.out.println("Invokable to PLAY GAME");
		engine.playAnimation();
	}), PAUSE(engine -> {
		if (!engine.getEngineStatus().get().equals("PAUSED")) {
			System.out.println("Invokable to PAUSE GAME");
			engine.pause();
		}
	}), FASTFORWARD(engine -> {
		System.out.println("FASTFORWARD");
		// engine.fastforward();
	}), RESTART(engine -> {
		System.out.println("RESTART LEVEL");
		// engine.play();
	}), NEXTLEVEL(engine -> {
		System.out.println("NEXT LEVEL");
		// engine.play();
	});

	private Consumer<GameProcessController> myConsumer;

	Modification_GameRemote(Consumer<GameProcessController> consumer) {
		myConsumer = consumer;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		myConsumer.accept(myModel.getGameProcessController());

	}

}
