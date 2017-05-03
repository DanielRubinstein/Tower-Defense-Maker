package ModificationFromUser.AttributeOwner;

import java.util.Optional;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameData.State.Component;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import resources.constants.StringResourceBundle;

public class Modification_PurchaseComponent implements ModificationFromUser{
	private Component newComponent;
	private Point2D newPosition;
	private Integer myCost;
	private static final StringResourceBundle strResources = new StringResourceBundle();

	public Modification_PurchaseComponent(Component presetAO, Point2D pos) {
		this.newComponent = presetAO;
		this.newPosition = pos;
		myCost = newComponent.<Integer>getAttribute(strResources.getFromAttributeNames("Price")).getValue();
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		switch(myModel.getMode().getUserMode()){
		case "PLAYER":
			Double money = myModel.getPlayerStatusReader().getProperty("Money").getValue();
			if (money < myCost){
				new ErrorDialog().create("Not Enough Money", "You don't have enough money to purchase this component");
				return;
			}	
			if(checkWithUser()){
				myModel.getModifiablePlayerStatus().decrementStatusItem("Money", myCost);
			} else {
				return;
			}
			break;
		}
		
		Modification_Add_PaletteToGrid addMod = new Modification_Add_PaletteToGrid(newComponent, newPosition);
		addMod.invoke(myModel);
	}
	
	private boolean checkWithUser() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(strResources.getFromStringConstants("StoreBuyConfirmation"));
		String priceFormat = strResources.getFromStringConstants("StorePurchaseFormat");
		alert.setHeaderText(String.format(priceFormat, myCost));
		alert.setContentText(strResources.getFromStringConstants("StoreQuestion"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK || result.get() == ButtonType.CANCEL) {
			alert.close();
		}
		return result.get() == ButtonType.OK;
	}

}
