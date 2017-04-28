package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.ComponentImpl;
import javafx.geometry.Point2D;

public class Modification_UpgradeComponent implements ModificationFromUser {
	private ComponentImpl toUpgrade;
	
	public Modification_UpgradeComponent(ComponentImpl toUpgrade) {
		this.toUpgrade = toUpgrade;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		String upgradePreset = (String) toUpgrade.getAttribute("Upgrade").getValue();
		Point2D loc = (Point2D) toUpgrade.getAttribute("Position").getValue();
		ComponentImpl toAdd = myModel.getBankController().getComponent(upgradePreset);
		Modification_Add_PaletteToGrid addMod = new Modification_Add_PaletteToGrid(toAdd, loc);
		addMod.invoke(myModel);
		Modification_RemoveAttributeOwner removeMod = new Modification_RemoveAttributeOwner(toUpgrade);
		removeMod.invoke(myModel);
	}

}
