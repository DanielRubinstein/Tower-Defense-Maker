package frontEnd.Facebook;

import com.restfb.exception.FacebookException;

import javafx.scene.image.ImageView;
/**
 * This interface defines how one can interact with various features of Facebook. These methods heavily utilize the restfb
 * API and further interactions with Facebook can be implemented by looking at restfb's documentation and adding those methods
 * accordingly.
 * @author Tim
 *
 */
public interface FacebookInteractor {

	/**
	 * Gets the profile picture for the user.
	 * @return ImageView of this profile picture.
	 * @throws FacebookException
	 */
	public ImageView getPicture() throws FacebookException;
	
	/**
	 * Posts this message to the user's Facebook feed along with a reference to the app's Facebook page.
	 * The purpose of this is to improve popularity of the app and let one's friends see that this person is playing the app.
	 * @param message
	 * @throws FacebookException
	 */
	public void shareToWall(String message) throws FacebookException;

}
