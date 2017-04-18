package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Menus.ButtonMenuImpl;
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

public class OptionsFacebook implements SkeletonObject {

	private View myView;
	private HBox myRoot;
	private Stage myParentStage;
	private Stage myStage;
	private ButtonMenuImpl myMenu;
	private FacebookConnector myFb;
	
	public OptionsFacebook(View view,Stage parentStage){
		myView = view;
		myRoot = new HBox();
		myParentStage = parentStage;
		setUp();
	}
	
	private void getPicture() {
		myFb = myView.getFb();
		ImageView pp;
		try {
			pp = myFb.getPicture();
			myRoot.getChildren().add(pp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setUp(){
		if(myView.getFb()!=null){
			Button b = new Button("test");
			b.setOnAction(e -> launch());
			myRoot.getChildren().add(b);
			getPicture();
		}
	}
	private void launch(){
		myStage = new Stage();
		myStage.initOwner(myParentStage);
		myStage.initModality(Modality.APPLICATION_MODAL);

		myMenu = new ButtonMenuImpl("Interact With Facebook");
		
		myMenu.addSimpleButtonWithHover("Share",e -> share(myStage), "Click to share");
		
		myMenu.addSimpleButtonWithHover("Message Friend", e -> message(), "Click to Message");
		
		myMenu.display(myStage);
	}
	
	
	
	private void message() {
		
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
			myFb.shareToWall(message);
			shareStage.close();
		});
		share.getChildren().addAll(text,b);
		Scene s = new Scene(share);
		shareStage.setScene(s);
		shareStage.show();
	}

	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return myRoot;
	}

	public void setAlignment(Pos topLeft, Priority always) {
		myRoot.setAlignment(Pos.CENTER_RIGHT);
	}
	public void setSize(double width, double height){
		myRoot.setMaxHeight(height);
		myRoot.setMaxWidth(width);
	}

}
