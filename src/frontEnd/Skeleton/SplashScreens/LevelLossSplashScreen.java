package frontEnd.Skeleton.SplashScreens;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class LevelLossSplashScreen extends SplashScreenImpl {

	public LevelLossSplashScreen(EventHandler restartLevel, EventHandler restartGame) {
		super(restartLevel);
		Button restartGameButton = new Button("restartGame");
		restartGameButton.setOnMouseClicked(restartGame);
		addButton(restartGameButton);
		
		setImage(new Image("resources/images/splashscreens/lose.png"));
	}

}
