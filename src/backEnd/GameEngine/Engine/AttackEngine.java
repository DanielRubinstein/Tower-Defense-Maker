package backEnd.GameEngine.Engine;

import java.util.List;
import java.util.ResourceBundle;

import backEnd.Attribute.Attribute;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameEngine.AttributeData;
import backEnd.GameEngine.AttributeFactory;
import backEnd.GameEngine.Component;
import javafx.geometry.Point2D;
import resources.Constants;
/**
 * 
 * @author Daniel
 * @author Alex
 *Attack components (targets) from other components (towers).
 */

public class AttackEngine implements Engine{
	private final static String RESOURCES_PATH = "resources/attributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);

	@Override
	public void gameLoop(State currentState) {
		ComponentGraph myComponentGraph=currentState.getComponentGraph();
		//List<Component> componentGraph = currentState.getComponentGraph().getComponentList();
		for(Component componentAttacker : myComponentGraph.getComponentList()){
			if(componentAttacker.getAttribute(myResources.getString("TYPE")).getValue().equals("TOWERTYPE")){ //Fix with resource
				for(Component componentTarget : myComponentGraph.getComponentsWithinRadius(componentAttacker, Constants.defaultRadius)){
					//change radius if needed in constants file
					//if(componentAttacker.getAttribute(myResources.getString("TYPE")).getValue().equals("TOWERTYPE")){ 
						//Fix with resource
						//why are we checking this twice?
					addProjectile(currentState, componentAttacker, componentTarget);
						componentAttacker.getBehavior("Attack").execute(null);
						componentTarget.getBehavior("Gitfked").execute(null); //Attacks everything and is wrong
					}
				}
			}
		}
	
	@SuppressWarnings("unchecked")
	public void addProjectile(State currentState, Component attacker, Component target){ //add the projectile that the attacker fired
		AttributeFactory af = new AttributeFactory();
		Component bullet=new Component();
		AttributeData ad=new AttributeData();
		//I'll figure out a cleaner way of doing this later
		Attribute<Boolean> cur=(Attribute<Boolean>) af.getAttribute(myResources.getString("Movable"));
		cur.setValue(true);
		ad.addAttribute(myResources.getString("Movable"), (backEnd.Attribute.AttributeImpl<?>) cur);
		
		Attribute<String> cur2=(Attribute<String>) af.getAttribute(myResources.getString("ImageFile"));
		cur2.setValue(Constants.BULLET_IMAGE_FILE);
		ad.addAttribute(myResources.getString("ImageFile"), (backEnd.Attribute.AttributeImpl<?>) cur2);
		
		currentState.getComponentGraph().addComponentToGrid(bullet, (Point2D) attacker.getAttribute("Position").getValue());
	}
	
}
