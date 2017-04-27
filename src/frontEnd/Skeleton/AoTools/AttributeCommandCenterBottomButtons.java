package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ModificationFromUser.AttributeOwner.Modification_Add_StraightToGrid;
import ModificationFromUser.AttributeOwner.Modification_Add_ToPalette;
import ModificationFromUser.AttributeOwner.Modification_PurchaseComponent;
import ModificationFromUser.AttributeOwner.Modification_RemoveAttributeOwner;
import ModificationFromUser.AttributeOwner.Modification_UpgradeComponent;
import backEnd.Attribute.AttributeOwner;
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

public class AttributeCommandCenterBottomButtons implements SkeletonObject{
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
		myRoot.setSpacing(10d);
		myRoot.setAlignment(Pos.BOTTOM_RIGHT);
	}
	
	public void addRemoveButton(Component c) {
		if(myView.isComponentOnGrid(c)){
			Button submit = new Button("Remove Now");
			submit.setOnAction(e -> {
				myView.sendUserModification(new Modification_RemoveAttributeOwner(c));
				myHostStage.close();
			});
			myRoot.getChildren().add(submit);
		}
	}
	
	public void addSubmitButton(Component obj) {
		if(myView.getBankController().getAccessibleComponentMap().containsValue(obj)){
			Button submit = new Button("Add Now");
			submit.setOnAction(e -> {
				myView.sendUserModification(new Modification_Add_StraightToGrid(obj));
				myHostStage.close();
			});
			myRoot.getChildren().add(submit);
		}
	}
	
	public void createAccessPermissionButton(AttributeOwner obj) {
		AccessPermissionsViewer accessPermissionsViewer = new AccessPermissionsViewer(myHostStage, myView, obj.getAccessPermissions());
		myRoot.getChildren().add(accessPermissionsViewer.getRoot());
	}
	
	public void createAddToPresetButton(AttributeOwner obj) {
		if(!(myView.getComponentPresets().contains(obj) || myView.getTilePresets().contains(obj))){
			List<String> dialogTitles = Arrays.asList("Preset Creation Utility", "Please Input a Name for your new preset");
			String promptLabel = "New preset name:";
			String promptText = "";
			SingleFieldPrompt myNameDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
			Button preset = new Button("Save a copy to preset palette");
			preset.setOnAction((e) -> {
				myView.sendUserModification(new Modification_Add_ToPalette(myNameDialog.create(), obj));
				myHostStage.close();
			});
			
			myRoot.getChildren().add(preset);
		}
	}
	
	public void createUpgradeButton(AttributeOwner obj){
		if(!myView.getBooleanAuthorModeProperty().get()){
			Button upgrade = new Button("Upgrade Component");
			int cost = (int) obj.getAttribute("UpgradeCost").getValue();
			upgrade.setOnAction((e) -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Buy Confirmation");
				alert.setHeaderText("This Upgrade Costs " + cost + " Dollars");
				alert.setContentText("Are you sure you want to upgrade this component?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK || result.get() == ButtonType.CANCEL) {
					alert.close();
				}
				if (result.get() == ButtonType.OK){
					myView.sendUserModification(new Modification_PurchaseComponent(cost));
					myView.sendUserModification(new Modification_UpgradeComponent((Component) obj));
				}
				myHostStage.close();
			});
			myRoot.getChildren().add(upgrade);
		}
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

}
