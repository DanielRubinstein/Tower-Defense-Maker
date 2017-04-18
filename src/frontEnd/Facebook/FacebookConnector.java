package frontEnd.Facebook;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public interface FacebookConnector {

	public ImageView getPicture() throws Exception;

	public ScrollPane getMessages();

	public void shareToWall(String message) throws FacebookException;

	public void login();

}