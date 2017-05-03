package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_Add_ToPalette_Methods;
import backEnd.Model;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Attribute.AttributeOwnerSerializer;
import backEnd.Bank.BankController;
import backEnd.Mode.ModeException;
import util.reflection.Reflection;

/**
 * Used when user creates a new preset component
 * 
 * @author Derek, Miguel Anderson
 *
 */
public class Modification_Add_ToPalette implements ModificationFromUser {

	private AttributeOwnerReader newAttrOwnReader;
	private BankController myBankController;
	private String newName;

	public static final String DESCRIPTION = "Add Preset Component or Tile";

	public Modification_Add_ToPalette(String newAttributeOwnerName, AttributeOwnerReader obj) {
		this.newAttrOwnReader = obj;
		this.newName = newAttributeOwnerName;
	}

	@Override
	public void invoke(Model model) throws Exception {
		switch (model.getMode().getUserMode()) {
		case "AUTHOR":
			AttributeOwner toAdd;
			AttributeOwner newAttributeOwner = model.getAttributeOwner(newAttrOwnReader);
			if (stateContains(newAttributeOwner, model)) {
				AttributeOwnerSerializer attributeOwnerSerializer = new AttributeOwnerSerializer();
				toAdd = attributeOwnerSerializer.createCopy(newAttributeOwner);
			} else {
				toAdd = newAttributeOwner;
			}
			myBankController = model.getBankController();
			Modification_Add_ToPalette_Methods methods = new Modification_Add_ToPalette_Methods(myBankController,
					newName);

			Reflection.callMethod(methods, "add", toAdd);
			break;

		case "PLAYER":
			new ModeException(model.getMode(), DESCRIPTION);
		}

	}
	
	private boolean stateContains(AttributeOwner myAttrOwn, Model myModel){
		return myModel.getGameData().getState().getComponentGraph().contains(myAttrOwn)
		|| myModel.getGameData().getState().getTileGrid().contains(myAttrOwn);
	}

}
