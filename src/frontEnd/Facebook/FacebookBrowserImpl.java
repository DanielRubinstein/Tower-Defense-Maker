package frontEnd.Facebook;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookException;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class launches a browser, through which one can log into Facebook. This class is an example (and the default)
 * implementation of how one can retrieve the user's access token. This is necessary for establishing a connection to
 * Facebook and is required for the FacebookConnector to work.
 * @author Tim
 *
 */
public class FacebookBrowserImpl implements FacebookBrowser {
	//these constants will not change as they are how the URL must be set up and parsed
	private static final String redirectURL = "&redirect_uri=https://www.facebook.com/connect/login_success.html";
	private static final String responseType = "&response_type=code%20token";
	private static final String DEFAULT_CONNECTION_URL ="https://www.facebook.com/v2.8/dialog/oauth?client_id=";
	private static final String DEFAULT_ACCESS_PATTERN = ".*access_token=(.*)&expires.*";
	
	private String totalLoginURL;
	private String scopeRequirements;
	
	private String appId;
	private String accessToken;
	private Stage dialogStage;
	
	private static final String DEAFULT_RESOURCE_BUNDLE = "resources/facebook";
	private ResourceBundle info = ResourceBundle.getBundle(DEAFULT_RESOURCE_BUNDLE);
	private String scopeInfoKey = "permissions";
	
	/**
	 * Initializes this class given the applicationId of the application being run. This String can be obtained
	 * from the application's settings on the Facebook page.
	 * @param applicationId
	 */
	public FacebookBrowserImpl(String applicationId){
		scopeRequirements = info.getString(scopeInfoKey);
		appId = applicationId;
		totalLoginURL=  DEFAULT_CONNECTION_URL+ appId + redirectURL +responseType + scopeRequirements;
	}
	/* (non-Javadoc)
	 * @see voogasalad.util.facebook.FacebookBrowser#getAccessToken()
	 */
	@Override
	public String getAccessToken(){
		return accessToken;
	}
	
	/* (non-Javadoc)
	 * @see voogasalad.util.facebook.FacebookBrowser#launchPage()
	 */
	@Override
	public void launchPage() throws FacebookException{
		WebEngine web = loadPage();
		web.setOnStatusChanged(e -> {
			String redirect = web.getLocation();
			Pattern p = Pattern.compile(DEFAULT_ACCESS_PATTERN);
			Matcher m = p.matcher(redirect);	
			if(m.matches()){
				accessToken = m.group(1);
				web.setOnStatusChanged(null);
			}
		});		
	}
	
	public void onDialogClose(EventHandler<WindowEvent> event){
		dialogStage.setOnCloseRequest(event);
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
		dialogStage = new Stage();
		Scene scene = new Scene(browser, browser.getPrefWidth(), browser.getPrefHeight());
		dialogStage.setScene(scene);
		dialogStage.show();
		return webEngine;
	}
	
	
	
}
