package frontEnd.Facebook;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.PackageElement;

import com.restfb.*;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FacebookConnector {
	
	private FacebookClient fbClient;
	private String appId = "426668214360430";
	private String appSecret = "d97cba98608128cdb4ca19e1da091de5";
	//426668214360430|NBuH0Pq0TADVIk_O-kwslAtGq9k
	private String tempAccess = "EAACEdEose0cBAIqI60xPAZBZAgGBSXZCe30Q7fjRUeBSJbdpy1SX6IbW2wE9PNf1Pa183HJvyXmTne7KdRR93qNa3fK8w6yngKZCGwCRHqFfrq2IVg5GayuqCGTjCQyLRG0ZBHfd5mKKSW6xamw03JT4NdwQcjPUaYi5JZBYq9ki9t8juBOQ0p7uMtRtkZCeIgZD";
	private FacebookClient userClient;
	private String redirectURL = "https://www.facebook.com/connect/login_success.html";
	private String totalLoginURL = "https://www.facebook.com/v2.8/dialog/oauth?client_id=" + appId + 
			"&redirect_uri=" + redirectURL + "&response_type=code%20token"+"&scope=publish_actions,manage_pages";
	private String DEFAULT_ACCESS_PATTERN = "access_token=.*&";
	private String DEFAULT_CODE_PATTERN = "";
	
	public FacebookConnector(){
		Version v = Version.LATEST;
		userClient = new DefaultFacebookClient(v);
		//AccessToken appToken = userClient.obtainAppAccessToken(appId, appSecret);
		//FacebookClient aClient = new DefaultFacebookClient(appToken.getAccessToken(),v);
		//testClient = aClient;
		//AccessToken cToken = fbClient.obtainUserAccessToken(appId, appSecret, redirectUri, verificationCode);
		//Parameter p = new Parameter();
	}
	
	public void log(){
		WebRequestor wr = new DefaultWebRequestor();
		ScopeBuilder s = new ScopeBuilder();
		String loginUrrr = fbClient.getLoginDialogUrl(appId, redirectURL, s);
		
		try {

			WebRequestor.Response loginr = wr.executeDelete(loginUrrr);
			//wr.exeute
			System.out.println(loginr);
			//System.out.println(loginr.getBody());
			//3D426668214360430
			String code = "AQBiJg5180ew8ExgFqtm6n5E8glgMv6PIH4KTt3ZQvHF8PCOvUS2hI8ZFsjRgjrCfLsRB5GvQ4_BcbNqPNag4co0IdaG7H9X3m_HxtBluIamwHOCF5cKFjVlG-Ba1qhnHVRE0dTbw48FFmyIjU8oMmIpWjgLLqpu41cQ9Vpuejn-v4nn7fVzOX5Oakftd0lZSC0SmWjZyJmYjf6B15G5BCGyhPmiYDuH--bNWaXlfrrL_2Kv2yzOyN6TsM7lp2L4s5c-jiD0yHsVe21QWz8p6KZD1ZX14Cuwoq-9onDUsacutNWdO0C9Fuk_irv79gdFk";
			//AccessToken a = login("AQBs41zRb90swBt4EHE6hjEMAVfMRXZn6IDCrO5dvnuyMDl5aic_AuTOfZzEXRD0sGiXLJw-qYWPgDYdbBVjaG46FDHYYI3HRxv2K8HbV1EQ0tuIHrAz5it9t7mSXv_S0eaK9KMxpCxmczhnLOzBZ2QTbfd1iE7DQsRdMhTJmDZN396FWWP9hHwBw0GLiHJwWWY2foJvZlLj9y2oZmOFBbLfTKMjP8uAAURAXdQzsoxjaTsg7afQY8gXtTL9_HRbBQ7njCHaHw9DMN7MUaUR-UvR1XoJqNcZeaqfbEnqm6nOsKOQp0SeMIWTpmwUoelMgdM");
			AccessToken a = fbClient.obtainUserAccessToken(appId, appSecret, redirectURL, code);
			System.out.println(a.getAccessToken());
			FacebookClient aClient = new DefaultFacebookClient(a.getAccessToken(),appSecret,Version.LATEST);
			User us = aClient.fetchObject("me", User.class);
			System.out.println(us.getName());
			//DefaultFacebookClient.AccessToken.fromQueryString(loginr.getBody());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	
	public FacebookClient.AccessToken login(String code) throws IOException{

		WebRequestor wr = new DefaultWebRequestor();
		WebRequestor.Response accessTokenResponse = wr.executeGet(
	            "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectURL
	            + "&client_secret=" + appSecret + "&code=" + code);
		System.out.println("*****");
		System.out.println(accessTokenResponse.getBody());

		System.out.println(DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody()).getAccessToken());
		return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
	}
	public void log2(){
		ScopeBuilder s = new ScopeBuilder();
		String loginUrrr = userClient.getLoginDialogUrl(appId, redirectURL, s);
		
		try {
			WebEngine web = loadPage();
			web.setOnStatusChanged(e -> {
				String redirect = web.getLocation();
				Pattern p = Pattern.compile(".*access_token=(.*)&expires.*");
				Matcher m = p.matcher(redirect);	
				if(m.matches()){
					String accessCode = m.group(1);
					userClient = new DefaultFacebookClient(accessCode,appSecret,Version.LATEST);
					Collection<Parameter> params = new ArrayList<Parameter>();
					User user = userClient.fetchObject("me", User.class);
					Connection<Post> myFeed = userClient.fetchConnection("me/feed", Post.class);
					System.out.println("user name " +user.getName()+ " " +user.getWebsite());
					params.add(Parameter.with("message", "my message"));
					Parameter[] postParamsArray = params.toArray(new Parameter[params.size()]);
					web.setOnStatusChanged(null);
					
					//Page myPage = userClient.fetchObject("me/feed", Page.class);
					//FacebookType message = userClient.publish("me/feed", FacebookType.class, postParamsArray);
					//userClient.publish(connection, objectType, parameters)
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
			//webEngine.load(new File(path).toURI().toURL().toExternalForm());
			webEngine.load(totalLoginURL);
			browser.autosize();
			Stage dialog = new Stage();
			Scene scene = new Scene(browser, browser.getPrefWidth(), browser.getPrefHeight());
			dialog.setScene(scene);
			dialog.show();
			
			webEngine.setOnVisibilityChanged(e -> System.out.println("((((("));
			return webEngine;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		

		
	}
	
}
