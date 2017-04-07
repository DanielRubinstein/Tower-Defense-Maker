import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.MoveBehavior;
import backEnd.GameEngine.Engine.Engine.EngineFactory;

public class Main {
	public static void main(String[] args){
		Behavior kappa = new MoveBehavior(0, 0);
		EngineFactory factory = new EngineFactory();
		factory.getEngine(kappa);
	}
}
