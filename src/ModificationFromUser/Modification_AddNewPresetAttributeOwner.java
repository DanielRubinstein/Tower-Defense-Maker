package ModificationFromUser;

import java.util.List;
import java.util.Observer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
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

	private AttributeOwner newAttrOwn;
	private String newAOName;
	private XStream xStream;
	public static final String DESCRIPTION = "Add Preset Component or Tile";		
	
	public Modification_AddNewPresetAttributeOwner(String newAttributeOwnerName, AttributeOwner obj){
		this.newAttrOwn = obj;
		this.newAOName = newAttributeOwnerName;
	}

	//FIXME currently the new preset will overwrite an existing preset with the same name, 
	// based on the implementation of addNewComponent()
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			AttributeOwner newAttrOwnToAdd;
			if(myModel.getGameData().getState().getComponentGraph().contains(newAttrOwn) || myModel.getGameData().getState().getTileGrid().contains(newAttrOwn)){
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
			
			if(newAttrOwnToAdd instanceof Tile){
				myModel.getBankController().addNewTile(newAOName, (Tile) newAttrOwnToAdd);
			}
			else if(newAttrOwnToAdd instanceof Component){
				System.out.println("yeah its a comp");
				myModel.getBankController().addNewComponent(newAOName, (Component) newAttrOwnToAdd);
			}
			break;
		case PLAYER:
			throw new ModeException(myModel.getMode(), DESCRIPTION);
		}
		
	}

	
}
