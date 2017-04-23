package backEnd.GameEngine.Engine;

import java.util.List;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentBuilder;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;
import javafx.geometry.Point2D;

/**
 * 
 * @author GameEngine team (Alex, Christian, Daniel)
 */
public class SpawnEngine implements Engine {

	// private final static String RESOURCES_PATH =
	// "resources/SOMERESOURCEFILE"; // Fix
	// private final static ResourceBundle myResources =
	// ResourceBundle.getBundle(RESOURCES_PATH);
	// private String spawnQueueAttributeName = "SpawnQueue";

	private boolean gamePaused = true;
	private State myState;

	// TODO Add logic in pausing the game and starting again... Fucked up the
	// timeline

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myState = gameData.getState();
		gamePaused = myState.gameIsRunning();
		if (gamePaused) {
			return;
		}
		List<Tile> tileList = gameData.getState().getTileGrid().getAllTiles();
		for (Tile spawnTile : tileList) {
			Object spawnQueueNameObj = spawnTile.getAttribute("SpawnTimeline").getValue();
			SpawnQueue currentSpawnQueue = gameData.getState().getSpawnQueues().get((String) spawnQueueNameObj);
			if (currentSpawnQueue != null) {
				// Spawning with frequencies
				//System.out.println(this.getClass().getName() + ": FrequencyQueue: " + currentSpawnQueue.getFrequencyQueue().size());
				for (Component component : currentSpawnQueue.getNextFrequencySpawn(gameData.getGameTime())) {
					spawn(component, spawnTile);
				}
				// Spawning directly with spawn queue
				spawn(currentSpawnQueue.getNextQueueSpawn(stepTime), spawnTile);
			}
		}
	}

	private void spawn(Component component, Tile spawnTile) {
		if (component == null) {
			//System.out.println(this.getClass().getName() + ": No component to add from spawn queue");
			return;
		}
		//System.out.println(this.getClass().getName() + ": Should add component from spawn queue");
		ComponentBuilder componentBuilder = new ComponentBuilder(component);
		Component spawnable = componentBuilder.getComponent();
		Object positionObj = spawnTile.getMyAttributes().get("Position");
		Point2D spawnPosition = (Point2D) positionObj;
		myState.getComponentGraph().addComponentToGrid(spawnable, spawnPosition);
	}
}
