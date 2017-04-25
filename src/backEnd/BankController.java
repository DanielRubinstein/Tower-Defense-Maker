package backEnd;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import javax.swing.JOptionPane;
import backEnd.Mode.Mode;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.AccessPermissions;
import backEnd.GameData.State.AccessPermissionsImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;

/**
 * 
 * @author Juan Philippe
 *
 */
public class BankController
{
	private static final String DUPLICATE_NAME_ERROR = "Cannot Add Duplicate Name";
	private Map<String, Tile> tileBank;
	private Map<String, Component> componentBank;
	private Mode myMode;
	private List<Observer> observers;
	
	public BankController(Mode myMode)
	{
		this(myMode, new HashMap<String, Tile>(), new HashMap<String, Component>());
	}
	
	public BankController(Mode myMode, Map<String, Tile> tileBank, Map<String, Component> componentBank)
	{
		this.tileBank = tileBank;
		this.componentBank = componentBank;
		this.myMode = myMode;
		this.observers = new ArrayList<Observer>();
		createTemplatesForTesting();
	}
	
	private void createTemplatesForTesting(){
		try{
			this.tileBank = new HashMap<String, Tile>();
			this.componentBank = new HashMap<String, Component>();
			Tile newTile = new TileImpl();
			newTile.setAttributeValue("ImageFile", "resources/images/Tiles/Blue.png");
			newTile.setAttributeValue("MoveDirection","Down");
			addNewTile("Blue Down Tile", newTile);
			
			Tile newTile2 = new TileImpl();
			newTile2.setAttributeValue("ImageFile", "resources/images/Tiles/Red.png");
			newTile2.setAttributeValue("MoveDirection","Right");
			addNewTile("Red Right Tile", newTile2);
			
			Tile newTile3 = new TileImpl();
			newTile3.setAttributeValue("ImageFile", "resources/images/Tiles/Green.png");
			newTile3.setAttributeValue("MoveDirection","Up");	
			addNewTile("Green Up Tile", newTile3);
			
			Tile newTile4 = new TileImpl();
			newTile4.setAttributeValue("ImageFile", "resources/images/Tiles/Yellow.png");
			newTile4.setAttributeValue("MoveDirection","Left");
			addNewTile("Yellow Left Tile", newTile4);
			
			
			Component testingBloon = new Component();
			testingBloon.setAttributeValue("ImageFile", "resources/images/Components/rainbow_bloon.png");
			testingBloon.setAttributeValue("Speed", 1d);
			testingBloon.setAttributeValue("Health", 20);
			testingBloon.setAttributeValue("Type", "Enemy");
			addNewComponent("Enemy", testingBloon);
			
			Component testingTurret = new Component();
			testingTurret.setAttributeValue("ImageFile", "resources/images/Components/zombie.png");
			testingTurret.setAttributeValue("Health", 10);
			testingTurret.setAttributeValue("Type", "Tower");
			testingTurret.setAttributeValue("Velocity", 1.0);
			testingTurret.setAttributeValue("FireDamage", 10);
			testingTurret.setAttributeValue("FireRate", 1000.0);
			testingTurret.setAttributeValue("ExplosionRadius", 40.0);
			testingTurret.setAttributeValue("FireRadius", 200.0);
			testingTurret.setAttributeValue("FireImage", "resources/images/Components/purple_bloon.png");
			addNewComponent("Tower", testingTurret);
			
			
		} catch( FileNotFoundException e){
			System.out.println("No image found");
		}
	}

	public void addNewTile (String name, Tile tile)
	{
		if (tileBank.containsKey(name)){
			JOptionPane.showMessageDialog(null, DUPLICATE_NAME_ERROR);
		}
		else{
			tileBank.put(name, tile);
			notifyObservers();
		}
	}

	public void removeTile(String name)
	{
		notifyObservers();
	}
	
	public Map<String, Tile> getAccessibleTileMap()
	{
		Map<String, Tile> subMap = new HashMap<String,Tile>();
		
		for (String x : tileBank.keySet())
		{
			if (tileBank.get(x).getAccessPermissions().permitsAccess(myMode.getUserMode(), myMode.getGameMode(), myMode.getLevelMode()))
			{
				subMap.put(x, tileBank.get(x));
			}
		}
		
		return subMap;
	}
	
	public Map<String, Component> getAccessibleComponentMap()
	{
		Map<String, Component> subMap = new HashMap<String,Component>();
		
		for (String x : componentBank.keySet())
		{
			if (componentBank.get(x).getAccessPermissions().permitsAccess(myMode.getUserMode(), myMode.getGameMode(), myMode.getLevelMode()))
			{
				subMap.put(x, componentBank.get(x));
			}
		}
		
		return subMap;
	}
	public Map<String, Component> getComponentMap()
	{
		return componentBank;
	}
	
	public Map<String, Tile> getTileMap()
	{
		return tileBank;
	}
	
	public void addNewComponent (String name, Component component)
	{
		if (tileBank.containsKey(name)){
			JOptionPane.showMessageDialog(null, DUPLICATE_NAME_ERROR);
		}
		else{
			componentBank.put(name, component);
			notifyObservers();
		}
	}

	public void removeComponent(String name)
	{
		componentBank.remove(name);
		notifyObservers();
	}

	public String getAOName(AttributeOwner preset) {
		if(preset instanceof Tile){
			return findKeyFromValue(tileBank, (Tile) preset);
		} else if (preset instanceof Component){
			return findKeyFromValue(componentBank, (Component) preset);
		}
		return "";
	}

	private <V> String findKeyFromValue(Map<String, V> bank, V preset) {
		for(Map.Entry<String, V> entry : bank.entrySet()){
			if (entry.getValue().equals(preset)){
				return entry.getKey();
			}
		}
		return "No name found";
	}

	public AttributeOwner getPreset(String presetName) {
		if(componentBank.containsKey(presetName)){
			return componentBank.get(presetName);
		} else if (tileBank.containsKey(presetName)){
			return tileBank.get(presetName);
		} else {
			return null;
		}
	}
	
	public Component getComponent(String componentName){
		return componentBank.get(componentName)
;	}
	
	public void addObserver(Observer o){
		observers.add(o);
	}
	
	private void notifyObservers() {
		for(Observer o : observers){
			o.update(null, null);
		}
	}
}
