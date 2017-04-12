package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.Mode.ModeException;

/**
 * Used when user creates a new preset component
 * 
 * @author Derek
 *
 */
public class Modification_AddNewPresetAttributeOwner implements ModificationFromUser {

	private AttributeOwner newAO;
	private String newAOName;
	public static final String DESCRIPTION = "Add Preset Component or Tile";		
	
	public Modification_AddNewPresetAttributeOwner(String newAttributeOwnerName, AttributeOwner newAO){
		this.newAO = newAO;
		this.newAOName = newAttributeOwnerName;
	}

	//FIXME currently the new preset will overwrite an existing preset with the same name, 
	// based on the implementation of addNewComponent()
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			if(newAO instanceof Tile){
				myModel.getBankController().addNewTile(newAOName, (Tile) newAO);
			}
			else if(newAO instanceof Component){
				myModel.getBankController().addNewComponent(newAOName, (Component) newAO);
			}
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}
		
	}

	
}
