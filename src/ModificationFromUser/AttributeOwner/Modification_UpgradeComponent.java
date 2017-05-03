package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameData.State.Component;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;

public class Modification_UpgradeComponent implements ModificationFromUser{
	private Integer myCost;
	private Component myComponentToUpgrade;
	
	public Modification_UpgradeComponent(Integer cost, Component component){
		this.myCost = cost;
		this.myComponentToUpgrade = component;
		
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		Double money = myModel.getPlayerStatusReader().getProperty("Money").getValue();
		if (money < myCost){
			new ErrorDialog().create("Not Enough Money", "You don't have enough money to purchase this component");
			return;
		}	
		
		Point2D position = myComponentToUpgrade.<Point2D>getAttribute("Position").getValue();
		
		String presetNameOfUpgrade = myComponentToUpgrade.<String>getAttribute("Upgrade").getValue();
		Component toAdd = myModel.getBankControllerReader().getComponent(presetNameOfUpgrade);
		
		Modification_RemoveAttributeOwner removeMod = new Modification_RemoveAttributeOwner(myComponentToUpgrade);
		removeMod.invoke(myModel);
		
		Modification_Add_PaletteToGrid addMod = new Modification_Add_PaletteToGrid(toAdd, position);
		addMod.invoke(myModel);
		
		myModel.getGameData().getStatus().decrementStatusItem("Money", myCost);
	}

}
