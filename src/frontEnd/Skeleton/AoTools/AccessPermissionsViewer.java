package frontEnd.Skeleton.AoTools;

import java.util.List;

import ModificationFromUser.AttributeOwner.Modification_EditAccessPermissions;
import backEnd.GameData.State.AccessPermissionsReader;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.CustomJavafxNodes.RestrictedPermissions;
import frontEnd.Skeleton.SkeletonObject;
import javafx.beans.property.BooleanProperty;
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
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

public class AccessPermissionsViewer implements SkeletonObject, RestrictedPermissions {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	
	private Stage myHostStage;
	private View myView;
	private AccessPermissionsReader myAccessPermissions;
	private Stage myStage;
	private Button ignition;
	private VBox myRoot;
	private LevelProgressionControllerReader myModeController;

	public AccessPermissionsViewer(Stage hostStage, View view, AccessPermissionsReader accessPermissionsReader) {
		myHostStage = hostStage;
		myView = view;
		myModeController = myView.getLevelProgressionController();
		myAccessPermissions = accessPermissionsReader;
		myRoot = new VBox();
		createIgnition();
	}

	private void createIgnition() {
		ignition = new ActionButton(stringResourceBundle.getFromStringConstants("AccessPermissions"), () -> {
			loadViewer();
			generate();
		});
	}

	private void loadViewer() {
		myRoot.getChildren().add(createTitle());
		myRoot.getChildren().add(createBody());
		myRoot.setSpacing(numericResourceBundle.getFromSizing("StandardSpacing"));
		myRoot.setPadding(new Insets(numericResourceBundle.getFromSizing("StandardSpacing")));
	}

	private HBox createBody() {
		HBox body = new HBox();
		List<String> modeCategories = myModeController.getModeCategories();
		for (String category : modeCategories) {
			VBox subBody = new VBox();
			Label titleLbl = new Label(category);
			titleLbl.setFont(Font.font(numericResourceBundle.getFromSizing("TitleFontSize")));
			titleLbl.setUnderline(true);
			subBody.getChildren().add(titleLbl);
			
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
			scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scrollPane.setMaxHeight(numericResourceBundle.getFromSizing("MaxMenuContentHeight"));
			
			VBox subsubBody = new VBox();
			
			
			if(category.equals("Level")){
				for(String game : myModeController.getGameList()){
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
			if (!myView.getBooleanAuthorModeProperty().get() || option.equals(stringResourceBundle.getFromStringConstants("AUTHOR")) || option.equals(stringResourceBundle.getFromStringConstants("DEFAULT"))) {
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
	
	private void indentInVBox(Node n){
		// Add offset to left side to indent from title
		// from http://docs.oracle.com/javafx/2/layout/LayoutSampleCSS.java.html
		VBox.setMargin(n, new Insets(0, 0, 0, numericResourceBundle.getFromSizing("StandardSpacing")));
	}

	private Label createTitle() {
		Label titleLbl = new Label(stringResourceBundle.getFromStringConstants("AccessPermissions"));
		titleLbl.setFont(Font.font(numericResourceBundle.getFromSizing("TitleFontSize")));
		titleLbl.setUnderline(true);
		return titleLbl;
	}

	private void generate() {
		myStage = new Stage();
		myStage.initOwner(myHostStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(myRoot);
		scene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
		myStage.setScene(scene);
		myStage.show();
	}

	public Node getRoot() {
		return ignition;
	}

	@Override
	public BooleanProperty disabledProperty() {
		return myRoot.disableProperty();
	}

}
