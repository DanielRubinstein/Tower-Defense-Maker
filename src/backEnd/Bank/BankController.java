package backEnd.Bank;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import backEnd.GameEngine.Behaviors.Behavior;
import javafx.geometry.Point2D;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.AccessPermissionsImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;

public class BankController extends Observable
{
	private Map<String, Tile> tileBank;
	private Map<String, Component> componentBank;
	private BehaviorBank behaviorBank;
	private RuleBank ruleBank;
	private AttributeBank attributeBank;
	
	public BankController(){
		init();
		this.tileBank = new HashMap<String, Tile>();
		this.componentBank = new HashMap<String, Component>();
		createTemplatesForTesting();
	}
	
	public BankController(Map<String, Tile> tileBank, Map<String, Component> componentBank){
		init();
		this.tileBank = tileBank;
		this.componentBank = componentBank;
		
	}
	
	private void init(){
		behaviorBank = new BehaviorBank();
		ruleBank = new RuleBank();
		attributeBank = new AttributeBank();
	}
	
	private void createTemplatesForTesting(){
		try{
			
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
			newTile3.setAttributeValue("MoveDirection","Down");
			addNewTile("Green Up Tile", newTile3);
			
			Tile newTile4 = new TileImpl();
			newTile4.setAttributeValue("ImageFile", "resources/images/Tiles/Yellow.png");
			newTile4.setAttributeValue("MoveDirection","Left");
			addNewTile("Yellow Left Tile", newTile4);
			
			
			Component newComponent = new Component(new AttributeData(),new AccessPermissionsImpl());
			newComponent.setAttributeValue("ImageFile", "resources/images/Components/rainbow_bloon.png");
			newComponent.setAttributeValue("Speed", 5d);
			newComponent.setAttributeValue("Health", 10);
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
		return tileBank;
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
	
	public List<Behavior> getBehaviorList()
	{
		return behaviorBank.getBehaviorList();
	}
	
	public List<Rule> getRuleList()
	{
		return ruleBank.getRuleList();
	}
	
	public List<AttributeImpl> getAttributeList()
	{
		return attributeBank.getAttributeList();
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

