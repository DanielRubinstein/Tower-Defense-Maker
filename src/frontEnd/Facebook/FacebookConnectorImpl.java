package frontEnd.Facebook;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Class to allow a user to connect to Facebook using his/her username and password. This class sets up the appropriate 
 * connection with Facebook and obtains all relevant access codes from the user.
 * The appId, appSecret, and URL's all come from the Application's Settings page on Facebook. All of the private static
 * Strings are necessarily copied from this app's settings page. Several additional access permissions are included as
 * they are the most important for implementing additional features.
 * See the Facebook Developer Docs for further information. 
 * This class and the entire package rely on the restfb API, and consequently the FacebookGraph API. 
 * @author Tim
 *
 */
public class FacebookConnectorImpl implements FacebookConnector {
	
	private static final String appId = "426668214360430";
	private static final String appSecret = "d97cba98608128cdb4ca19e1da091de5";
	private FacebookClient userClient;
	private static final String redirectURL = "&redirect_uri=https://www.facebook.com/connect/login_success.html";
	private static final String scopeRequirements = "&scope=publish_actions,manage_pages,read_page_mailboxes,pages_messaging";
	private static final String responseType = "&response_type=code%20token";
	private static final String totalLoginURL = "https://www.facebook.com/v2.8/dialog/oauth?client_id=" + appId + 
			  redirectURL +responseType + scopeRequirements;
	private static final String DEFAULT_ACCESS_PATTERN = ".*access_token=(.*)&expires.*";
	private FacebookInteractorImpl myInteractor;
	
	public FacebookConnectorImpl(){
		Version v = Version.LATEST;
		userClient = new DefaultFacebookClient(v);
		myInteractor = new FacebookInteractorImpl(userClient,appId);
	}
	

	@Override
	public FacebookInteractor getInteractor(){
		return myInteractor;
	}


	@Override
	public void login(){
		try {
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
				
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	private WebEngine loadPage() {
		try {
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			webEngine.load(totalLoginURL);
			browser.autosize();
			Stage dialog = new Stage();
			Scene scene = new Scene(browser, browser.getPrefWidth(), browser.getPrefHeight());
			dialog.setScene(scene);
			dialog.show();
			return webEngine;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
