package backEnd.GameEngine.Engine;

import java.util.List;
import java.util.ResourceBundle;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
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
	private String spawnQueueAttribute = "SpawnQueue";
	private String spawnFrequenciesAttribute = "SpawnQueue";
	
	private boolean paused = true;
	private Long startSpawnTime;
	private Long lastSpawnTime;
	@Override
	public void gameLoop(State currentState, double stepTime) {
		//spawnPeriod = currentState.getSpawnPeriod(); Implement this ish
		if(!paused){
			startSpawnTime = System.currentTimeMillis();
		}
		List<Tile> tileList = currentState.getTileGrid().getAllTiles();
		for(Tile spawnTile : tileList){
			if(spawnTile.getAttribute(myResources.getString(componentType)).getValue().equals(spawnerType)){
				
				/**
				 * IF SPAWNING THEN
				 *    POP SPAWN QUEUE
				 *    SEND TO FRONTEND
				 */
			}
		}
	}
	
	private void spawn(Component component) {
		//Spawn it lmao
	}
}
