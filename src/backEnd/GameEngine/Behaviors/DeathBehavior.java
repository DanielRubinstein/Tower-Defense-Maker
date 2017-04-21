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

	@Override
	public <T> void execute(T componentToUse) { //pass in a component
		myComponent=(Component) componentToUse;
		Object spawn=myComponent.getAttribute("SpawnsOnDeath").getValue();
		spawnsOnDeath=(boolean) spawn;
		if (spawnsOnDeath){
			Object spawnedOnDeath=myComponent.getAttribute("SpawnOnDeathObject").getValue();
			componentSpawnedOnDeath=(Component) spawnedOnDeath;
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
		Object currentHealth=myComponent.getAttribute("Health").getValue();
		return ((int) currentHealth)>0;
	}
	
	public Component getNewComponent(){
		return componentSpawnedOnDeath;
	}

}
