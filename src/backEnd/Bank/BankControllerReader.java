package backEnd.Bank;

import java.util.Collection;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.Tile;

public interface BankControllerReader {
	
	void addObserver(SerializableObserver o);
	
	Collection<Tile> getAccessibleTilePresets();
	
	Collection<Component> getAccessibleComponentPresets();
	
	public String getPresetName(AttributeOwnerReader preset);
	
	/**
	 * This one should get changed to ComponentReader
	 * @param componentName
	 * @return
	 */
	Component getComponent(String componentName);

	AttributeOwnerReader getPreset(String presetName);

}