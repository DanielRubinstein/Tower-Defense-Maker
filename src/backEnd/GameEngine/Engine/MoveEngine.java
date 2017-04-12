package backEnd.GameEngine.Engine;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import backEnd.Coord;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.*;
import javafx.geometry.Point2D;
import resources.Constants;
import backEnd.GameData.State.*;


/**
 * Performs movement behaviors for all movable components
 * @author Daniel
 * @author Christian Martindale
 *
 */
public class MoveEngine implements Engine{
	private State myState;
	/**
	 * simple BFS to label each Tile with the
	 * direction an object on the Tile should move
	 * 
	 * might need to be in State? will need to be recalled when a tower is added or removed from State
	 * 
	 * @param TileGrid the Grid of Tiles that Components must navigate
	 * @param xStart the starting x-coordinate
	 * @param yStart the starting y-coordinate
	 */

	@SuppressWarnings("unchecked")
	@Override
	public void gameLoop(State currentState) {
		myState=currentState;
		for (Component c: myState.getComponentGraph().getAllComponents()){
			Tile currentTile=myState.getTileGrid().getTileByLocation((Point2D) c.getAttribute("Position").getValue()); 
			// TODO: change attribute name if needed/ at least put it in resource file
			String currentDirection=(String) currentTile.getAttribute("MoveDirection").getValue();
			Point2D currentPosition=(Point2D) c.getAttribute("Position").getValue();
			myState.getComponentGraph().removeComponent(c);
			//TODO: error checking/ going off the screen.
			Point2D newPosition = currentPosition; //initialize position with current location, change it after
			if (currentDirection.equals("LEFT")){
				newPosition=new Point2D(currentPosition.getX()-Constants.defaultMoveAmount, currentPosition.getY());				
			}
			else if (currentDirection.equals("RIGHT")){
				newPosition=new Point2D(currentPosition.getX()+Constants.defaultMoveAmount, currentPosition.getY());				
			}
			else if (currentDirection.equals("UP")){
				newPosition=new Point2D(currentPosition.getX(), currentPosition.getY()+Constants.defaultMoveAmount);				
			}
			else if (currentDirection.equals("DOWN")){
				newPosition=new Point2D(currentPosition.getX()-Constants.defaultMoveAmount, currentPosition.getY()-Constants.defaultMoveAmount);				
			}
			myState.getComponentGraph().addComponentToGrid(c, newPosition);
			Attribute<Point2D> newPositionAttribute=new AttributeImpl<Point2D>();
			newPositionAttribute.setValue(newPosition);
			Attribute<Point2D> positionAttribute=(Attribute<Point2D>) c.getAttribute("Position");
			positionAttribute.setValue(newPosition);
		}
		
	}
}