package backEnd.Bank;

import java.util.List;
import java.util.Map;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.GameData.Rules;
import backEnd.GameData.State.Tile;

public class BankController
{
	private Map<String, Tile> tileBank;
	private Map<String, Component> componentBank;
	private BehaviorBank behaviorBank;
	private RuleBank ruleBank;
	private AttributeBank attributeBank;
	

	public BankController(Map<String, Tile> tileBank, Map<String, Component> componentBank)
	{
		this.tileBank = tileBank;
		this.componentBank = componentBank;
		
		behaviorBank = new BehaviorBank();
		ruleBank = new RuleBank();
		attributeBank = new AttributeBank();
	}

	public void addNewTile (String name, Tile tile)
	{
		tileBank.put(name, tile);
	}

	public void removeTile(String name)
	{
		tileBank.remove(name);
	}
	
	public Map<String, Tile> getTileMap()
	{
		return tileBank;
	}
	
	public void addNewComponent (String name, Component component)
	{
		componentBank.put(name, component);
	}

	public void removeComponent(String name)
	{
		componentBank.remove(name);
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
	
	public List<Attribute> getAttributeList()
	{
		return attributeBank.getAttributeList();
	}
}

