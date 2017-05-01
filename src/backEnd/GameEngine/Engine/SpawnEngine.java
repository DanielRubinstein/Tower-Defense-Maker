
package backEnd.GameEngine.Engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import backEnd.Attribute.Attribute;
import backEnd.Bank.BankControllerImpl;
import backEnd.Bank.BankControllerReader;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentBuilder;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
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
	private BankControllerReader myBank;

	// TODO Add logic in pausing the game and starting again... Fucked up the
	// timeline

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myState = gameData.getState();
		myBank = gameData.getBankController();
		gamePaused = myState.gameIsRunning();
		if (gamePaused) {
			return;
		}
		Map<String, SpawnQueues> spawnQueues = gameData.getState().getSpawnQueues();
		Map<String, List<String>> toSpawnMap = new HashMap<String, List<String>>();
		for(String key : spawnQueues.keySet()){
			List<String> toSpawn = spawnQueues.get(key).getNextSpawns(gameData.getGameTime(), stepTime);
			toSpawnMap.put(key, toSpawn);
		}
		Collection<Tile> tileList = gameData.getState().getTileGrid().getAllTiles();
		for (Tile spawnTile : tileList) {
			String spawnQueueName = spawnTile.<String>getAttribute("SpawnTimeline").getValue();
			List<String> spawnList = toSpawnMap.get(spawnQueueName);
			if (spawnList != null) {
				for (String component : spawnList) {
					spawn(myBank.getComponent(component), spawnTile);
				}
			}
		}
		updateSpawnTimelines(gameData.getGameTime());
	}

	private void updateSpawnTimelines(double gameTime) {
		for(SpawnQueues spawnQueue : myState.getSpawnQueues().values()){
			spawnQueue.update();
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
		Attribute<Point2D> spawnPositionAttribute = spawnTile.getAttribute("Position");
		Point2D tilePosition = spawnPositionAttribute.getValue();
		Point2D spawnPosition = new Point2D(tilePosition.getX(), tilePosition.getY());
		//System.out.println(this.getClass().getName() + ": Spawn Position: " + spawnPosition.getX() + " | " + spawnPosition.getY());
		myState.getComponentGraph().addComponentToGrid(spawnable, spawnPosition);
	}
}

