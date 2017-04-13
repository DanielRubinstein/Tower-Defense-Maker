package backEnd.Bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.Rules;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

public class BankController extends Observable
{
	private Map<String, Tile> tileBank;
	private Map<String, Component> componentBank;
	private BehaviorBank behaviorBank;
	private RuleBank ruleBank;
	private AttributeBank attributeBank;
	
	public BankController(){
		this.tileBank = new HashMap<String, Tile>();
		this.componentBank = new HashMap<String, Component>();
		init();
	}
	
	public BankController(Map<String, Tile> tileBank, Map<String, Component> componentBank)
	{
		this.tileBank = tileBank;
		this.componentBank = componentBank;
		init();
	}
	
	private void init(){
		behaviorBank = new BehaviorBank();
		ruleBank = new RuleBank();
		attributeBank = new AttributeBank();
	}

	public void addNewTile (String name, Tile tile)
	{
		tileBank.put(name, tile);
		this.setChanged();
		this.notifyObservers();
	}

	public void removeTile(String name)
	{
		tileBank.remove(name);
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
	
	public List<Rules> getRuleList()
	{
		return ruleBank.getRuleList();
	}
	
	public List<AttributeImpl> getAttributeList()
	{
		return attributeBank.getAttributeList();
	}
}

