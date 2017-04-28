package backEnd.GameEngine.Behaviors;


import java.util.Map;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.ComponentImpl;
import javafx.geometry.Point2D;

public class DeathBehavior implements Behavior {
	private ComponentImpl myComponent;	//
	
	private boolean spawnsOnDeath;
	private ComponentImpl componentSpawnedOnDeath;

	@Override
	public <T> void execute(T componentToUse) { //pass in a component
		
		myComponent=(ComponentImpl) componentToUse;
		Object spawn=myComponent.getAttribute("SpawnOnDeath").getValue();
		spawnsOnDeath= (boolean) spawn;
		if (spawnsOnDeath){
			Object spawnedOnDeath=myComponent.getAttribute("SpawnOnDeathObject").getValue();
			componentSpawnedOnDeath=(ComponentImpl) spawnedOnDeath;
		}

	}
	


	public boolean spawnsOnDeath(){
		return spawnsOnDeath;
	}
	
	public boolean isDead(){
		Object currentHealth=myComponent.getAttribute("Health").getValue();
		//System.out.println("Current HP is " + myComponent.getAttribute("Health").getValue());
		return ((int) currentHealth)<=0;
	}
	
	public ComponentImpl getNewComponent(){
		return componentSpawnedOnDeath;
	}

}
