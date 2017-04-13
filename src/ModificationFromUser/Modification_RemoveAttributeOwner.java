package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

public class Modification_RemoveAttributeOwner implements ModificationFromUser {
	
	private AttributeOwner toRemove;
	public static final String ERROR_DESCRIPTION = "Cannot remove tiles";

	public Modification_RemoveAttributeOwner(AttributeOwner toRemove){
		this.toRemove = toRemove;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		if (toRemove instanceof Tile){
			throw new Exception(ERROR_DESCRIPTION);
		}
		else if (toRemove instanceof Component){
			myModel.getState().getComponentGraph().removeComponent((Component)toRemove);

		}
		
	}

}
