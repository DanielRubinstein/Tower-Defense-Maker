package frontEnd.Facebook;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookResponseContentException;
import com.restfb.json.JsonObject;
import com.restfb.types.Conversation;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;

import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FacebookConnectorImpl implements FacebookConnector {
	
	private String appId = "426668214360430";
	private String appSecret = "d97cba98608128cdb4ca19e1da091de5";
	private FacebookClient userClient;
	private String redirectURL = "https://www.facebook.com/connect/login_success.html";
	private String totalLoginURL = "https://www.facebook.com/v2.8/dialog/oauth?client_id=" + appId + 
			"&redirect_uri=" + redirectURL + "&response_type=code%20token"+"&scope=publish_actions,manage_pages,read_page_mailboxes,pages_messaging";
	private String DEFAULT_ACCESS_PATTERN = ".*access_token=(.*)&expires.*";
	private String DEFAULT_PICTURE_PATTERN = ".*\"url\":\"(.*)\".*";
	
	public FacebookConnectorImpl(){
		Version v = Version.LATEST;
		userClient = new DefaultFacebookClient(v);

	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Facebook.FacebookConnector#getPicture()
	 */
	@Override
	public ImageView getPicture() throws Exception{
		JsonObject picture = userClient.fetchObject("me/picture", JsonObject.class, Parameter.with("redirect","false"));
		Pattern p = Pattern.compile(DEFAULT_PICTURE_PATTERN);
		Matcher m = p.matcher(picture.toString());
		if(m.matches()){
			String imageURL = m.group(1);
			Image im = new Image(imageURL);
			ImageView viewImage = new ImageView(im);
			return viewImage;
		}else{
			throw new FacebookResponseContentException("couldnt find image", new IOException("File not found"));
		}	
		
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Facebook.FacebookConnector#getMessages()
	 */
	@Override
	public ScrollPane getMessages(){
		ScrollPane scroll = new ScrollPane();
		VBox content = new VBox();
		Connection<Conversation> pages = userClient.fetchConnection("me/conversations", Conversation.class);
		for(Conversation c: pages.getData()){
			content.getChildren().add(new Label(c.getSnippet()));
		}
		scroll.setContent(content);
		return scroll;
	}


	/* (non-Javadoc)
	 * @see frontEnd.Facebook.FacebookConnector#shareToWall(java.lang.String)
	 */
	@Override
	public void shareToWall(String message) throws FacebookException{
		Collection<Parameter> params = new ArrayList<Parameter>();
		Page myPage = userClient.fetchObject(appId, Page.class);
		params.add(Parameter.with("message", message));
		params.add(Parameter.with("link", myPage.getLink()));
		Parameter[] postParamsArray = params.toArray(new Parameter[params.size()]);
		try{
			FacebookType post = userClient.publish("me/feed", FacebookType.class, postParamsArray);
		} catch (com.restfb.exception.FacebookOAuthException e){
			throw new FacebookException(e);
		}

	}
	/* (non-Javadoc)
	 * @see frontEnd.Facebook.FacebookConnector#log2()
	 */
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
