package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.Modification_Add_StraightToGrid;
import ModificationFromUser.AttributeOwner.Modification_Add_ToPalette;
import ModificationFromUser.AttributeOwner.Modification_RemoveAttributeOwner;
import ModificationFromUser.AttributeOwner.Modification_UpgradeComponent;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;
import util.reflection.Reflection;

public class AttributeCommandCenterBottomButtons implements SkeletonObject{
	private static final NumericResourceBundle NUMERIC_RESOURCE_BUNDLE = new NumericResourceBundle();
	private static final StringResourceBundle STRING_RESOURCE_BUNDLE = new StringResourceBundle();
	private HBox myRoot;
	private View myView;
	private Stage myHostStage;
	
	public AttributeCommandCenterBottomButtons(View view, Stage hostStage){
		myView = view;
		myHostStage = hostStage;
		initializeRoot(); 
	}

	private void initializeRoot() {
		myRoot = new HBox();
		myRoot.setSpacing(NUMERIC_RESOURCE_BUNDLE.getFromSizing("StandardSpacing"));
		myRoot.setAlignment(Pos.BOTTOM_RIGHT);
	}
	
	public void addRemoveButton(Component component) {
		if(myView.isComponentOnGrid(component)){
			Button submit = new Button(STRING_RESOURCE_BUNDLE.getFromStringConstants("Remove"));
			submit.setOnAction(e -> {
				myView.sendUserModification(new Modification_RemoveAttributeOwner(component));
				myHostStage.close();
			});
			myRoot.getChildren().add(submit);
		}
	}
	
	public void addSubmitButton(Component component) {
		// as is this option is not available when creating a preset
		if(myView.getBankControllerReader().getAccessibleComponentPresets().contains(component)){
			Button submit = new Button(STRING_RESOURCE_BUNDLE.getFromStringConstants("AddNow"));
			submit.setOnAction(e -> {
				myView.sendUserModification(new Modification_Add_StraightToGrid(component));
				myHostStage.close();
			});
			myRoot.getChildren().add(submit);
		}
	}
	
	public void createAccessPermissionButton(AttributeOwnerReader attributeOwnerReader) {
		AccessPermissionsViewer accessPermissionsViewer = new AccessPermissionsViewer(myHostStage, myView, attributeOwnerReader.getAccessPermissionsReader());
		myRoot.getChildren().add(accessPermissionsViewer.getRoot());
	}
	
	public void createAddToPresetButton(AttributeOwnerReader obj) {
		if(!(myView.getBankControllerReader().getAccessibleComponentPresets().contains(obj) 
				|| myView.getBankControllerReader().getAccessibleTilePresets().contains(obj))){
			List<String> dialogTitles = Arrays.asList(STRING_RESOURCE_BUNDLE.getFromStringConstants("PresetMain1"), STRING_RESOURCE_BUNDLE.getFromStringConstants("PresetMain2"));
			String promptLabel = STRING_RESOURCE_BUNDLE.getFromStringConstants("PresetSub");
			String promptText = "";
			SingleFieldPrompt myNameDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
			Button preset = new Button(STRING_RESOURCE_BUNDLE.getFromStringConstants("PresetSaveDesc"));
			preset.setOnAction((e) -> {
				if(!(obj.<String>getAttributeReader(STRING_RESOURCE_BUNDLE.getFromAttributeNames("ImageFile")).getValue().equals(""))){
					myView.sendUserModification(new Modification_Add_ToPalette(myNameDialog.getUserInputString(), obj));
					myHostStage.close();
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle(STRING_RESOURCE_BUNDLE.getFromStringConstants("PresetSavingError"));
					alert.setHeaderText(STRING_RESOURCE_BUNDLE.getFromStringConstants("PresetSavingErrorDesc"));
					alert.setContentText(STRING_RESOURCE_BUNDLE.getFromStringConstants("PresetPickImage"));
					alert.showAndWait();
					e.consume();
				}
			});
			
			myRoot.getChildren().add(preset);
		}
	}
	
	public void createUpgradeButton(AttributeOwnerReader attributeOwnerReader){
		if(!myView.getBooleanAuthorModeProperty().get()){
			Button upgrade = new Button(STRING_RESOURCE_BUNDLE.getFromStringConstants("UpgradeComponentTitle"));
			Integer cost = attributeOwnerReader.<Integer>getAttributeReader(STRING_RESOURCE_BUNDLE.getFromAttributeNames("UpgradeCost")).getValue();
			upgrade.setOnAction((e) -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle(STRING_RESOURCE_BUNDLE.getFromStringConstants("StoreBuyConfirmation"));
				String costFormat = STRING_RESOURCE_BUNDLE.getFromStringConstants("UpgradePurchaseFormat");
				alert.setHeaderText(String.format(costFormat, cost.toString()));
				alert.setContentText(STRING_RESOURCE_BUNDLE.getFromStringConstants("UpgradeQuestion"));
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK || result.get() == ButtonType.CANCEL) {
					alert.close();
				}
				if (result.get() == ButtonType.OK){
					findUpgradeModification(cost, attributeOwnerReader);
				}
				myHostStage.close();
			});
			myRoot.getChildren().add(upgrade);
		}
	}

	private void findUpgradeModification(Integer cost, AttributeOwnerReader attributeOwnerReader) {
		myView.sendUserModification((ModificationFromUser) Reflection.createInstance(Modification_UpgradeComponent.class.getName(), cost, attributeOwnerReader));
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

}
