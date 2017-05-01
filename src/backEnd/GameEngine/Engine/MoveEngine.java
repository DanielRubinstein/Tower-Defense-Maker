package backEnd.GameEngine.Engine;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;
import backEnd.GameData.GameData;
import backEnd.GameData.State.*;
/**
 * Performs movement behaviors for all movable components
 * @author Daniel
 *
 */
public class MoveEngine implements Engine{
	private State myState;
	private Tile currentTile;
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();
	
	private final String DOWN=STRING_RESOURCES.getFromStringConstants("Down");
	private final String UP=STRING_RESOURCES.getFromStringConstants("Up");
	private final String LEFT=STRING_RESOURCES.getFromStringConstants("Left");
	private final String RIGHT=STRING_RESOURCES.getFromStringConstants("Right");

	/**
	 * 
	 * @param TileGrid the Grid of Tiles that Components must navigate
	 * @param xStart the starting x-coordinate
	 * @param yStart the starting y-coordinate
	 */
		
	public void gameLoop(GameData gameData, double stepTime) {
		myState=gameData.getState();
		for (Component myComponent: myState.getComponentGraph().getAllComponents()){
			move(myComponent);
		}
	}
	

	private void move(Component c) {
		Coordinates previousMovement=c.getPreviousMovement();
		Coordinates newMovement;
		double speed = c.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("Speed")).getValue();
		Point2D newPoint;
		Point2D currentLocation=c.<Point2D>getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
		double currentX = currentLocation.getX();
		double currentY = currentLocation.getY();
		newMovement=new Coordinates(0,0); //null movement (no horizontal or vertical movement)
		currentTile = myState.getTileGrid().getTileByScreenPosition(currentLocation);
		if (currentTile==null){
			return;
		}
		if ((currentTile.<String>getAttribute((STRING_RESOURCES.getFromAttributeNames("MoveDirection"))).getValue().equals(""))){
			return;
		}
		String DIRECTION = currentTile.<String>getAttribute(STRING_RESOURCES.getFromAttributeNames("MoveDirection")).getValue();
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
		c.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Position"), newPoint);		
	}
}
