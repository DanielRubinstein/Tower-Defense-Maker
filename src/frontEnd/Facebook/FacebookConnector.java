package frontEnd.Facebook;
import java.io.IOException;

import com.restfb.*;
import com.restfb.FacebookClient.AccessToken;

public class FacebookConnector {
	
	private FacebookClient fbClient;
	private String appId = "426668214360430";
	private String appSecret = "d97cba98608128cdb4ca19e1da091de5";
	//426668214360430|NBuH0Pq0TADVIk_O-kwslAtGq9k
	
	
	public FacebookConnector(){
		Version v = Version.LATEST;
		fbClient = new DefaultFacebookClient(v);
		AccessToken appToken = fbClient.obtainAppAccessToken(appId, appSecret);
		AccessToken cToken = fbClient.obtainUserAccessToken(appId, appSecret, redirectUri, verificationCode);
	}
	public FacebookClient.AccessToken login(String code, String redirectUrl) throws IOException{

		
		WebRequestor wr = new DefaultWebRequestor();
		WebRequestor.Response accessTokenResponse = wr.executeGet(
	            "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl
	            + "&client_secret=" + appSecret + "&code=" + code);
		
		return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
	}
}
