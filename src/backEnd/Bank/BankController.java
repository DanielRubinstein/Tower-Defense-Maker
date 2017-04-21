package backEnd.Bank;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.Mode.Mode;
import javafx.geometry.Point2D;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.Rules;
import backEnd.GameData.State.AccessPermissionsImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;

public class BankController extends Observable
{
	private Map<String, Tile> tileBank;
	private Map<String, Component> componentBank;
	private Mode myMode;
	
	public BankController(Mode myMode)
	{
		this.tileBank = new HashMap<String, Tile>();
		this.componentBank = new HashMap<String, Component>();
		this.myMode = myMode;
		createTemplatesForTesting();
	}
	
	public BankController(Mode myMode, Map<String, Tile> tileBank, Map<String, Component> componentBank)
	{
		this.tileBank = tileBank;
		this.componentBank = componentBank;
	}
	
	private void createTemplatesForTesting(){
		try{
			
			Tile newTile = new TileImpl(Arrays.asList(), Arrays.asList("AUTHOR"), new Point2D(0,0));
			newTile.setAttributeValue("ImageFile", "resources/images/Tiles/Blue.png");
			newTile.setAttributeValue("MoveDirection","Down");
			addNewTile("Blue Down Tile", newTile);
			
			Tile newTile2 = new TileImpl(Arrays.asList(), Arrays.asList("AUTHOR"), new Point2D(0,0));
			newTile2.setAttributeValue("ImageFile", "resources/images/Tiles/Red.png");
			newTile2.setAttributeValue("MoveDirection","Right");
			addNewTile("Red Right Tile", newTile2);
			
			
			Component newComponent = new Component(new AttributeData(),new AccessPermissionsImpl());
			newComponent.setAttributeValue("ImageFile", "resources/images/Components/rainbow_bloon.png");
			newComponent.setAttributeValue("Speed", 5d);
			addNewComponent("Chill Bloon", newComponent);
		} catch( FileNotFoundException e){
			System.out.println("No image found");
		}
	}

	public void addNewTile (String name, Tile tile)
	{
		System.out.println("added here");
		tileBank.put(name, tile);
		this.setChanged();
		this.notifyObservers();
	}

	public void removeTile(String name)
	{
		this.setChanged();
		this.notifyObservers();
	}
	
	public Map<String, Tile> getTileMap()
	{
		Map<String, Tile> subMap = new HashMap<String,Tile>();
		
		for (String x : tileBank.keySet())
		{
			//if (tileBank.get(x).getAccessPermissions().permitsAccess());
		}
		
		return subMap;
	}
	
	public void addNewComponent (String name, Component component)
	{
		componentBank.put(name, component);
		this.setChanged();
		this.notifyObservers();
	}

	public void removeComponent(String name)
	{
		componentBank.remove(name);
		this.setChanged();
		this.notifyObservers();
	}

	
	public Map<String, Component> getComponentMap()
	{
		return componentBank;
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
}

