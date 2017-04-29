package backEnd.GameEngine.Behaviors;
import java.io.FileNotFoundException;

import java.util.ResourceBundle;
import backEnd.Attribute.AttributeData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Engine.Coordinates;
import javafx.geometry.Point2D;
/**
 * This class allows us to keep each Component's location attribute up to date
 * as we move the components around via the MoveEngine.
 * 
 * @author Daniel
 *
 */
public class MoveBehavior implements Behavior {
	public AttributeData myAttributes;
	private Point2D currentPosition;
	private Point2D newPoint;
	private Component myComponent;
	private Tile currentTile;
	private double speed;
	private double currentX;
	private double currentY;
	private Coordinates previousMovement;
	private Coordinates newMovement;
	private Coordinates directionVector;
	private double normalizer; //used to normalize our movement vectors
	private final static String RESOURCES_PATH = "resources/attributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private final static String UP="Up";
	private final static String DOWN="Down";
	private final static String RIGHT="Right";
	private final static String LEFT="Left";
	public MoveBehavior(Component inputComponent) {
		myComponent = inputComponent;
		previousMovement = myComponent.getPreviousMovement();
		speed = myComponent.<Double>getAttribute(myResources.getString("Speed")).getValue();
		currentPosition = myComponent.<Point2D>getAttribute(myResources.getString("Position")).getValue();
	}
	public <T> void execute(T tile, boolean atMiddleOfTile) throws FileNotFoundException { // TODO error
																	// checking
		currentTile = (Tile) tile;
		if (currentTile.getAttribute(myResources.getString("MoveDirection")).getValue() == null) {
			return;
		}
		currentX = currentPosition.getX();
		currentY = currentPosition.getY();
		switch (currentTile.<String>getAttribute(myResources.getString("MoveDirection")).getValue()) {
		case LEFT:
			if (!atMiddleOfTile&&!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			else{
			normalizer=Math.sqrt(Math.pow(myComponent.getPreviousMovement().getX()-1, 2)+Math.pow(0+myComponent.getPreviousMovement().getY(), 2));
			newMovement = new Coordinates((myComponent.getPreviousMovement().getX()-1)/normalizer, (0+myComponent.getPreviousMovement().getY())/normalizer);
			}
			break;
		case RIGHT:
			if (!atMiddleOfTile&&!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			else{
			normalizer=Math.sqrt(Math.pow(myComponent.getPreviousMovement().getX()+1, 2)+Math.pow(myComponent.getPreviousMovement().getY(), 2));
			newMovement = new Coordinates((myComponent.getPreviousMovement().getX()+1)/normalizer, myComponent.getPreviousMovement().getY()/normalizer);
			}
			break;
		case UP:
			if (!atMiddleOfTile&&!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			else{
			normalizer=Math.sqrt(Math.pow(myComponent.getPreviousMovement().getX(), 2)+Math.pow(-1+myComponent.getPreviousMovement().getY(), 2));
			newMovement = new Coordinates(myComponent.getPreviousMovement().getX()/normalizer, (-1+myComponent.getPreviousMovement().getY())/normalizer);
			}
			break;
		case DOWN:
			if (!atMiddleOfTile&&!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			else{
			normalizer=Math.sqrt(Math.pow(myComponent.getPreviousMovement().getX(), 2)+Math.pow(1+myComponent.getPreviousMovement().getY(), 2));
			newMovement = new Coordinates(myComponent.getPreviousMovement().getX()/normalizer, (1+myComponent.getPreviousMovement().getY())/normalizer);
			}
			break;
		default:
			return;
		}
		adjustMovement();
		myComponent.setAttributeValue(myResources.getString("Position"), newPoint);
		return;
	}
	
	private void adjustMovement() {
		myComponent.setPreviousMovement(newMovement);
		Coordinates velocity=new Coordinates(newMovement.getX()*speed, newMovement.getY()*speed);
		newPoint = new Point2D(currentX+velocity.getX(), currentY+velocity.getY());
	}

	/**
	 * return the component's new, updated position
	 */
	public Point2D getPosition() {
		return newPoint;
	}
	@Override
	public <T> void execute(T toModify) throws FileNotFoundException {
		execute(toModify, false);
	}
}