package ModificationFromUser.AttributeOwner;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Observer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ModificationFromUser.ModificationFromUser;
import backEnd.BankController;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerSerializer;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.ModeException;

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
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";
	
	public Modification_Add_ToPalette(String newAttributeOwnerName, AttributeOwner obj){
		this.newAttrOwn = obj;
		this.newName = newAttributeOwnerName;
	}

	//FIXME currently the new preset will overwrite an existing preset with the same name, 
	// based on the implementation of addNewComponent()
	@Override
	public void invoke(ModelImpl model) throws Exception {
		
		switch (model.getMode().getUserMode()) {
		case "AUTHOR":
			AttributeOwner newAttrOwnToAdd;
			if(model.getGameData().getState().getComponentGraph().contains(newAttrOwn) || model.getGameData().getState().getTileGrid().contains(newAttrOwn)){
				AttributeOwnerSerializer attributeOwnerSerializer = new AttributeOwnerSerializer();
				newAttrOwnToAdd = attributeOwnerSerializer.createCopy(newAttrOwn);
			} else {
				newAttrOwnToAdd = newAttrOwn;
			}
			myBankController = model.getBankController();
			try {
				Method add = Modification_Add_ToPalette.class.getDeclaredMethod("add", newAttrOwn.getClass());
				add.setAccessible(true);
				add.invoke(this, newAttrOwnToAdd);
			} catch (NoSuchMethodException e) {
				throw new Exception(DESCRIPTION_ERROR);
			}
			break;
			
		case "PLAYER":
			throw new ModeException(model.getMode(), DESCRIPTION);
		}
		
	}
	
	private void add(TileImpl tile){
		myBankController.addNewTile(newName, tile);
	}
	
	private void add(Component component){
		myBankController.addNewComponent(newName, component);
	}
	
}
