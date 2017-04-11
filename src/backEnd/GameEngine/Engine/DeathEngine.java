package backEnd.GameEngine.Engine;


import backEnd.GameData.State.State;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.GameEngine.Behaviors.DeathBehavior;
import backEnd.GameEngine.Behaviors.MoveBehavior;
import javafx.geometry.Point2D;

public class DeathEngine {
	private DeathBehavior DB;
	public void gameLoop(State currentState){
	DB=new DeathBehavior();
	for(Component struct: currentState.getComponentGraph().getAllComponents()){
		DB.execute(struct);
		if (DB.isDead()){
			currentState.getComponentGraph().getAllComponents().remove(struct);
		}
		if (DB.spawnsOnDeath()){
			currentState.getComponentGraph().addComponentToGrid(DB.getNewComponent(), (Point2D) struct.getMyAttributes().get("LOCATION").getValue());
		}
	}
 }
}
