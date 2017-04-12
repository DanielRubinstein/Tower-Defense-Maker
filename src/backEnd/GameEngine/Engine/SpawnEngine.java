package backEnd.GameEngine.Engine;

import java.util.List;
import java.util.ResourceBundle;

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
	private String typeOfTile = "TileType";
	private String spawnerType = "Spawner";
	@Override
	public void gameLoop(State currentState, double stepTime) {
		//spawnPeriod = currentState.getSpawnPeriod(); Implement this ish
		List<Tile> tileList = currentState.getTileGrid().getAllTiles();
		for(Tile spawnTile : tileList){
			if(spawnTile.getAttribute(myResources.getString(typeOfTile)).getValue().equals(spawnerType)){
				/**
				 * IF SPAWNING THEN
				 *    POP SPAWN QUEUE
				 *    SEND TO FRONTEND
				 */
			}
		}
	}
}
