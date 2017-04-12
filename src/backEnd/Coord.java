package backEnd;

import javafx.geometry.Point2D;

/**
 * Helper class for BFS in pathfinding algorithm
 * @author Christian Martindale (with online resource input)
 * @author Daniel
 *
 */
public class Coord {
    private Coord last;
	private int xCoord;
    private int yCoord;
    public Coord(int x, int y, Coord prev) {
        this.xCoord = x;
        this.yCoord = y;
        this.last = prev;
    }
    
    public int getXCoord(){
    	return xCoord;
    }
    public int getYCoord(){
    	return yCoord;
    }
    
    public Point2D getAsPoint(){
    	return new Point2D(xCoord, yCoord);
    }
    
    public Coord getLastCoord(){
    	return last;
    }
    
    @Override
    public boolean equals(Object other){
		if(other == null || !(other instanceof Coord)){return false;}
    	if(((Coord) other).getXCoord() == this.getXCoord() && ((Coord) other).getYCoord() == this.getYCoord()){
    		return true;
    	}
    	return false;
    	
    }
}