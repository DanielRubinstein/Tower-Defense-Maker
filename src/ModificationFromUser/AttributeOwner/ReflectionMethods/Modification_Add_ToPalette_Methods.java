package ModificationFromUser.AttributeOwner.ReflectionMethods;

import backEnd.Bank.BankController;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;

/**
 * This is the reflection class for adding components and tiles to the palette.
 * This class allows Modification_Add_ToPalette to call the appropiate method
 * for either components and tiles
 * 
 * @author Miguel Anderson
 *
 */

public class Modification_Add_ToPalette_Methods {
	private BankController myBankController;
	private String newName;
	public static final String DESCRIPTION = "Add Preset Component or Tile";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_Add_ToPalette_Methods(BankController bankController, String newAttributeOwnerName) {
		this.myBankController = bankController;
		this.newName = newAttributeOwnerName;
	}

	public void add(TileImpl tile) {
		myBankController.addNewTile(newName, tile);
	}

	public void add(Component component) {
		myBankController.addNewComponent(newName, component);
	}

}
