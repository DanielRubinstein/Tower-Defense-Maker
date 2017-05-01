package frontEnd.Skeleton.SplashScreens;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class LevelWinSplashScreen extends SplashScreenImpl{
	
	public LevelWinSplashScreen(EventHandler restartLevel, EventHandler continueToNextLevel){
		super(restartLevel);
		Button continueButton = new Button("Continue to Next Level");
		continueButton.setOnMouseClicked(continueToNextLevel);
		addButton(continueButton);
		
		setImage(new Image("resources/images/splashscreens/youwin.png"));
	}

}
