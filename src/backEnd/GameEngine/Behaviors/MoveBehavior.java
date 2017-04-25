package backEnd.GameEngine.Behaviors;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Observable;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;
import resources.Constants;

/**
 * This class allows us to keep each Component's location attribute up to date as we move the components
 * around via the MoveEngine.
 * @author Daniel
 *
 */
public class MoveBehavior implements Behavior {
	public AttributeData myAttributes;
	private Point2D currentPosition;
	private Point2D newPoint;
	private Component myComponent;
	private Tile currentTile;
	private double moveAmount = Constants.defaultMoveAmount;
	
	public MoveBehavior(Component inputComponent){
		myComponent=inputComponent;
		Object currentPositionO=myComponent.getAttribute("Position").getValue();
		currentPosition=(Point2D) currentPositionO;
		//System.out.println("in move behavior " + myComponent.getAttribute("Position").getValue() + "    " +myComponent.getAttribute("Position").getValue().getClass());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> void execute(T tile) throws FileNotFoundException {//pass in a tile //TODO error checking
		currentTile=(Tile) tile;

		//System.out.println("   tile  "+tile);
		//System.out.println("   tile  "+currentTile.getAttribute("MoveDirection"));
		
		//System.out.println("   in move behavior " +currentTile.getAttribute("MoveDirection").getValue().getClass() + "    " +
				//currentTile.getAttribute("MoveDirection").getValue());
		if (currentTile.getAttribute("MoveDirection").getValue()==null){
			System.out.println("WO BU ZHIDAO");
			return;
		}
		switch ((String) currentTile.getAttribute("MoveDirection").getValue()) {
		case "Left":
			newPoint=new Point2D(currentPosition.getX()-moveAmount, currentPosition.getY());	
			break;
		case "Right":
			newPoint=new Point2D(currentPosition.getX()+moveAmount, currentPosition.getY());	
			break;
		case "Up":
			newPoint=new Point2D(currentPosition.getX(), currentPosition.getY()-moveAmount);
			break;
		case "Down":
			newPoint=new Point2D(currentPosition.getX(), currentPosition.getY()+moveAmount);
			break;
		default:
			//System.out.println("Movebehavior- No direction specified");
			return;
		}
		myComponent.setAttributeValue("Position",newPoint); //does it get overwritten?
		return;

	}

	/**
	 * return the component's new, updated position
	 */
	public Point2D getPosition(){
		return newPoint;
	}
	
	/**
	 * sets the distance the Component will move per iteration of the game loop
	 */
	public void setMoveAmount(double speed){
		moveAmount = speed;
	}
}