package backEnd.LevelProgression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import data.DataController;
import data.XMLReadingException;

/**
 * 
 * @author Riley Nisbet
 *
 */
public class LevelProgressionController {
	private Map<String,List<String>> gamesMap; //String gameName -> List of Level names
	private DataController myDataController;
	
	public LevelProgressionController(DataController dataController, Map<String,List<String>> gamesMap){
		this.gamesMap = gamesMap;
		this.myDataController = dataController;
	}
	
	public List<String> getGameList(){
		return new ArrayList<String>(gamesMap.keySet());
	}
	
	public List<String> getLevelList(String gameName){
		return gamesMap.get(gameName);
	}
	
	public void setLevelList(String gameName, List<String> levelList){
		gamesMap.put(gameName, levelList);
	}
	
	public void addNewGame(String gameName){
		gamesMap.put(gameName, new ArrayList<String>());
	}
	
	public GameData getNextLevel(String gameName, String currentLevel) throws XMLReadingException{
		List<String> levelPathsList = gamesMap.get(gameName);
		String nextLevel = null;
		for (int i = 0; i < levelPathsList.size(); i++){
			if (levelPathsList.get(i).equals(currentLevel)){
				nextLevel = levelPathsList.get(++i);
				break;
			}
		}
		if (nextLevel == null){
			//TODO: save high score here?
			return null;
			//there is no next level
		}
		else{
			return myDataController.loadGameStateData(nextLevel);
		}
	}
	
	public void addLevelToGame(String gameName, String level){
		List<String> levelPathsList = gamesMap.get(gameName);
		levelPathsList.add(level);
		gamesMap.put(gameName, levelPathsList);
	}
	
	public Map<String,List<String>> getGamesMap(){
		return gamesMap;
	}
}
