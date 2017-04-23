package frontEnd.Facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookResponseContentException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class implements FacebookInteractor and allows one to send and receive information with Facebook.
 * To add a new feature or way to interface with Facebook, one should read the restFB document. Following this class
 * and those methods, this class is very extendible.
 * @author Tim
 *
 */
public class FacebookInteractorImpl implements FacebookInteractor{
	
	private FacebookClient userClient;
	private static String myAppId;
	private static final String DEFAULT_PICTURE_PATTERN = ".*\"url\":\"(.*)\".*";
	
	public FacebookInteractorImpl(FacebookClient client, String appId){
		userClient = client;
		myAppId=appId;
		
	}
	
	void setClient(FacebookClient c){
		userClient = c;
	}
	
	@Override
	public void shareToWall(String message) throws FacebookException{
		Collection<Parameter> params = new ArrayList<Parameter>();
		Page myPage = userClient.fetchObject(myAppId, Page.class);
		params.add(Parameter.with("message", message));
		params.add(Parameter.with("link", myPage.getLink()));
		Parameter[] postParamsArray = params.toArray(new Parameter[params.size()]);
		FacebookType post = userClient.publish("me/feed", FacebookType.class, postParamsArray);
	}
	
	@Override
	public ImageView getPicture() throws FacebookException{
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
	

	

}
