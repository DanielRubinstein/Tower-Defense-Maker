package backEnd.GameEngine;
import com.sun.javafx.geom.Point2D;
public class BehaviorFactory {
	public Behavior getBehavior(String key, Component myComponent){ //use Salas' version when merging
		switch (key) {
		case "MoveBehavior":
			return new MoveBehavior();
		case "CollisionBehavior":
			return new CollisionBehavior();
		case "DeathBehavior":
			return new DeathBehavior();
		default: throw new IllegalArgumentException(); //FIX THIS	
		}
	}
}