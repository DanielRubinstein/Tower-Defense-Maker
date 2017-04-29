package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.AttributeOwner.ReflectionMethods.Modification_Add_ToPalette_Methods;
import backEnd.BankController;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerSerializer;
import backEnd.Mode.ModeException;
import util.reflection.Reflection;

/**
 * Used when user creates a new preset component
 * 
 * @author Derek
 *
 */
public class Modification_Add_ToPalette implements ModificationFromUser {

	private AttributeOwner newAttrOwn;
	private BankController myBankController;
	private String newName;

	public static final String DESCRIPTION = "Add Preset Component or Tile";

	public Modification_Add_ToPalette(String newAttributeOwnerName, AttributeOwner obj) {
		this.newAttrOwn = obj;
		this.newName = newAttributeOwnerName;
	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		switch (model.getMode().getUserMode()) {
		case "AUTHOR":
			AttributeOwner newAttrOwnToAdd;
			if (model.getGameData().getState().getComponentGraph().contains(newAttrOwn)
					|| model.getGameData().getState().getTileGrid().contains(newAttrOwn)) {
				AttributeOwnerSerializer attributeOwnerSerializer = new AttributeOwnerSerializer();
				newAttrOwnToAdd = attributeOwnerSerializer.createCopy(newAttrOwn);
			} else {
				newAttrOwnToAdd = newAttrOwn;
			}
			myBankController = model.getBankController();
			Modification_Add_ToPalette_Methods methods = new Modification_Add_ToPalette_Methods(myBankController,
					newName);

			Reflection.callMethod(methods, "add", newAttrOwnToAdd);
			break;

		case "PLAYER":
			new ModeException(model.getMode(), DESCRIPTION);
		}

	}

}
