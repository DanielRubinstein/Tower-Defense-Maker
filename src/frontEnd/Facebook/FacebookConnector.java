package frontEnd.Facebook;


/**
 * This interface defines how one can connect with Facebook using the App's information and user information.
 * 
 * To connect to FB and interact with Facebook, one should login(), get the FacebookInteractor, and call methods through
 * that interface.
 * @author Tim
 *
 */
public interface FacebookConnector {
	/**
	 * Launches a window which allows the user to log in using username and password.
	 */
	public void login();
	/**
	 * Gets the FacebookInteractor which one uses to send and receive information with Facebook.
	 * @return
	 */
	public FacebookInteractor getInteractor();

}