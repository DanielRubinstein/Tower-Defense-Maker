package data.GamePrep.Menus;

import java.util.ResourceBundle;
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

public class MenuSplash {
	private static final String DEFAULT_RESOURCE_BUNDLE = "resources/facebook";
	private ResourceBundle appInfo = ResourceBundle.getBundle(DEFAULT_RESOURCE_BUNDLE);
	private String appSecret = appInfo.getString("appSecret");
	private String appID = appInfo.getString("appID");
	private ButtonMenuImpl splash;
	private Stage myStage;
	private Consumer<FacebookInteractor> mySetFB;

	
	public MenuSplash(Stage stage, Consumer<ButtonMenuImpl> startConsumer, Consumer<FacebookInteractor> setFb) {
		splash = new ButtonMenuImpl("Welcome");
   	 	splash.addSimpleButtonWithHover("START", () -> startConsumer.accept(splash) , "Click to start the game!");
   	 	splash.addSimpleButtonWithHover("Help/Instructions", () -> {
   	 		HelpOptions help = new HelpOptionsImpl();
   	 		help.displayOnStage(null);
   	 	}, "See the help page");
   	 	splash.addSimpleButtonWithHover("Connect To Facebook", () -> launchFb(stage), "Log in and connect to Facebook to see high scores, screenshots, post to the official voogasalad_su3ps1ckt34m1337 page");
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
		ButtonMenuImpl myLoginButton = new ButtonMenuImpl("Login!");
		myLoginButton.addSimpleButtonWithHover("Login", () -> {
			fbBrowser.launchPage();
			fbBrowser.onDialogClose(e -> {
				String accessToken = fbBrowser.getAccessToken();
				fb.login(accessToken);			
				FacebookInteractor fbInter= fb.getInteractor();
				mySetFB.accept(fbInter);
				loginStage.close();
			});
		}, "Click to launch facebook");
		myLoginButton.display(loginStage);
	}
}
