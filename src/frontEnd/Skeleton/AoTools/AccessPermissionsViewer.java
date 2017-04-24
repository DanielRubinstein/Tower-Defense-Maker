package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ModificationFromUser.AttributeOwner.Modification_EditAccessPermissions;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.AccessPermissions;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.Constants;

public class AccessPermissionsViewer implements SkeletonObject {
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
		// TODO NEED TO OBSERVE backend to populate level options as new games are selected!!!
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
		for (String title : catList) {
			VBox subBody = new VBox();
			Label titleLbl = new Label(title);
			subBody.getChildren().add(titleLbl);
			List<String> opts = null;

			// TODO fix this shit
			if (title.equals("User")) {
				myView.getLevelProgressionController().getMode();
				opts = myView.getLevelProgressionController().getMode().getAllUserModes();
			} else if (title.equals("Game")) {
				opts = myView.getLevelProgressionController().getGameList();
			} else if (title.equals("Level")) {
				/*
				opts = myView.getLevelProgressionController().getLevelList(null);
				opts = myView.getLevelProgressionController().getFullLevelList();
				*/
				subBody.getChildren()
						.add(createGameLevelDropDown(subBody, myView.getLevelProgressionController().getFullLevelList()));
			}

			addCheckBoxes(subBody, opts);

			body.getChildren().add(subBody);
		}

		return body;
	}

	private void addCheckBoxes(VBox subBody, List<String> opts) {
		if(opts == null) return;
		for (String option : opts) {
			CheckBox cB = new CheckBox(option);
			if (!myView.getBooleanAuthorModeProperty().get() || option.equals("AUTHOR")) {
				cB.disableProperty().set(true);
			}

			if (myAccessPermissions.permitsAccess(option)) {
				cB.setSelected(true);
			}
			cB.selectedProperty().addListener((o, oldV, newV) -> {
				myView.sendUserModification(new Modification_EditAccessPermissions(myAccessPermissions, newV, option));
			});

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
		Scene scene = new Scene(myRoot, 500, 300);
		scene.getStylesheets().add(Constants.DEFAULT_CSS);
		myStage.setScene(scene);
		myStage.show();
	}

	public Node getRoot() {
		return ignition;
	}

}
