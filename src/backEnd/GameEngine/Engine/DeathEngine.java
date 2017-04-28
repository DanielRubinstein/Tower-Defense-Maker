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
				//System.out.println("Death Behavior Created for " + struct);
				toRemove.add(struct);
				gameData.getStatus().incrementStatusItem("KillCount", 1);
				gameData.getStatus().incrementStatusItem("Money", (Integer)struct.getAttribute("MoneyBounty").getValue());
				gameData.getStatus().incrementStatusItem("Score", (Integer)struct.getAttribute("ScoreBounty").getValue());
				//System.out.println("current score is "+gameData.getStatus().getStatusItemValue("Score"));

				if (DB.spawnsOnDeath()) {
					Object currentLocation = struct.getMyAttributes().get("Position").getValue();
					Component newComponent=DB.getNewComponent();
					newComponent.setAttributeValue("Position", (Point2D) currentLocation);
					toAdd.put(DB.getNewComponent(), (Point2D) currentLocation);
				}
			}

		}
		for (Component toDelete: toRemove){
			gameData.getState().getComponentGraph().removeComponent(toDelete);
			//System.out.println("Deathbehavior executed, component removed");
		}
		for (Component c: toAdd.keySet()){
			gameData.getState().getComponentGraph().addComponentToGrid(c, toAdd.get(c));
		}
	}
}
