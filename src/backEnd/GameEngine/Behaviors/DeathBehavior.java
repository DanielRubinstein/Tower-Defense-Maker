package backEnd.GameEngine.Behaviors;

import java.util.Map;
import java.util.Observable;

import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;

public class DeathBehavior implements Behavior {
	private Component myComponent;	//
	
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
