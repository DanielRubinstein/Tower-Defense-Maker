package backEnd.GameEngine.Engine;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
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
		for (Component struct : gameData.getState().getComponentGraph().getAllComponents()) {
			DB.execute(struct);
			if (DB.isDead()) {
				gameData.getState().getComponentGraph().getAllComponents().remove(struct);
			}
			if (DB.spawnsOnDeath()) {
				Object currentLocation = struct.getMyAttributes().get("Position").getValue();
				gameData.getState().getComponentGraph().addComponentToGrid(DB.getNewComponent(),
						(Point2D) currentLocation);
			}
		}
	}
}
