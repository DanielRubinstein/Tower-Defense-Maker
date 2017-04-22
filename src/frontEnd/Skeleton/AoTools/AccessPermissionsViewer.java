package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.AccessPermissions;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.Constants;

public class AccessPermissionsViewer implements SkeletonObject{
	private Stage myHostStage;
	private View myView;
	private AccessPermissions myAccessPermissions;
	private Stage myStage;
	private Button ignition;
	private VBox myRoot;

	public AccessPermissionsViewer(Stage hostStage, View view, AccessPermissions accessPermissions) {
		myHostStage = hostStage;
		myView = view;
		myAccessPermissions = accessPermissions;
		createIgnition();
	}

	private void createIgnition() {
		ignition = new ActionButton("See Access Permissions", () -> {
			loadViewer();
			generate();
		});
	}

	private void loadViewer() {
		myRoot = new VBox();
		myRoot.getChildren().add(createTitle());
		
		myRoot.getChildren().add(createBody());
		myRoot.setSpacing(30);
	}

	private HBox createBody() {
		HBox body = new HBox();
		List<String> catList = Arrays.asList("User", "Game", "Level");
		for (String title : catList){
			VBox subBody = new VBox();
			Label titleLbl = new Label(title);
			subBody.getChildren().add(titleLbl);
			List<String> opts = null;
			if(title.equals("User")){
				opts = Arrays.asList("AUTHOR", "PLAYER");
			} else if (title.equals("Game")){
				opts = myView.getLevelProgressionController().getGameList();
			} else if (title.equals("Level")){
				//opts = myView.getLevelProgressionController().getLevelList(null);
				opts = Arrays.asList("Level 1", "Level 2", "Level 3");
			}
			
			for(String thing : opts){
				CheckBox cB = new CheckBox(thing);
				if(!myView.getBooleanAuthorModeProperty().get() || thing.equals("AUTHOR")){
					cB.disableProperty().set(true);
				}
				
				if(myAccessPermissions.getUserModeList().contains(thing) || myAccessPermissions.getGameModeList().contains(thing)){
					cB.setSelected(true);
				}
				
				
				
				cB.selectedProperty().addListener((o, oldV, newV) -> {
					
				});
				subBody.getChildren().add(cB);
			}
			
			body.getChildren().add(subBody);
		}
		
		
		return body;
	}

	private Label createTitle() {
		Label titleLbl = new Label("Access Permissions");
		titleLbl.setFont(Font.font(32));
		titleLbl.setUnderline(true);
		return titleLbl;
	}

	private void generate() {
		myStage = new Stage();
		myStage.initOwner(myHostStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(myRoot);
		scene.getStylesheets().add(Constants.DEFAULT_CSS);
		myStage.setScene(scene);
		myStage.show();
	}

	public Node getRoot() {
		return ignition;
	}

}
