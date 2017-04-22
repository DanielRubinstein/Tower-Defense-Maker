package frontEnd.Facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookException;

/**
 * Class to allow a user to connect to Facebook using his/her username and password. This class sets up the appropriate 
 * connection with Facebook after getting the access token.
 * The appId and appSecret come from the Application's Settings page on Facebook and should be passive in. 
 * All of the private static Strings are necessarily copied from this the API's specifications to logging in.
 * Several additional access permissions are included via the resource bundle, which allow for certain interactions.
 * See the Facebook Developer Docs for further information. 
 * This class and the entire package rely on the restfb API, and consequently the FacebookGraph API. 
 * @author Tim
 *
 */
public class FacebookConnectorImpl implements FacebookConnector {

	private String appId;
	private String appSecret;
	private FacebookClient userClient;
	private FacebookInteractorImpl myInteractor;

	
	/**
	 * Initializes this class with the applicationID and applicationSecret found from the app's Facebook page after
	 * the app has been created on Facebook.
	 * @param applicationId
	 * @param applicationSecret
	 */
	public FacebookConnectorImpl(String applicationId, String applicationSecret){
		appId = applicationId;
		appSecret = applicationSecret;
		userClient = new DefaultFacebookClient(Version.LATEST);
		myInteractor = new FacebookInteractorImpl(userClient,appId);
	}
	
	@Override
	public FacebookInteractor getInteractor(){
		return myInteractor;
	}

	@Override
	public void login(String accessToken) throws FacebookException{
		userClient = new DefaultFacebookClient(accessToken,appSecret,Version.LATEST);
		myInteractor.setClient(userClient);
	}
	
	
}
