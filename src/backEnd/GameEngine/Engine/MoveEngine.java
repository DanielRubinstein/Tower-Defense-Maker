package backEnd.GameEngine.Engine;
import java.util.ResourceBundle;
import javafx.geometry.Point2D;
import backEnd.GameData.GameData;
import backEnd.GameData.State.*;
/**
 * Performs movement behaviors for all movable components
 * @author Daniel
 * @author Christian Martindale
 *
 */
public class MoveEngine implements Engine{
	private State myState;
	private Tile currentTile;
	private final String BUNDLE_NAME = "resources.constants.stringResourceBundle";
	private final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private final String ATTRIBUTE_BUNDLE_NAME = "resources.allAttributeNames";
	private final ResourceBundle ATTRIBUTE_RESOURCE_BUNDLE = ResourceBundle.getBundle(ATTRIBUTE_BUNDLE_NAME);
	private final String DOWN=RESOURCE_BUNDLE.getString("Down");
	private final String UP=RESOURCE_BUNDLE.getString("Up");
	private final String LEFT=RESOURCE_BUNDLE.getString("Left");
	private final String RIGHT=RESOURCE_BUNDLE.getString("Right");

	/**
	 * 
	 * @param TileGrid the Grid of Tiles that Components must navigate
	 * @param xStart the starting x-coordinate
	 * @param yStart the starting y-coordinate
	 */
		
	public void gameLoop(GameData gameData, double stepTime) {
		myState=gameData.getState();
		for (ComponentImpl myComponent: myState.getComponentGraph().getAllComponents()){
			move(myComponent);
		}
	}

	private void move(ComponentImpl c) {
		Coordinates previousMovement=c.getPreviousMovement();
		Coordinates newMovement;
		double speed = c.<Double>getAttribute(ATTRIBUTE_RESOURCE_BUNDLE.getString("Speed")).getValue();
		Point2D newPoint;
		Point2D currentLocation=c.<Point2D>getAttribute(ATTRIBUTE_RESOURCE_BUNDLE.getString("Position")).getValue();
		double currentX = currentLocation.getX();
		double currentY = currentLocation.getY();
		newMovement=new Coordinates(0,0); //null movement (no horizontal or vertical movement)
		currentTile = myState.getTileGrid().getTileByScreenPosition(currentLocation);
		if (currentTile==null){
			return;
		}
		String DIRECTION = currentTile.<String>getAttribute(ATTRIBUTE_RESOURCE_BUNDLE.getString("MoveDirection")).getValue();
		if (LEFT.equals(DIRECTION)&&(myState.getTileGrid().atMiddleYOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0))) {
				newMovement = new Coordinates(-1, 0);

		} else if (RIGHT.equals(DIRECTION)&&(myState.getTileGrid().atMiddleYOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0))) {
				newMovement = new Coordinates(1, 0);
		} else if (UP.equals(DIRECTION)&&(myState.getTileGrid().atMiddleXOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0))) {
				newMovement = new Coordinates(0, -1);
		} else if (DOWN.equals(DIRECTION)&&(myState.getTileGrid().atMiddleXOfTile(currentLocation)||(previousMovement.getX()==0&&previousMovement.getY()==0))) {
				newMovement = new Coordinates(0, 1);
		} else {
			if (!(previousMovement.getX()==0&&previousMovement.getY()==0)){
				newMovement=previousMovement;
			}
		}
		Coordinates velocity=new Coordinates(newMovement.getX()*speed, newMovement.getY()*speed);
		newPoint = new Point2D( currentX+velocity.getX(), currentY+velocity.getY());
		c.setPreviousMovement(newMovement);
		c.setAttributeValue(ATTRIBUTE_RESOURCE_BUNDLE.getString("Position"), newPoint);		
	}
}
