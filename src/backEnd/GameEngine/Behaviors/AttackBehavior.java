package backEnd.GameEngine.Behaviors;

import java.util.Observable;

import backEnd.Attribute.AttributeData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;

/**
 * 
 * @author Christian Martindale
 * Governs the exact actions projectiles take after reaching their target
 * 2 classes of projectile have different methods of affecting State (single target and AOE)
 */
public class AttackBehavior implements Behavior{

	public AttributeData myAttributes;
	private Component myComponent;
	private Point2D currentPosition;
	private State myState;

	public AttackBehavior(Component inputComponent, State currentState){
		myComponent=inputComponent;
		myState = currentState;
		currentPosition = (Point2D) myComponent.getAttribute("Position").getValue();
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void execute(T toModify) {
		// TODO Auto-generated method stub
		
	}

}
