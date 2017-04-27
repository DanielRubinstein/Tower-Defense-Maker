package frontEnd.Skeleton.SplashScreens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import resources.Constants;

public class SplashScreen extends Scene
{
	private BorderPane myRoot;
	
	public SplashScreen(SplashScreenData data)
	{
		super(new BorderPane());
		myRoot = (BorderPane) getRoot();
		
		getStylesheets().add(Constants.DEFAULT_CSS);
		
		myRoot.setBottom(new Text(data.getMessageBody()));
		
		setContinue(data.getOnContinue());
	}

	private void setContinue(Runnable onContinue)
	{
		setOnKeyPressed(new EventHandler<Event>()
		{
			@Override
			public void handle(Event e)
			{
				onContinue.run();
			}

		});
	}
	
	
}
