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
		for (Component c: myState.getComponentGraph().getAllComponents()){
			mb=new MoveBehavior(c);
			Object o = c.getAttribute("Position").getValue();
			Point2D currentLocation=(Point2D) o;
			if (currentLocation==null){ //there are some components that have been intialized with empty values. why?
				System.out.println("We're checking a component with an uninitialized location.");
				continue;
			}
			//try{
			System.out.println("currentLocation: "+currentLocation.getX());
			currentTile = myState.getTileGrid().getTileByScreenLocation(currentLocation); 
			myState.getComponentGraph().removeComponent(c);
			try {
				mb.setMoveAmount((double) c.getAttribute("Speed").getValue());
				mb.execute(currentTile);
				Point2D newPosition=mb.getPosition();
				System.out.println("Move Behavior executed. Old position was: "+currentLocation + " new position is "+newPosition);
				myState.getComponentGraph().addComponentToGrid(c, newPosition);
			} catch (FileNotFoundException e) {
				ErrorDialog fnf = new ErrorDialog();
				fnf.create("Error", "File not found");
			//}

			//} catch (Exception e){
			//	System.out.println("other erorr "+e.getMessage());
			//	e.printStackTrace();
			}
			
		}
		
		
	}
}