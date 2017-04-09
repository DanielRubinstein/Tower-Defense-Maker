package backEnd.GameEngine.Behaviors;

import java.util.Map;
import java.util.Observable;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.AttributeData;
import javafx.geometry.Point2D;
import resources.Constants;
import backEnd.GameEngine.myAttributes;

/**
 * This class allows us to keep each Component's location attribute up to date as we move the components
 * around via the MoveEngine.
 * @author Daniel
 *
 */
public class MoveBehavior implements Behavior {
	private AttributeData myAttributes; //we're not using this now, but we will once we figure out how frontend passes attributes
	private Attribute<Point2D> currentAttribute;
	private Point2D currentPoint;
	private Point2D newPoint;
	
	public MoveBehavior(Map<String, Attribute<?>> myAttributes){	
	}
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Map<String, Attribute<?>> myAttributes) {
		myAttributes MA=new myAttributes(); //use this until we figure out how we get attributes from frontend
		currentAttribute=(Attribute<Point2D>) MA.getAttribute("LOCATION");
		currentPoint=(Point2D) currentAttribute.getValue();
		switch (MA.getAttribute("LOCATION").getValue().toString()) {
		case "LEFT":

			newPoint=new Point2D(currentPoint.getX()-Constants.moveAmount, currentPoint.getY());
			currentAttribute.setValue(newPoint);
			MA.addAttribute("LOCATION", currentAttribute);
		case "RIGHT":
			newPoint=new Point2D(currentPoint.getX()+Constants.moveAmount, currentPoint.getY());
			currentAttribute.setValue(newPoint);
			MA.addAttribute("LOCATION", currentAttribute);
		case "UP":
			newPoint=new Point2D(currentPoint.getX(), currentPoint.getY()+Constants.moveAmount);
			currentAttribute.setValue(newPoint);
			MA.addAttribute("LOCATION", currentAttribute);
		case "DOWN":

			newPoint=new Point2D(currentPoint.getX(), currentPoint.getY()-Constants.moveAmount);
			currentAttribute.setValue(newPoint);
			MA.addAttribute("LOCATION", currentAttribute);
			
		default: throw new IllegalArgumentException(); //TODO: figure out how we are doing error handling
		}
	}

	
	@Override
	public void update(Observable newData, Object arg) {
		myAttributes = (AttributeData) newData;
	}

	
}