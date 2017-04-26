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
	private final String POSITION="Position";
	private final String TYPE="Type";
	private final String PROJECTILE="Projectile";
	private final String ENEMY="Enemy";
	private final String TOWER="Tower";
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
			if (!c.<String>getAttribute(TYPE).getValue().equals(ENEMY)){
				continue;
			}
			Point2D currentLocation=c.<Point2D>getAttribute(POSITION).getValue();
			currentTile = gameData.getState().getTileGrid().getTileByScreenPosition(currentLocation);
			if (currentTile==null){
				continue;
			}
			mb=new MoveBehavior(c);
			try {
				mb.execute(currentTile, gameData.getState().getTileGrid().atMiddleOfTile(currentLocation));
				Point2D newPosition=mb.getPosition();
				if (newPosition!=null&&!newPosition.equals(currentLocation)){
					c.setAttributeValue(POSITION, newPosition);
				}
			} catch (FileNotFoundException e) {
				ErrorDialog fnf = new ErrorDialog();
				fnf.create("Error", "File not found");
				break;
			}
		}
		return;
	}
}