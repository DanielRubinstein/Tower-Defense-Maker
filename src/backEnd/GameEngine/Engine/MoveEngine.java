package backEnd.GameEngine.Engine;
import java.io.FileNotFoundException;
import backEnd.GameEngine.Behaviors.*;
import frontEnd.Menus.ErrorDialog;
import javafx.geometry.Point2D;
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
	@Override
	public void gameLoop(State currentState, double stepTime) {
		myState=currentState;
		for (Component c: myState.getComponentGraph().getAllComponents()){
			System.out.println(c instanceof Component);
			Tile currentTile = myState.getTileGrid().getTileByLocation((Point2D)c.getAttribute("Position").getValue()); 
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
		
		
	}
}