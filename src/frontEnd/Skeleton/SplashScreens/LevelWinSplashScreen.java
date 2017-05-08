package frontEnd.Skeleton.SplashScreens;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 * @author Derek
 *
 */
public class LevelWinSplashScreen extends SplashScreenImpl{
	
	public LevelWinSplashScreen(EventHandler restartLevel, EventHandler continueToNextLevel){
		super(restartLevel);
		Button continueButton = new Button(getResource("NextLevelButtonText"));
		continueButton.setOnMouseClicked(continueToNextLevel);
		addButton(continueButton);
		setMessage(getResource("LevelWinMessage"));
		setImage(new Image(getResource("LevelWinImagePath")));
	}

}
