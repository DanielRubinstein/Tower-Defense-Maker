package frontEnd.Skeleton.SplashScreens;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class GameWinSplashScreen extends SplashScreenImpl {
	public GameWinSplashScreen(EventHandler restartLevel, EventHandler restartGame){
		super(restartLevel);
		Button restartGameButton = new Button(getResource("RestartGameButtonText"));
		restartGameButton.setOnMouseClicked(restartGame);
		addButton(restartGameButton);
		setMessage(getResource("GameWinMessage"));
		setImage(new Image(getResource("GameWinImagePath")));
	}

}
