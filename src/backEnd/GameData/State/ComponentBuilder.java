package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.ResourceBundle;

import backEnd.Attribute.AttributeData;
import backEnd.GameEngine.Behaviors.Behavior;

public class ComponentBuilder {
	private final static String DEFAULT_ATTRIBUTE_PATH = "resources/defaultComponentAttributes";
	private final static String BEHAVIOR_PATH = "resources/behaviorNames";
	private final static ResourceBundle behaviorResources = ResourceBundle.getBundle(BEHAVIOR_PATH);
	private final static ResourceBundle attributeResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTE_PATH);
	private AttributeData myAttributes;
	private AccessPermissions myAccessPermissions;
	
	public ComponentBuilder(AttributeData attributes, AccessPermissions accessPermissions) {
		myAttributes = attributes;
		myAccessPermissions = accessPermissions;
		
	}
	
	public Component getComponent() {
		try {
			return new Component(myAttributes.copy(),myAccessPermissions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
