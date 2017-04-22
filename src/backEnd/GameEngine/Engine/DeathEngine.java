package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.ComponentGraphImpl;
import backEnd.GameEngine.Behaviors.DeathBehavior;
import javafx.geometry.Point2D;

/**
 * Check if objects are dead, remove them from the grid if so + if the object
 * spawns another object when it dies, add it to the grid.
 * 
 * @author Daniel
 *
 */

public class DeathEngine implements Engine {
	private DeathBehavior DB;
	public void gameLoop(GameData gameData, double stepTime) {
		DB = new DeathBehavior();
		List<Component> toRemove=new ArrayList<Component>();
		Map<Component, Point2D> toAdd=new HashMap<Component, Point2D>();
		for (Component struct : gameData.getState().getComponentGraph().getAllComponents()) {
			DB.execute(struct);
			if (DB.isDead()) {
				toRemove.add(struct);
				//System.out.println("OK something is indeed dead");
				//System.out.println("previous score is "+gameData.getStatus().getStatusItemValue("Score"));
				gameData.getStatus().incrementStatusItem("KillCount", 1);
				gameData.getStatus().incrementStatusItem("Money", (Integer)struct.getAttribute("MoneyBounty").getValue());
				gameData.getStatus().incrementStatusItem("Score", (Integer)struct.getAttribute("ScoreBounty").getValue());
				System.out.println("current score is "+gameData.getStatus().getStatusItemValue("Score"));

				if (DB.spawnsOnDeath()) {
					Object currentLocation = struct.getMyAttributes().get("Position").getValue();
					toAdd.put(DB.getNewComponent(), (Point2D) currentLocation);
					//System.out.println("OK spawned");
				}
			}

		}
		for (Component toDelete: toRemove){
			//System.out.println("ID is "+toDelete.printID());
			gameData.getState().getComponentGraph().removeComponent(toDelete);
			//System.out.println("deleted once");
		}
		for (Component c: toAdd.keySet()){
			gameData.getState().getComponentGraph().addComponentToGrid(c, toAdd.get(c));
			System.out.println("Added once");
		}
	}
}
