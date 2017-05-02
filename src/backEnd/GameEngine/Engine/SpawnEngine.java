
package backEnd.GameEngine.Engine;

import java.util.Collection;
import java.util.List;

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
 * Deals with spawning Components in State
 * @author Alex Salas
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
		Collection<Tile> tileList = gameData.getState().getTileGrid().getAllTiles();
		for (Tile spawnTile : tileList) {
			String spawnQueueNameObj = spawnTile.<String>getAttribute("SpawnTimeline").getValue();
			SpawnQueues currentSpawnQueue = gameData.getState().getSpawnQueues().get(spawnQueueNameObj);
			if (currentSpawnQueue != null) {
				// Spawning with frequencies
				//System.out.println(this.getClass().getName() + ": FrequencyQueue: " + currentSpawnQueue.getFrequencySpawnQueue().size());
				for (String component : currentSpawnQueue.getNextFrequencySpawn(gameData.getGameTime(), stepTime)) {
					spawn(myBank.getComponent(component), spawnTile);
				}
				// Spawning directly with spawn queue
				String componentSingleSpawnName = currentSpawnQueue.getNextSingleSpawn(gameData.getGameTime());
				if(componentSingleSpawnName != null){
					Component nextQueueSpawn = myBank.getComponent(componentSingleSpawnName);
					spawn(nextQueueSpawn, spawnTile);
				}
			}
		}
		updateSpawnTimelines(gameData.getGameTime());
	}

	private void updateSpawnTimelines(double gameTime) {
		for(SpawnQueues spawnQueue : myState.getSpawnQueues().values()){
			spawnQueue.update(gameTime);
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

