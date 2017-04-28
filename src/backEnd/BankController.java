package backEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import backEnd.Mode.Mode;
<<<<<<< HEAD
import backEnd.Attribute.AttributeData;
=======
import data.DataControllerReader;
import resources.constants.StringResourceBundle;
>>>>>>> 24bb9c3fa1bf8e7df3482775321bbe43dd9b7326
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.Tile;

/**
 * 
 * @author Juan Philippe
 *
 */

public class BankController implements BankControllerReader
{
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private Map<String, Tile> tileBank;
	private Map<String, ComponentImpl> componentBank;
	private Map<String, Tile> accessibleTileBank;
	private Map<String, ComponentImpl> accessibleComponentBank;
	private Mode myMode;
	private List<SerializableObserver> observers;
	
	public BankController(Mode myMode)
	{
		this(myMode, new HashMap<String, Tile>(), new HashMap<String, Component>());
	}

	public BankController(Mode myMode, Map<String, Tile> tileBank, Map<String, Component> componentBank) {
		this.tileBank = tileBank;
		this.componentBank = componentBank;
		this.myMode = myMode;
		this.observers = new ArrayList<SerializableObserver>();
		accessibleComponentBank = new HashMap<>();
		accessibleTileBank = new HashMap<>();
		createTemplatesForTesting();
	}
	
	@Override
	public String getComponentName(ComponentImpl component){
		return findKeyFromValue(componentBank, component);
	}

	public void addNewTile(String name, Tile tile) {
		if (tileBank.containsKey(name)) {
			JOptionPane.showMessageDialog(null, strResources.getFromErrorMessages("Duplicate_Name_Error"));
		} else {
			tileBank.put(name, tile);
			refreshAccessibleTileMap();
			notifyObservers();
		}
	}

	public void removeTile(String name) {
		tileBank.remove(name);
		refreshAccessibleTileMap();
		notifyObservers();
	}

	public Map<String, Tile> getAccessibleTileMap() {
		refreshAccessibleTileMap();
		return accessibleTileBank;
	}

	private void refreshAccessibleTileMap() {
		accessibleTileBank.clear();

		for (String x : tileBank.keySet()) {
			if (tileBank.get(x).getAccessPermissions().permitsAccess(myMode.getUserMode(), myMode.getGameMode(),
					myMode.getLevelMode())) {
				accessibleTileBank.put(x, tileBank.get(x));
			}
		}
	}

	public Map<String, ComponentImpl> getAccessibleComponentMap() {
		refreshAccessibleComponentMap();
		return accessibleComponentBank;
	}

	private void refreshAccessibleComponentMap() {
		accessibleComponentBank.clear();

		for (String x : componentBank.keySet()) {
			if (componentBank.get(x).getAccessPermissions().permitsAccess(myMode.getUserMode(), myMode.getGameMode(),
					myMode.getLevelMode())) {
				accessibleComponentBank.put(x, componentBank.get(x));
			}
		}
	}

	public Map<String, ComponentImpl> getComponentMap() {
		return componentBank;
	}

	public Map<String, Tile> getTileMap() {
		return tileBank;
	}

	public void addNewComponent(String name, ComponentImpl component) {
		if (tileBank.containsKey(name)) {
			JOptionPane.showMessageDialog(null, strResources.getFromErrorMessages("Duplicate_Name_Error"));
		} else {
			componentBank.put(name, component);
			refreshAccessibleComponentMap();
			notifyObservers();
		}
	}

	public void removeComponent(String name) {
		componentBank.remove(name);
		refreshAccessibleComponentMap();
		notifyObservers();
	}

	public String getAOName(AttributeOwner preset) {
		if (preset instanceof Tile) {
			return findKeyFromValue(tileBank, (Tile) preset);
		} else if (preset instanceof ComponentImpl) {
			return findKeyFromValue(componentBank, (ComponentImpl) preset);
		}
		return "";
	}

	private <V> String findKeyFromValue(Map<String, V> bank, V preset) {
		for (Map.Entry<String, V> entry : bank.entrySet()) {
			if (entry.getValue().equals(preset)) {
				return entry.getKey();
			}
		}
		return strResources.getFromErrorMessages("No_Name_Found");
	}

	public AttributeOwner getPreset(String presetName) {
		if (componentBank.containsKey(presetName)) {
			return componentBank.get(presetName);
		} else if (tileBank.containsKey(presetName)) {
			return tileBank.get(presetName);
		} else {
			return null;
		}
	}
	
	public ComponentImpl getComponent(String componentName){
		if (componentBank.containsKey(componentName)){
			return componentBank.get(componentName);
		}
		if (componentName == null){
			return null;
		}
		else{
			throw new RuntimeException(strResources.getFromErrorMessages("Component_Not_Found"));
		}
	}
	
	public void addObserver(SerializableObserver o){
		observers.add(o);
	}

	private void notifyObservers() {
		for(SerializableObserver o : observers){
			o.update(null, null);
		}
	}
}
