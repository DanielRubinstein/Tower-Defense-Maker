package backEnd.GameEngine;

import java.util.ResourceBundle;

import com.sun.javafx.geom.Point2D;

public class AttributeFactory {
	
	private final static String RESOURCES_PATH = "resources/AttributeFactory";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	public Attribute<?> getAttribute(String variableName){
		switch (myResources.getString(variableName)) {
			case "Point2D":
				return new Attribute<Point2D>();
			case "String":
				return new Attribute<String>();
			case "Integer":
				Attribute<Integer> myHealthAttribute=new Attribute<Integer>();
				return myHealthAttribute;
			default: throw new IllegalArgumentException();
		}
	}
}
