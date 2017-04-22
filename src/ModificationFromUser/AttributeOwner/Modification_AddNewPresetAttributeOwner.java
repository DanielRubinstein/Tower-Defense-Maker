package ModificationFromUser.AttributeOwner;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Observer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankController;
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
public class Modification_AddNewPresetAttributeOwner implements ModificationFromUser {

	private AttributeOwner newAttrOwn;
	private String newAOName;
	private BankController myBankController;
	private XStream xStream;
	public static final String DESCRIPTION = "Add Preset Component or Tile";		
	
	public Modification_AddNewPresetAttributeOwner(String newAttributeOwnerName, AttributeOwner obj){
		this.newAttrOwn = obj;
		this.newAOName = newAttributeOwnerName;
	}

	//FIXME currently the new preset will overwrite an existing preset with the same name, 
	// based on the implementation of addNewComponent()
	@Override
	public void invoke(ModelImpl model) throws Exception {
		myBankController = model.getBankController();
		switch (model.getMode().getUserMode()) {
		case "AUTHOR":
			AttributeOwner newAttrOwnToAdd;
			if(model.getGameData().getState().getComponentGraph().contains(newAttrOwn) || model.getGameData().getState().getTileGrid().contains(newAttrOwn)){
				xStream = new XStream(new DomDriver());
				xStream.alias("Component", Component.class);
				xStream.alias("Tile", Tile.class);
				List<Observer> oldObservers = newAttrOwn.getAndClearObservers();
				String serializedAO = xStream.toXML(newAttrOwn);
				newAttrOwn.setObserverList(oldObservers);
				newAttrOwnToAdd = (AttributeOwner) xStream.fromXML(serializedAO);
			} else {
				newAttrOwnToAdd = newAttrOwn;
			}
			
			try {
				Method add = Modification_AddNewPresetAttributeOwner.class.getDeclaredMethod("add", newAttrOwn.getClass());
				add.setAccessible(true);
				add.invoke(this, newAttrOwnToAdd);
			} catch (NoSuchMethodException e) {
				System.out.println("in Modification_AddNewPresetAttributeOwner, No method found, ugh");
				// do nothing
				// this means the thing being put in attribute command center is a tile
			} catch (Exception e) {
				// something went wrong
				System.out.println("Something went wrong in Modification_AddNewPresetAttributeOwner");
				// TODO add exception?
			}
			break;
		case "PLAYER":
			throw new ModeException(model.getMode(), DESCRIPTION);
		}
		
	}
	
	private void add(TileImpl tile){
		myBankController.addNewTile(newAOName, tile);
	}
	
	private void add(Component component){
		myBankController.addNewComponent(newAOName, component);
	}
	
}
