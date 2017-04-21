package backEnd.GameEngine.Engine;
import java.io.FileNotFoundException;


import backEnd.GameEngine.Behaviors.*;
import frontEnd.CustomJavafxNodes.ErrorDialog;
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
	private Tile currentTile;
	private MoveBehavior mb;
	/**
	 * 
	 * @param TileGrid the Grid of Tiles that Components must navigate
	 * @param xStart the starting x-coordinate
	 * @param yStart the starting y-coordinate
	 */
	@Override
	public void gameLoop(State currentState, double stepTime) {
		myState=currentState;
		ComponentGraph newGraph=new ComponentGraphImpl();
		for (Component c: myState.getComponentGraph().getAllComponents()){
			Object o = c.getAttribute("Position").getValue();
			Point2D currentLocation=(Point2D) o;
			mb=new MoveBehavior(c);
			currentTile = currentState.getTileGrid().getTileByScreenLocation(currentLocation);
			if (currentTile==null){
				continue;
			}
			try {
				Object speedObj=c.getAttribute("Speed").getValue();
				mb.setMoveAmount((double) speedObj);
				mb.execute(currentTile);
				Point2D newPosition=mb.getPosition();
				newGraph.addComponentToGrid(c, newPosition);
			} catch (FileNotFoundException e) {
				ErrorDialog fnf = new ErrorDialog();
				fnf.create("Error", "File not found");
				break;
			}
		}
		myState.setComponentGraph(newGraph);
		System.out.println("PLEASE STOP MOVE ENGINE 5");
		return;
	}
}