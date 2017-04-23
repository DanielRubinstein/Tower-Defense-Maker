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
	public void gameLoop(GameData gameData, double stepTime) {
		myState=gameData.getState();
		List<Component> toRemove=new ArrayList<Component>();
		Map<Component, Point2D> toAdd=new HashMap<Component, Point2D>();
		for (Component c: myState.getComponentGraph().getAllComponents()){
			Object o = c.getAttribute("Position").getValue();
			Point2D currentLocation=(Point2D) o;
			currentTile = gameData.getState().getTileGrid().getTileByScreenLocation(currentLocation);
			if (currentTile==null){
				continue;
			}
			mb=new MoveBehavior(c);
			try {
				Object speedObj=c.getAttribute("Speed").getValue();
				mb.setMoveAmount((double) speedObj);
				mb.execute(currentTile);
				Point2D newPosition=mb.getPosition();
				if (newPosition!=null&&!newPosition.equals(currentLocation)){
					toAdd.put(c, newPosition);
					toRemove.add(c);
				}
			} catch (FileNotFoundException e) {
				ErrorDialog fnf = new ErrorDialog();
				fnf.create("Error", "File not found");
				break;
			}
		}

		for (Component toDelete: toRemove){
			gameData.getState().getComponentGraph().removeComponent(toDelete);
		}
		for (Component c: toAdd.keySet()){
			gameData.getState().getComponentGraph().addComponentToGrid(c, toAdd.get(c));
		}

		return;
	}
}