package frontEnd.Skeleton.AoTools;

import java.util.List;
import java.util.ResourceBundle;

import ModificationFromUser.AttributeOwner.Modification_EditAccessPermissions;
import backEnd.GameData.State.AccessPermissions;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.Constants;

public class AccessPermissionsViewer implements SkeletonObject {
	private static final String BUNDLE_NAME = "resources.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private Stage myHostStage;
	private View myView;
	private AccessPermissions myAccessPermissions;
	private Stage myStage;
	private Button ignition;
	private VBox myRoot;
	private LevelProgressionControllerReader myModeController;

	public AccessPermissionsViewer(Stage hostStage, View view, AccessPermissions accessPermissions) {
		myHostStage = hostStage;
		myView = view;
		myModeController = myView.getLevelProgressionController();
		myAccessPermissions = accessPermissions;
		// TODO NEED TO OBSERVE backend to populate level options as new games are selected!!!
		createIgnition();
	}

	private void createIgnition() {
		ignition = new ActionButton(RESOURCE_BUNDLE.getString("AccessPermissions"), () -> {
			loadViewer();
			generate();
		});
	}

	private void loadViewer() {
		myRoot = new VBox();
		myRoot.getChildren().add(createTitle());
		myRoot.getChildren().add(createBody());
		myRoot.setSpacing(20);
		myRoot.setPadding(new Insets(10,10,10,10));
	}

	private HBox createBody() {
		HBox body = new HBox();
		List<String> modeCategories = myModeController.getModeCategories();
		for (String category : modeCategories) {
			VBox subBody = new VBox();
			Label titleLbl = new Label(category);
			titleLbl.setFont(Font.font(24));
			titleLbl.setUnderline(true);
			subBody.getChildren().add(titleLbl);
			
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
			scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scrollPane.setMaxHeight(300);
			
			VBox subsubBody = new VBox();
			
			
			if(category.equals("Level")){
				for(String game : myModeController.getOptions("Game")){
					VBox subsubsubBody = new VBox();
					Label gameLbl = new Label(game);
					subsubsubBody.getChildren().add(gameLbl);
					addCheckBoxes(subsubsubBody, myModeController.getLevelList(game));
					indentInVBox(subsubsubBody);
					subsubBody.getChildren().add(subsubsubBody);
				}
			} else {
				List<String> opts = myModeController.getOptions(category);
				addCheckBoxes(subsubBody, opts);
			}
			
			scrollPane.setContent(subsubBody);
			subBody.getChildren().add(scrollPane);
			body.getChildren().add(subBody);
			
		}

		return body;
	}

	private void addCheckBoxes(VBox subBody, List<String> opts) {
		if(opts == null) return;
		for (String option : opts) {
			CheckBox cB = new CheckBox(option);
			if (!myView.getBooleanAuthorModeProperty().get() || option.equals("AUTHOR") || option.equals("DEFAULT")) {
				cB.disableProperty().set(true);
			}

			if (myAccessPermissions.permitsAccess(option)) {
				cB.setSelected(true);
			}
			cB.selectedProperty().addListener((o, oldV, newV) -> {
				myView.sendUserModification(new Modification_EditAccessPermissions(myAccessPermissions, newV, option));
			});
			indentInVBox(cB);
			subBody.getChildren().add(cB);
		}
	}

	private Node createGameLevelDropDown(VBox parent, List<String> list) {
		
		ObservableList<String> options = (ObservableList<String>) FXCollections.observableArrayList(list);
		ComboBox<String> optionsBox = new ComboBox<String>(options);
		try {
			// TODO this will work as long as there is an attribute there
			optionsBox.getSelectionModel().select(null);
		} catch (NullPointerException e) {
			// do nothing
		}
		optionsBox.valueProperty().addListener((o, oldValue, newValue) -> {
			parent.getChildren().clear();
			parent.getChildren().add(optionsBox);
			addCheckBoxes(parent, myView.getLevelProgressionController().getLevelList(newValue));
			//optionsBox.requestFocus();
			
		});
		return optionsBox;
	}
	
	private void indentInVBox(Node n){
		// Add offset to left side to indent from title
		// from http://docs.oracle.com/javafx/2/layout/LayoutSampleCSS.java.html
		VBox.setMargin(n, new Insets(0, 0, 0, 12));
	}

	private Label createTitle() {
		Label titleLbl = new Label(RESOURCE_BUNDLE.getString("AccessPermissions"));
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
