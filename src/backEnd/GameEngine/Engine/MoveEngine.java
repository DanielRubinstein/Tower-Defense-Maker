package backEnd.GameEngine.Engine;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import backEnd.Coord;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameEngine.Behaviors.*;
import frontEnd.Menus.ErrorDialog;
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
	public void gameLoop(State currentState, double stepTime) {
		myState=currentState;
		for (Component c: myState.getComponentGraph().getAllComponents()){
			System.out.println(c.getMyAttributes().getAttributeMap().keySet()+" skirt skirt line 39 in moveengine");
			System.out.println("9.3/4  "+c.containsAttribute("Position"));
			System.out.println((c.getAttribute("Position").getValue()).getClass().getTypeName() +" skirt skirt line 40 in moveengine");
			//System.out.println(((AttributeImpl<Point2D>)c.getAttribute("Position").getValue()).getValue().getX() +" skirt skirt line 41 in moveengine");
			
			Attribute<Point2D> location=(AttributeImpl<Point2D>) c.getAttribute("Position");
			System.out.println("got to line 45 this is gr8");
			System.out.println("is null :" + location.getValue()==null + " line 46");
			Point2D myPoint=new Point2D((int) location.getValue().getX(), (int) location.getValue().getY());
			System.out.println("DIDNT FAIL");
			Tile currentTile=myState.getTileGrid().getTileByLocation(myPoint);
			System.out.println("WORLDSTAR");
			myState.getComponentGraph().removeComponent(c);
			MoveBehavior mb=new MoveBehavior(c);
			try {
				mb.execute(currentTile);
			} catch (FileNotFoundException e) {
				ErrorDialog fnf = new ErrorDialog();
				fnf.create("Error", "File not found");
			}
			Point2D newPosition=mb.getPosition();
			myState.getComponentGraph().addComponentToGrid(c, newPosition);
			
		}
		
		//TODO : different case for bullets
		// if (Component.getMyType.equals("Projectile")
		//(Component.getMyType.equals("Enemy")
		//StartPosition : Point2D
		//TargetPosition : Point2D
		
	}
}