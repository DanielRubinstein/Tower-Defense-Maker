package backEnd;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import backEnd.Mode.Mode;
import data.DataController;
import data.DataControllerReader;
import resources.constants.StringResourceBundle;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.AccessPermissions;
import backEnd.GameData.State.AccessPermissionsImpl;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;

/**
 * 
 * @author Juan Philippe
 *
 */

public class BankController implements BankControllerReader
{
	private static final String DUPLICATE_NAME_ERROR = "Cannot Add Duplicate Name";
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private Map<String, Tile> tileBank;
	private Map<String, ComponentImpl> componentBank;
	private Map<String, Tile> accessibleTileBank;
	private Map<String, ComponentImpl> accessibleComponentBank;
	private Mode myMode;
	private List<SerializableObserver> observers;
	private DataControllerReader dataController;
	
	public BankController(Mode myMode, DataControllerReader dataController) {
		this.tileBank = dataController.loadTileMap();
		this.componentBank = dataController.loadComponentMap();
		this.myMode = myMode;
		this.dataController = dataController;
		this.observers = new ArrayList<SerializableObserver>();
		accessibleComponentBank = new HashMap<>();
		accessibleTileBank = new HashMap<>();
		//createTemplatesForTesting();
	}
	
	@Override
	public String getComponentName(ComponentImpl component){
		return findKeyFromValue(componentBank, component);
	}
	
	private void createTemplatesForTesting(){
		try{
			this.tileBank = new HashMap<String, Tile>();
			this.componentBank = new HashMap<String, ComponentImpl>();
			Tile newTile = new TileImpl();
			newTile.setAttributeValue("ImageFile", "resources/images/Tiles/Blue.png");
			newTile.setAttributeValue("MoveDirection", "Down");
			addNewTile("Blue Down Tile", newTile);

			Tile newTile2 = new TileImpl();
			newTile2.setAttributeValue("ImageFile", "resources/images/Tiles/Red.png");
			newTile2.setAttributeValue("MoveDirection", "Right");
			addNewTile("Red Right Tile", newTile2);

			Tile newTile3 = new TileImpl();
			newTile3.setAttributeValue("ImageFile", "resources/images/Tiles/Green.png");
			newTile3.setAttributeValue("MoveDirection", "Up");
			addNewTile("Green Up Tile", newTile3);

			Tile newTile4 = new TileImpl();
			newTile4.setAttributeValue("ImageFile", "resources/images/Tiles/Yellow.png");
			newTile4.setAttributeValue("MoveDirection", "Left");
			addNewTile("Yellow Left Tile", newTile4);

			ComponentImpl testerSpawnedBloon = new ComponentImpl();
			testerSpawnedBloon.setAttributeValue("ImageFile", "resources/images/Components/blue_bloon.png");
			testerSpawnedBloon.setAttributeValue("Speed", 1d);
			testerSpawnedBloon.setAttributeValue("Health", 20);
			testerSpawnedBloon.setAttributeValue("Type", "Enemy");
			

			ComponentImpl testingBloon = new ComponentImpl();
			testingBloon.setAttributeValue("ImageFile", "resources/images/Components/rainbow_bloon.png");
			testingBloon.setAttributeValue("Speed", 1d);
			testingBloon.setAttributeValue("Health", 20);
			testingBloon.setAttributeValue("Type", "Enemy");
			testingBloon.setAttributeValue("SpawnOnDeath", true);
			testingBloon.setAttributeValue("SpawnOnDeathObject", testerSpawnedBloon);
			addNewComponent("Enemy", testingBloon);

			ComponentImpl testingTurret = new ComponentImpl();
			testingTurret.setAttributeValue("ImageFile", "resources/images/Components/zombie.png");
			testingTurret.setAttributeValue("Health", 10);
			testingTurret.setAttributeValue("Type", "Tower");
			testingTurret.setAttributeValue("Velocity", 2.0);
			testingTurret.setAttributeValue("Speed", 1.0);
			testingTurret.setAttributeValue("FireDamage", 10);
			testingTurret.setAttributeValue("FireRate", 1000.0);
			testingTurret.setAttributeValue("ExplosionRadius", 40.0);
			testingTurret.setAttributeValue("FireType", "SingleTarget");
			testingTurret.setAttributeValue("FireRadius", 200.0);
			testingTurret.setAttributeValue("FireImage", "resources/images/Components/purple_bloon.png");
			addNewComponent("Tower", testingTurret);

		} catch (FileNotFoundException e) {
			System.out.println("No image found");
		}
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
		return componentBank.get(componentName);
	}
	
	public void addObserver(SerializableObserver o){
		observers.add(o);
	}

	private void notifyObservers() {
		for(SerializableObserver o : observers){
			o.update(null, null);
		}
		saveXML();
	}

	private void saveXML() {
		dataController.saveUniversalGameData();
	}
}
