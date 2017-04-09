package backEnd.GameEngine.Behaviors;

import java.util.Map;
import java.util.Observable;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.AttributeData;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.myAttributes;
import javafx.geometry.Point2D;

public class DeathBehavior implements Behavior {
	//use the following 2 when we remove dummy attributes
	private Component myComponent;	//
	private myAttributes MA; //we won't use this later
	private boolean spawnsOnDeath;
	private Component componentSpawnedOnDeath;
	private AttributeData myAttributes;

	@Override
	public <T> void execute(T componentToUse) { //pass in a component
		myComponent=(Component) componentToUse;
		spawnsOnDeath=(boolean) myComponent.getAttribute("SPAWNS_ON_DEATH").getValue();
		if (spawnsOnDeath){
			componentSpawnedOnDeath=(Component) myComponent.getAttribute("COMPONENT_SPAWNED_ON_DEATH").getValue();
		}

	}
	
	@Override
	public void update(Observable newData, Object arg) { //TODO figure out how we are using AttributeData with
		//this. Then we can figure out how observables work.
		myAttributes = (AttributeData) newData;
	}


	public boolean spawnsOnDeath(){
		return spawnsOnDeath;
	}
	
	public boolean isDead(){
		int currentHealth=(int) myComponent.getAttribute("HEALTH").getValue();
		return currentHealth>0;
	}
	
	public Component getNewComponent(){
		return componentSpawnedOnDeath;
	}

}