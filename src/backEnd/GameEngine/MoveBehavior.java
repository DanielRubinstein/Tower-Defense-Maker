package backEnd.GameEngine;

import java.util.Map;

import javafx.geometry.Point2D;

/**
 * 
 * @author GameEngine Team
 *
 */
public class MoveBehavior implements Behavior{
	
	public MoveBehavior(Map<String, Attribute<?>> myAttributes){	
	}
	
	@Override
	public void execute(Map<String, Attribute<?>> myAttributes) {
		//TODO ERROR HANDLING
		Attribute<Point2D> coord = (Attribute<Point2D>)myAttributes.get("Point");
		coord.setValue(new Point2D(0,0));
		myAttributes.put("Point",coord);
	}
}
