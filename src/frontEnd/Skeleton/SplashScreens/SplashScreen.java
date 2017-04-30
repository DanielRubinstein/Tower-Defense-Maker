package frontEnd.Skeleton.SplashScreens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public class SplashScreen
{
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private BorderPane myBP;
	private Scene myScene;
	
	public SplashScreen(SplashScreenData data)
	{
		myBP = new BorderPane();
		

		
		myBP.setCenter(new Label(data.getMessageBody()));

		
		
		myScene = new Scene(myBP);
		myScene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
		setContinue(data.getOnContinue());
		
	}

	private void setContinue(Runnable onContinue)
	{
		myScene.setOnKeyPressed(new EventHandler<Event>()
		{
			@Override
			public void handle(Event e)
			{
				onContinue.run();
			}

		});
	}
	
	
	public void display(Stage myStage){
		myStage.setScene(myScene);
		myStage.show();
	}
	
	
	
}
