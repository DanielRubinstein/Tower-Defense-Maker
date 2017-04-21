package backEnd.GameEngine.Engine;

import java.util.List;
import java.util.ResourceBundle;

import backEnd.Attribute.Attribute;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentBuilder;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;

/**
 * 
 * @author Alex
 * @author Christian
 */
public class SpawnEngine implements Engine {

	private final static String RESOURCES_PATH = "resources/SOMERESOURCEFILE"; // Fix
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private String spawnQueueAttributeName = "SpawnQueue";

	private boolean internalPaused = true;
	private boolean gamePaused = true;
	private boolean started = false;
	private Long startSpawnTimeMillis;
	private Long startPauseTimeMillis;

	// TODO Add logic in pausing the game and starting again... Fucks up the
	// timeline

	@Override
	public void gameLoop(State currentState, double stepTime) {
		// gamePaused = currentState.getEnginePauserinolamo;

		if (internalPaused && gamePaused) {
			return;
		}
		if (!internalPaused && gamePaused) { // Someone pressed pause
			startPauseTimeMillis = System.currentTimeMillis();
			return;
		}
		if (internalPaused && !gamePaused) { // Someone pressed play
			startSpawnTimeMillis += System.currentTimeMillis() - startPauseTimeMillis;
		}
		if (!started) {
			started = true;
			startSpawnTimeMillis = System.currentTimeMillis();
		}
		List<Tile> tileList = currentState.getTileGrid().getAllTiles();
		for (Tile spawnTile : tileList) {
			if (!spawnTile.getAttribute(myResources.getString(spawnQueueAttributeName)).getValue().equals(null)) {
				Attribute<SpawnQueue> spawnQueueAttribute = (Attribute<SpawnQueue>) spawnTile.getAttribute(myResources.getString(spawnQueueAttributeName));
				SpawnQueue currentSpawnQueue = spawnQueueAttribute.getValue();
				// Spawning with frequencies
				for (Component component : currentSpawnQueue
						.getNextFrequency(startSpawnTimeMillis - System.currentTimeMillis())) {
					spawn(component);
				}
				// Spawning directly with spawn queue
				spawn(currentSpawnQueue.getNextSpawn());
			}
		}
	}

	private void spawn(Component component) {
		if (component == null) {
			return;
		}
		ComponentBuilder componentBuilder = new ComponentBuilder(component);
		Component spawnable = componentBuilder.getComponent();
		//TODO PUSH TO FRONTEND SCREEN
	}
}
