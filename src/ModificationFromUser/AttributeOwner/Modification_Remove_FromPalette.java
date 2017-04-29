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
public class Modification_Remove_FromPalette implements ModificationFromUser {

	private AttributeOwner attrOwn;
	private BankController myBankController;

	public static final String DESCRIPTION = "Remove Preset Component or Tile";	
	
	public Modification_Remove_FromPalette(AttributeOwner obj){
		this.attrOwn = obj;
	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		
		switch (model.getMode().getUserMode()) {
		case "AUTHOR":
			myBankController = model.getBankController();
			Reflection.callMethod(myBankController, "remove", attrOwn);
			break;
			
		case "PLAYER":
			throw new ModeException(model.getMode(), DESCRIPTION);
		}
		
	}

	
}
