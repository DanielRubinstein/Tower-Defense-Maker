package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;

import backEnd.Attribute.AttributeOwner;
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
	private AttributeOwner myObj;
	private Stage myStage;
	private Button ignition;
	private VBox myRoot;

	public AccessPermissionsViewer(Stage hostStage, View view, AttributeOwner obj) {
		myHostStage = hostStage;
		myView = view;
		myObj = obj;
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
			
			List<String> catListOpts = Arrays.asList("Bloons", "AUTHOR", "Level 3", "etc..", "More to come");
			
			for(String thing : catListOpts){
				CheckBox cB = new CheckBox(thing);
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
