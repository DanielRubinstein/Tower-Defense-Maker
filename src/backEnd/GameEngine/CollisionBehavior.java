package backEnd.GameEngine;

public class CollisionBehavior implements Behavior {

	@Override
	public void execute() {
		switch (key) {
		case "aa":
			return new MoveBehavior();
		case "CollisionBehavior":
			return new CollisionBehavior();
		case "DeathBehavior":
			return new DeathBehavior();
		default: throw new IllegalArgumentException(); //FIX THIS	
		}
	}

}
