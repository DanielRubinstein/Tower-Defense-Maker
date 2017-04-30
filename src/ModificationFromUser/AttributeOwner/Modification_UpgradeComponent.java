package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;

public class Modification_UpgradeComponent implements ModificationFromUser {
	private Component toUpgrade;
	
	//FIXME should be reader interface in constructor
	public Modification_UpgradeComponent(Component toUpgrade) {
		this.toUpgrade = toUpgrade;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		String upgradePreset = (String) toUpgrade.getAttribute("Upgrade").getValue();
		Point2D loc = (Point2D) toUpgrade.getAttribute("Position").getValue();
		Component toAdd = myModel.getBankControllerReader().getComponent(upgradePreset);
		Modification_RemoveAttributeOwner removeMod = new Modification_RemoveAttributeOwner(toUpgrade);
		removeMod.invoke(myModel);
		Modification_Add_PaletteToGrid addMod = new Modification_Add_PaletteToGrid(toAdd, loc);
		addMod.invoke(myModel);
		
	}

}
