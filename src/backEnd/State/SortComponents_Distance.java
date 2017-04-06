package backEnd.State;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sun.javafx.geom.Point2D;

public class SortComponents_Distance {
	private Point2D centerLocation;
	
	public List<Point2D> nearToFar(Point2D centerLoc, List<Point2D> locations){
		centerLocation = centerLoc;
		Collections.sort(locations, new NearestComponentsComparator());
		return locations;
	}
	
	public List<Point2D> farToNear(Point2D centerLoc, List<Point2D> locations){
		centerLocation = centerLoc;
		Collections.sort(locations, new NearestComponentsComparator().reversed());
		return locations;
	}
	
	public class NearestComponentsComparator implements Comparator<Point2D> {
	    @Override
	    public int compare(Point2D a, Point2D b) {
	        return (int) centerLocation.distance(a)- (int)centerLocation.distance(b);
	    }
	}
}
