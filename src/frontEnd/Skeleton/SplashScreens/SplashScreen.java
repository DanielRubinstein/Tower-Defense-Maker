package frontEnd.Skeleton.SplashScreens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import resources.constants.NumericConstants;
import resources.constants.StringConstants;

public class SplashScreen extends Scene
{
	private StringConstants stringConstants = new StringConstants();
	private BorderPane myRoot;
	
	public SplashScreen(SplashScreenData data)
	{
		super(new BorderPane());
		myRoot = (BorderPane) getRoot();
		
		getStylesheets().add(stringConstants.getDefaultCSS());
		
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
