package frontEnd.Skeleton.UserTools;

import com.restfb.exception.FacebookException;

import frontEnd.ViewReader;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import frontEnd.Facebook.FacebookInteractor;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public class OptionsFacebook implements SkeletonObject {

	private ViewReader myView;
	private HBox myRoot;
	private Stage myStage;
	private ButtonMenuImpl myMenu;
	private FacebookInteractor myFb;
	private ImageView profPic;
	private StringResourceBundle strResources = new StringResourceBundle();
	
	public OptionsFacebook(ViewReader view){
		myView = view;
		myRoot = new HBox();
		setUp();
	}
	
	private void getPicture() {
		myFb = myView.getFb();
		try {
			profPic = myFb.getPicture();
			profPic.setOnMouseClicked(e -> launch());
			myRoot.getChildren().add(profPic);
		} catch (FacebookException e) {
			ErrorDialog eD = new ErrorDialog();
			eD.create(strResources.getFromErrorMessages("Facebook_Error"), e.getMessage());
		}
	}

	private void setUp(){
		if(myView.getFb()!=null){
			getPicture();
			ActionButton b = new ActionButton(strResources.getFromStringConstants("FacebookOptions"), () -> launch());
			myRoot.getChildren().add(b);
		}
	}
	
	private void launch(){
		myStage = new Stage();
		myStage.initOwner(myView.getMainWindow());
		myStage.initModality(Modality.APPLICATION_MODAL);
		myMenu = new ButtonMenuImpl(strResources.getFromStringConstants("InteractFacebook"));
		
		myMenu.addSimpleButtonWithHover(strResources.getFromStringConstants("SimpleFacebookShare"),
				() -> share(myStage), strResources.getFromStringConstants("LongFacebookShare"));
		myMenu.display(myStage);
	}

	private void share(Stage parStage) {
		Stage shareStage = new Stage();
		shareStage.initOwner(parStage);
		shareStage.initModality(Modality.APPLICATION_MODAL);
		VBox share = new VBox();
		TextField text = new TextField();
		Button b = new Button("Share");
		b.setOnAction(e -> {
			String message = text.getText().trim();
			try {
				myFb.shareToWall(message);
			} catch (FacebookException e1) {
				ErrorDialog eD = new ErrorDialog();
				eD.create(strResources.getFromErrorMessages("Facebook_Error"), e1.getMessage());
			}
			shareStage.close();
		});
		share.getChildren().addAll(text,b);
		Scene s = new Scene(share);
		shareStage.setScene(s);
		shareStage.show();
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

	public void setAlignment(Pos alignment, Priority always) {
		myRoot.setAlignment(alignment);
	}
	public void setSize(double width, double height){
		myRoot.setMaxHeight(height);
		myRoot.setMaxWidth(width);
	}

}
