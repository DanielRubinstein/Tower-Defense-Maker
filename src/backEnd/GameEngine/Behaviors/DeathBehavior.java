package backEnd.GameEngine.Behaviors;

import java.util.Map;
import java.util.Observable;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.AttributeData;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.myAttributes;
import javafx.geometry.Point2D;

public class DeathBehavior implements Behavior {
	private AttributeData myAttributes;
	private myAttributes MA; //we won't use this later
	private boolean spawnsOnDeath;
	private Component componentSpawnedOnDeath;
	public DeathBehavior(){
		MA=new myAttributes(); //use this until we figure out how we get attributes from frontend
		spawnsOnDeath=(boolean) MA.getAttribute("SPAWNS_ON_DEATH").getValue();
		if (spawnsOnDeath){
			componentSpawnedOnDeath=(Component) MA.getAttribute("COMPONENT_SPAWNED_ON_DEATH").getValue();
		}
	}

	
	@Override
	public void update(Observable newData, Object arg) {
		myAttributes = (AttributeData) newData;
	}

	@Override
	public void execute(Map<String, Attribute<?>> myAttributes) {
		isDead();
	}
	
	public boolean isDead(){
		int currentHealth=(int) MA.getAttribute("HEALTH").getValue();
		return currentHealth>0;
	}
	
	public Component getNewComponent(){
		return componentSpawnedOnDeath;
	}

}
