package frontEnd.Facebook;

import com.restfb.exception.FacebookException;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * This interface defines the default way to obtain an access key, via a popup Browser where the user can
 * log into the Facebook website and improve permissions. This is the default way as the Facebook website
 * provides a clear explanation of our app and its permissions to the user logging in.
 * @author Tim
 *
 */
public interface FacebookBrowser {

	/**
	 * Gets the access token to allow the user to log in to FacebookConnector. Be sure to call this after the 
	 * webpage has closed to get the accurate access token. 
	 * @return accessToken
	 */
	String getAccessToken();

	/**
	 * Launches the page where the user will be directed to the Facebook Login page. This also allows them to
	 * access or deny any permissions this app requires, which necessary to retrieve the access token.
	 * @throws FacebookException
	 */
	void launchPage() throws FacebookException;
	
	/**
	 * Sets the event as the eventhandler when the dialogue browser closes
	 * @param event
	 */
	public void onDialogClose(EventHandler<WindowEvent> event);

}