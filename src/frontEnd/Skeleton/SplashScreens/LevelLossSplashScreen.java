package frontEnd.Skeleton.SplashScreens;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class LevelLossSplashScreen extends SplashScreenImpl {

	public LevelLossSplashScreen(EventHandler restartLevel, EventHandler restartGame) {
		super(restartLevel);
		Button restartGameButton = new Button(getResource("RestartGameButtonText"));
		restartGameButton.setOnMouseClicked(restartGame);
		addButton(restartGameButton);
		setMessage(getResource("LevelLossMessage"));
		setImage(new Image(getResource("LevelLossImagePath")));
	}

}
