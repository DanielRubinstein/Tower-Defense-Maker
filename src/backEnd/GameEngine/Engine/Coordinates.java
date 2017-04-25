package backEnd.GameEngine.Engine;
/**
 * @author Daniel
 * Used by MoveEngine to figure out the angle in which the component should be moved.
 */
public class Coordinates {
	private double x;
	private double y;
	public Coordinates(double myX, double myY){
		x=myX;
		y=myY;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public Coordinates getMovementAngleCoordinates(Coordinates oldCoordinates){
		return new Coordinates((this.getX()+oldCoordinates.getX())/Math.sqrt(2),(this.getY()+oldCoordinates.getY())/Math.sqrt(2)) ;
	}
}