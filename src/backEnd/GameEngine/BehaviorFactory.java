package backEnd.GameEngine;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import backEnd.GameData.State.Component;
import backEnd.GameEngine.Behaviors.AttackBehavior;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.GameEngine.Behaviors.DeathBehavior;
import backEnd.GameEngine.Behaviors.MoveBehavior;

public class BehaviorFactory {
//
//	private final static String RESOURCES_PATH = "resources/BehaviorFactory";
//	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private Component myComponent;
	
	public BehaviorFactory(Component component) {
		myComponent = component;
	}
	
//	public Behavior getBehavior(String key){
//		String simpleName = Behavior.class.getSimpleName();
//		String fullName = Behavior.class.getName();
//		String basePath = fullName.substring(0, fullName.length() - simpleName.length());
//		Object object = null;
//		try {
//			object = Class.forName(basePath + myResources.getString(simpleName)).getConstructor(Component.class).newInstance(myComponent);
//		} catch (	InstantiationException 		| IllegalAccessException	| ClassNotFoundException | IllegalArgumentException |
//					InvocationTargetException 	| NoSuchMethodException		| SecurityException e) {
//			e.printStackTrace();
//		}
//		return (Behavior)object;
//	}
	
	/*
	 * Just hardcoding to get it working. We'll test the reflection once we get stuff moving on the screen.
	 */
	public Behavior getBehavior(String key){
		switch (key){
		case "MoveBehavior":
			return new MoveBehavior(myComponent);

		case "DeathBehavior":
			return new DeathBehavior();
		//case "AttackBehavior":
		//	return new AttackBehavior(myComponent);
		default:
			System.out.println("behavior factory tried and failed to generate a behavior");
			return null;
		
		}
	}

}