package backEnd.GameEngine.Engine;

import java.util.List;
import java.util.ResourceBundle;

import backEnd.Attribute.Attribute;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;
/**
 * 
 * @author Alex
 * @author Christian
 */
public class SpawnEngine implements Engine{

	private int spawnPeriod = 100;
	private final static String RESOURCES_PATH = "resources/SOMERESOURCEFILE"; //Fix
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private String componentType = "TileType";
	private String spawnerType = "Spawner";
	private String spawnQueueAttributeName = "SpawnQueue";
	
	private boolean paused = true;
	private Long startSpawnTimeMillis;
	
	
	// TODO Add logic in pausing the game and starting again... Fucks up the timeline
	
	@Override
	public void gameLoop(State currentState, double stepTime) {
		//spawnPeriod = currentState.getSpawnPeriod(); Implement this ish
		//currentState
		
		if(!paused){
			startSpawnTimeMillis = System.currentTimeMillis();
		}
		List<Tile> tileList = currentState.getTileGrid().getAllTiles();
		for(Tile spawnTile : tileList){
			if(!spawnTile.getAttribute(myResources.getString(spawnQueueAttributeName)).getValue().equals(null)){
				Attribute<SpawnQueue> spawnQueueAttribute = ((Attribute<SpawnQueue>)spawnTile.getAttribute(myResources.getString(spawnQueueAttributeName)));
				SpawnQueue currentSpawnQueue = spawnQueueAttribute.getValue();
				// Spawning with frequencies
				for(Component component:currentSpawnQueue.getNextFrequency(startSpawnTimeMillis - System.currentTimeMillis())){
					spawn(component);
				}
				// Spawning directly
				spawn(currentSpawnQueue.getNextSpawn());
				/**
				 * IF SPAWNING THEN
				 *    POP SPAWN QUEUE
				 *    SEND TO FRONTEND
				 */
			}
		}
	}

	private void spawn(Component component) {
		if(component == null){return;}
		//Spawn it lmao
		// Not sure how to connect this to frontend
	}
}
