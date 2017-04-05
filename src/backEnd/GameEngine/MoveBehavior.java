package backEnd.GameEngine;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

/**
 * 
 * @author alex salas
 *
 */
public class MoveBehavior implements Behavior{
	
	public MoveBehavior(){		
	}
	
	@Override
	public void execute() {
		Attribute<Point2D> coord = (Attribute<Point2D>)myAttributes.get("Point");
		coord.setValue(new Point2D(0,0));
		myAttributes.put("Point",coord);
		
		
	}

}
