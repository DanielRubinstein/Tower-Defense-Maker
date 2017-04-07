package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleToLongFunction;

import com.sun.javafx.geom.Point2D;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import backEnd.State.State;

public class CollisionEngine implements Engine{

	@Override
	public void gameLoop(State currentState) {
		currentState.getComponentGraph();
		List<Component> myList = new ArrayList<Component>();
		for(Component component1: myList){
			for(Component component2: myList){
				if(collideObjects(component1, component2)){
					//TODO: Do collide
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private boolean collideObjects(Component component1, Component component2) {
		String attributeCoordinate = ""; //myResources.getShit();
		String attributeDimesion   = "";
		double component1x = ((Attribute<Point2D>)component1.getAttribute(attributeCoordinate)).getValue().x;
		double component1y = ((Attribute<Point2D>)component1.getAttribute(attributeCoordinate)).getValue().y;
		double component1w = ((Attribute<Point2D>)component1.getAttribute(attributeDimesion)).getValue().x;
		double component1h = ((Attribute<Point2D>)component1.getAttribute(attributeDimesion)).getValue().y;
		
		double component2x = ((Attribute<Point2D>)component2.getAttribute(attributeCoordinate)).getValue().x;
		double component2y = ((Attribute<Point2D>)component2.getAttribute(attributeCoordinate)).getValue().y;
		double component2w = ((Attribute<Point2D>)component2.getAttribute(attributeDimesion)).getValue().x;
		double component2h = ((Attribute<Point2D>)component2.getAttribute(attributeDimesion)).getValue().y;

		double c1Left   = component1x - component1w / 2;
		double c1Right  = component1x + component1w / 2;
		double c1Top    = component1y - component1h / 2;
		double c1Bottom = component1y + component1h / 2;
		
		double c2Left   = component2x + component2w / 2;
		double c2Right  = component2x + component2w / 2;
		double c2Top    = component2y + component2h / 2;
		double c2Bottom = component2y + component2h / 2;
		
		boolean xOverlap = c2Left < c1Left && c1Left < c2Right || c2Left < c1Right && c1Right < c2Right || 
				           c1Left < c2Left && c2Left < c1Right || c1Left < c2Right && c2Right < c1Right;
		boolean yOverlap = c2Top < c1Top && c1Top < c2Bottom || c2Top < c1Bottom && c1Bottom < c2Bottom || 
						   c1Top < c2Top && c2Top < c1Bottom || c1Top < c2Bottom && c2Bottom < c1Bottom;
		
		return xOverlap && yOverlap;
	}
	
}
