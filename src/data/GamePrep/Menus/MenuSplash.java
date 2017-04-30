package data.GamePrep.Menus;

import java.util.function.Consumer;

import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.Facebook.FacebookBrowser;
import frontEnd.Facebook.FacebookBrowserImpl;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookConnectorImpl;
import frontEnd.Facebook.FacebookInteractor;
import frontEnd.Skeleton.UserTools.HelpOptions;
import frontEnd.Skeleton.UserTools.HelpOptionsImpl;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public class MenuSplash {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private String appSecret = stringResourceBundle.getFromFacebook("appSecret");
	private String appID = stringResourceBundle.getFromFacebook("appID");
	private ButtonMenuImpl splash;
	private Stage myStage;
	private Consumer<FacebookInteractor> mySetFB;

	
	public MenuSplash(Stage stage, Consumer<ButtonMenuImpl> startConsumer, Consumer<FacebookInteractor> setFb) {
		splash = new ButtonMenuImpl(stringResourceBundle.getFromStringConstants("Welcome"));
   	 	splash.addSimpleButtonWithHover(stringResourceBundle.getFromStringConstants("Start"), () -> startConsumer.accept(splash) , stringResourceBundle.getFromMenuText("StartHover"));
   	 	splash.addSimpleButtonWithHover(stringResourceBundle.getFromMenuText("Help"), () -> {
   	 		HelpOptions help = new HelpOptionsImpl();
   	 		help.display(myStage);
   	 	}, stringResourceBundle.getFromMenuText("HelpHover"));
   	 	splash.addSimpleButtonWithHover(stringResourceBundle.getFromMenuText("FBconnect"), () -> launchFb(stage), stringResourceBundle.getFromMenuText("FBconnectHover"));
		myStage = stage;
		mySetFB = setFb;
	}
	
	public void display(){
		splash.display(myStage);
	}

	private void launchFb(Stage stage) {
		Stage loginStage = new Stage();
		loginStage.initOwner(stage);
		loginStage.initModality(Modality.APPLICATION_MODAL);
		FacebookBrowser fbBrowser = new FacebookBrowserImpl(appID);
		FacebookConnector fb = new FacebookConnectorImpl(appID,appSecret);
		ButtonMenuImpl myLoginButton = new ButtonMenuImpl(stringResourceBundle.getFromStringConstants("Login"));
		myLoginButton.addSimpleButtonWithHover(stringResourceBundle.getFromStringConstants("Login"), () -> {
			fbBrowser.launchPage();
			fbBrowser.onDialogClose(e -> {
				String accessToken = fbBrowser.getAccessToken();
				fb.login(accessToken);			
				FacebookInteractor fbInter= fb.getInteractor();
				mySetFB.accept(fbInter);
				loginStage.close();
			});
		}, stringResourceBundle.getFromMenuText("FBconnectHover"));
		myLoginButton.display(loginStage);
	}
}
