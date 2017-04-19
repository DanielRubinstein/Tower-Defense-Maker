package frontEnd.Facebook;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Class to allow a user to connect to Facebook using his/her username and password. This class sets up the appropriate 
 * connection with Facebook and obtains all relevant access codes from the user.
 * The appId and appSecret come from the Application's Settings page on Facebook and should be passive in. 
 * All of the private static Strings are necessarily copied from this the API's specifications to logging in.
 * Several additional access permissions are included via the resource bundle, which allow for certain interactions.
 * See the Facebook Developer Docs for further information. 
 * This class and the entire package rely on the restfb API, and consequently the FacebookGraph API. 
 * @author Tim
 *
 */
public class FacebookConnectorImpl implements FacebookConnector {
	
	private static final String redirectURL = "&redirect_uri=https://www.facebook.com/connect/login_success.html";
	private static final String responseType = "&response_type=code%20token";
	private static final String DEFAULT_ACCESS_PATTERN = ".*access_token=(.*)&expires.*";
	private static final String DEFAULT_CONNECTION_URL ="https://www.facebook.com/v2.8/dialog/oauth?client_id=";
	
	private String scopeRequirements;
	private String appId;
	private String appSecret;
	private String totalLoginURL;
	private FacebookClient userClient;
	private FacebookInteractorImpl myInteractor;

	private static final String DEAFULT_RESOURCE_BUNDLE = "resources/facebook";
	private ResourceBundle info = ResourceBundle.getBundle(DEAFULT_RESOURCE_BUNDLE);
	private String scopeInfoKey = "permissions";
	
	/**
	 * Initializes this class with the applicationID and applicationSecret found from the app's Facebook page after
	 * the app has been created on Facebook.
	 * @param applicationId
	 * @param applicationSecret
	 */
	public FacebookConnectorImpl(String applicationId, String applicationSecret){
		scopeRequirements = info.getString(scopeInfoKey);
		appId = applicationId;
		appSecret = applicationSecret;
		totalLoginURL=  DEFAULT_CONNECTION_URL+ appId + redirectURL +responseType + scopeRequirements;
		userClient = new DefaultFacebookClient(Version.LATEST);
		myInteractor = new FacebookInteractorImpl(userClient,appId);
	}
	

	@Override
	public FacebookInteractor getInteractor(){
		return myInteractor;
	}


	@Override
	public void login() throws FacebookException{
		WebEngine web = loadPage();
		web.setOnStatusChanged(e -> {
			String redirect = web.getLocation();
			Pattern p = Pattern.compile(DEFAULT_ACCESS_PATTERN);
			Matcher m = p.matcher(redirect);	
			if(m.matches()){
				String accessCode = m.group(1);
				userClient = new DefaultFacebookClient(accessCode,appSecret,Version.LATEST);
				myInteractor.setClient(userClient);
				web.setOnStatusChanged(null);
			}
		});		
	}
	
	/**
	 * Launches the page to allow the user to log in.
	 * @return WebEngine
	 * @throws FacebookException
	 */
	private WebEngine loadPage() throws FacebookException{
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load(totalLoginURL);
		browser.autosize();
		
		Stage dialog = new Stage();
		Scene scene = new Scene(browser, browser.getPrefWidth(), browser.getPrefHeight());
		dialog.setScene(scene);
		dialog.show();
		return webEngine;
	}
	
	
}
