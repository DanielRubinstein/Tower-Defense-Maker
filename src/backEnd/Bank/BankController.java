package backEnd.Bank;

import java.util.HashMap;
import java.util.List;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.Component;
import backEnd.GameData.Rules;
import backEnd.GameData.State.Tile;

public class BankController
{
	private HashMap<String, Tile> tileBank;
	private HashMap<String, Component> componentBank;
	private BehaviorBank behaviorBank;
	private RuleBank ruleBank;
	private AttributeBank attributeBank;
	

	public BankController(HashMap<String, Tile> tileBank, HashMap<String, Component> componentBank)
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
	
	public HashMap<String, Tile> getTileMap()
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
	
	public HashMap<String, Component> getComponentMap()
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

