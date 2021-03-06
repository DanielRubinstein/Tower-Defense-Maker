package backEnd.Bank;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import backEnd.Mode.Mode;
import data.DataControllerReader;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import resources.constants.StringResourceBundle;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;

/**
 * 
 * @author Juan Philippe
 *
 */

public class BankControllerImpl implements SerializableObservable, BankController, SerializableObserver
{
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private Map<String, Tile> tileBank;
	private Map<String, Component> componentBank;
	private Map<String, Tile> accessibleTileBank;
	private Map<String, Component> accessibleComponentBank;
	private Mode myMode;
	private DataControllerReader dataController;
	private List<SerializableObserver> observers;

	public BankControllerImpl(Mode myMode, DataControllerReader dataController) {
		this.tileBank = dataController.loadTileMap();
		this.componentBank = dataController.loadComponentMap();
		this.myMode = myMode;
		myMode.addObserver(this);
		this.dataController = dataController;
		this.observers = new ArrayList<SerializableObserver>();
		accessibleComponentBank = new HashMap<>();
		accessibleTileBank = new HashMap<>();
		//createTemplatesForTesting();
	}
	
	@Deprecated
	private void createTemplatesForTesting(){
		try{
			this.tileBank = new HashMap<String, Tile>();
			this.componentBank = new HashMap<String, Component>();
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

			Component testerSpawnedBloon = new ComponentImpl();
			testerSpawnedBloon.setAttributeValue("ImageFile", "resources/images/Components/blue_bloon.png");
			testerSpawnedBloon.setAttributeValue("Speed", 1d);
			testerSpawnedBloon.setAttributeValue("Health", 20);
			testerSpawnedBloon.setAttributeValue("Type", "Enemy");
			

			Component testingBloon = new ComponentImpl();
			testingBloon.setAttributeValue("ImageFile", "resources/images/Components/rainbow_bloon.png");
			testingBloon.setAttributeValue("Speed", 0.1);
			testingBloon.setAttributeValue("Health", 20);
			testingBloon.setAttributeValue("Type", "Enemy");
			testingBloon.setAttributeValue("SpawnOnDeath", true);
			testingBloon.setAttributeValue("SpawnOnDeathObject", testerSpawnedBloon);
			addNewComponent("Enemy", testingBloon);

			Component testingTurret = new ComponentImpl();
			testingTurret.setAttributeValue("ImageFile", "resources/images/Components/zombie.png");
			testingTurret.setAttributeValue("Health", 10);
			testingTurret.setAttributeValue("Type", "Tower");
			testingTurret.setAttributeValue("Velocity", 5.0);
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
	
	@Override
	public void addNewComponent(String name, Component component) {
		if (componentBank.containsKey(name) || tileBank.containsKey(name)) {
			JOptionPane.showMessageDialog(null, strResources.getFromErrorMessages("Duplicate_Name_Error"));
		} else {
			componentBank.put(name, component);
			refreshAccessibleComponentMap();
			notifyObservers();
		}
	}

	@Override
	public void addNewTile(String name, Tile tile) {
		if (componentBank.containsKey(name) || tileBank.containsKey(name)) {
			JOptionPane.showMessageDialog(null, strResources.getFromErrorMessages("Duplicate_Name_Error"));
		} else {
			tileBank.put(name, tile);
			refreshAccessibleTileMap();
			notifyObservers();
		}
	}
	
	@Override
	public void remove(Component component) {
		String removeName = getPresetName(component);
		componentBank.remove(removeName);
		refreshAccessibleComponentMap();
		notifyObservers();
	}
	@Override
	public void remove(Tile tile) {
		String removeName = getPresetName(tile);
		tileBank.remove(removeName);
		refreshAccessibleTileMap();
		notifyObservers();
	}
	
	@Override
	public Map<String, Component> getComponentMap() {
		return componentBank;
	}

	@Override
	public Map<String, Tile> getTileMap() {
		return tileBank;
	}
	
	@Override
	public Map<String, Component> getAccessibleComponentMap() {
		refreshAccessibleComponentMap();
		return accessibleComponentBank;
	}
	
	@Override
	public Map<String, Tile> getAccessibleTileMap() {
		refreshAccessibleTileMap();
		return accessibleTileBank;
	}
	
	@Override
	public void refreshAccessibleMaps(){
		refreshAccessibleComponentMap();
		refreshAccessibleTileMap();
	}

	private void refreshAccessibleTileMap() {
		refreshAccessibleAttributeOwnerMap(tileBank, accessibleTileBank);
	}

	private void refreshAccessibleComponentMap() {
		refreshAccessibleAttributeOwnerMap(componentBank, accessibleComponentBank);
	}
	
	private <T extends AttributeOwner> void refreshAccessibleAttributeOwnerMap(Map<String, T> mainMap, Map<String, T> accessibleMap){
		accessibleMap.clear();
		for(String presetName : mainMap.keySet()){
			checkIfAccessibleAndAdd(presetName, mainMap.get(presetName), accessibleMap);
		}
	}
	
	private <T extends AttributeOwner> void checkIfAccessibleAndAdd(String presetName, T preset, Map<String, T> presetAccessibleMap){
		if(preset.getAccessPermissions().permitsAccess(myMode.getUserMode(), 
														myMode.getGameMode(),
														myMode.getLevelMode())){
			presetAccessibleMap.put(presetName, preset);
		}
	}

	@Override
	public AttributeOwnerReader getPreset(String presetName) {
		if (componentBank.containsKey(presetName)) {
			return componentBank.get(presetName);
		} else if (tileBank.containsKey(presetName)) {
			return tileBank.get(presetName);
		} else {
			new ErrorDialog().create(strResources.getFromErrorMessages("Bank_Error_Header"), 
					String.format(strResources.getFromErrorMessages("Missing_Preset"), presetName));
			return null;
		}
	}
	
	//TODO: get rid of instanceOf
	@Override
	public String getPresetName(AttributeOwnerReader preset) {
		if (preset instanceof Tile) {
			return findKeyFromValue(tileBank, (Tile) preset);
		} else if (preset instanceof Component) {
			return findKeyFromValue(componentBank, (Component) preset);
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

	
	
	@Override
	public Component getComponent(String componentName){
		if (componentBank.containsKey(componentName)){
			return componentBank.get(componentName);
		}
		if (componentName == null){
			return null;
		}
		else{
			new ErrorDialog().create(strResources.getFromErrorMessages("Bank_Error_Header"), 
					String.format(strResources.getFromErrorMessages("Missing_Preset"), componentName));
			return null;
		}
	}
	
	@Override
	public void addObserver(SerializableObserver o){
		observers.add(o);
	}

	private void notifyObservers() {
		for(SerializableObserver o : observers){
			o.update((SerializableObservable) this, null);
		}
		dataController.saveUniversalGameData();
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SerializableObserver> getObservers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Tile> getAccessibleTilePresets() {
		return this.getAccessibleTileMap().values();
	}

	@Override
	public Collection<Component> getAccessibleComponentPresets() {
		return this.getAccessibleComponentMap().values();
	}

	@Override
	public void update(SerializableObservable so, Object obj) {
		refreshAccessibleMaps();
		notifyObservers();
	}
}
