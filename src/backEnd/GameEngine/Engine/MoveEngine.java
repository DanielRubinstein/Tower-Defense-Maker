package backEnd.GameEngine.Engine;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backEnd.GameEngine.Behaviors.*;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;
import backEnd.GameData.GameData;
import backEnd.GameData.State.*;
/**
 * Performs movement behaviors for all movable components
 * @author Daniel
 * @author Christian Martindale
 *
 *
 */
public class MoveEngine implements Engine{
	private State myState;
	private Tile currentTile;
	private final static String UP="Up";
	private final static String DOWN="Down";
	private final static String RIGHT="Right";
	private final static String LEFT="Left";
	/**
	 * 
	 * @param TileGrid the Grid of Tiles that Components must navigate
	 * @param xStart the starting x-coordinate
	 * @param yStart the starting y-coordinate
	 */
	@Override
	
	
	public void gameLoop(GameData gameData, double stepTime) {
		myState=gameData.getState();
		for (Component myComponent: myState.getComponentGraph().getAllComponents()){
			move(myComponent);
		}
	}
	
	
	
	private void move(Component c) {
		Coordinates previousMovement=c.getPreviousMovement();
		Coordinates newMovement;
		double speed = c.<Double>getAttribute("Speed").getValue();
		Point2D newPoint;
		Point2D currentLocation=c.<Point2D>getAttribute("Position").getValue();
		double currentX = currentLocation.getX();
		double currentY = currentLocation.getY();
		newMovement=new Coordinates(0,0);
		currentTile = myState.getTileGrid().getTileByScreenPosition(currentLocation);
		if (currentTile==null){
			return;
		}
		switch (currentTile.<String>getAttribute("MoveDirection").getValue()) {
		case LEFT:
			if (myState.getTileGrid().atMiddleYOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement = new Coordinates(-1, 0);
			}
			else if (!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			break;
		case RIGHT:
			if (myState.getTileGrid().atMiddleYOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement = new Coordinates(1, 0);
			}
			else if (!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			break;
		case UP:
			if (myState.getTileGrid().atMiddleXOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement = new Coordinates(0, -1);
			}
			else if (!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			break;
		case DOWN:
			if (myState.getTileGrid().atMiddleXOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement = new Coordinates(0, 1);
			}
			else if (!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
			break;
		default:
			return;
		}
		Coordinates velocity=new Coordinates(newMovement.getX()*speed, newMovement.getY()*speed);
		newPoint = new Point2D( currentX+velocity.getX(), currentY+velocity.getY());
		c.setPreviousMovement(newMovement);
		c.setAttributeValue("Position", newPoint);		
	}
}