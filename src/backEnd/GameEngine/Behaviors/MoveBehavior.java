package backEnd.GameEngine.Behaviors;

import java.util.Map;
import java.util.Observable;

import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

import backEnd.GameData.State.TileAttributeType;
import backEnd.GameData.State.TileImpl;
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
	
	@SuppressWarnings("unchecked")
	public MoveBehavior(Component inputComponent){
		myComponent=inputComponent;
		currentPosition=(Point2D) myComponent.getAttribute("Position").getValue();
	}
	
	@Override
	public <T> void execute(T tile) {//pass in a tile //TODO error checking
		currentTile=(Tile) tile;
		switch ((String) currentTile.getAttribute("MoveDirection").getValue()) {
		case "LEFT":
			newPoint=new Point2D(currentPosition.getX()-Constants.defaultMoveAmount, currentPosition.getY());				
		case "RIGHT":
			newPoint=new Point2D(currentPosition.getX()+Constants.defaultMoveAmount, currentPosition.getY());				
		case "UP":
			newPoint=new Point2D(currentPosition.getX(), currentPosition.getY()+Constants.defaultMoveAmount);				
		case "DOWN":
			newPoint=new Point2D(currentPosition.getX(), currentPosition.getY()-Constants.defaultMoveAmount);				
			
		default: throw new IllegalArgumentException(); //TODO: figure out how we are doing error handling
		}
	}

	/*
	 * return the component's new, updated position
	 */
	public Point2D getPosition(){
		return newPoint;
	}
	
	/*
	 * TODO: does using observables make sense anymore?
	 */
	@Override
	public void update(Observable newData, Object arg) {
		myAttributes = (AttributeData) newData;
	}
}