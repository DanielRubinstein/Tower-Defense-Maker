package frontEnd.Menus;

import java.util.function.Consumer;

import backEnd.GameData.GameData;
import javafx.stage.Stage;

/**
 * If the user decides to create a game, this class is instantiated and offers a
 * series of dialog boxes to the user in order to create a game
 * 
 * @author Miguel Anderson
 *
 */
public class GameMaker {

	public GameMaker(Stage stage, Consumer<GameData> gameDataConsumer) {
		stage.show();
		System.out.println("MAKE A GAME");
	}

}
