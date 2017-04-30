package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankController;
import backEnd.Mode.ModeException;
import util.reflection.Reflection;

/**
 * Used when user creates a new preset component
 * 
 * @author Derek
 *
 */
public class Modification_Remove_FromPalette implements ModificationFromUser {

	private AttributeOwnerReader attrOwnReader;
	private BankController myBankController;

	public static final String DESCRIPTION = "Remove Preset Component or Tile";	
	
	public Modification_Remove_FromPalette(AttributeOwnerReader obj){
		this.attrOwnReader = obj;
	}

	@Override
	public void invoke(Model model) throws Exception {
		
		switch (model.getMode().getUserMode()) {
		case "AUTHOR":
			myBankController = model.getBankController();
			AttributeOwner attrOwn = model.getAttributeOwner(attrOwnReader);
			Reflection.callMethod(myBankController, "remove", attrOwn);
			break;
			
		case "PLAYER":
			throw new ModeException(model.getMode(), DESCRIPTION);
		}
		
	}

	
}
