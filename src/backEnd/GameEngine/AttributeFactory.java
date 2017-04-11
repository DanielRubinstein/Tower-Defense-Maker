package backEnd.GameEngine;

import java.util.ResourceBundle;

import com.sun.javafx.geom.Point2D;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.MoveDirections;

public class AttributeFactory {
	
	private final static String RESOURCES_PATH = "resources/AttributeFactory";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	public Attribute<?> getAttribute(String variableName){
		switch (myResources.getString(variableName)) {
			case "Point2D":
				return new AttributeImpl<Point2D>();
			case "String":
				return new AttributeImpl<String>();
			case "Integer":
				return new AttributeImpl<Integer>();
			case "Boolean":
				return new AttributeImpl<Boolean>();
			case "MoveDirections":
				return new AttributeImpl<MoveDirections>();
			default: throw new IllegalArgumentException();
		}
	}
}
