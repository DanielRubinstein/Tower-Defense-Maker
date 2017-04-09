package backEnd.GameEngine;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import backEnd.GameEngine.Behaviors.Behavior;

public class BehaviorFactory {

	private final static String RESOURCES_PATH = "resources/BehaviorFactory";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private Component myComponent;
	
	public BehaviorFactory(Component component) {
		myComponent = component;
	}
	
	public Behavior getBehavior(String key){
		String simpleName = Behavior.class.getSimpleName();
		String fullName = Behavior.class.getName();
		String basePath = fullName.substring(0, fullName.length() - simpleName.length());
		Object object = null;
		try {
			object = Class.forName(basePath + myResources.getString(simpleName)).getConstructor(Component.class).newInstance(myComponent);
		} catch (	InstantiationException 		| IllegalAccessException	| ClassNotFoundException | IllegalArgumentException |
					InvocationTargetException 	| NoSuchMethodException		| SecurityException e) {
			e.printStackTrace();
		}
		return (Behavior)object;
	}

}