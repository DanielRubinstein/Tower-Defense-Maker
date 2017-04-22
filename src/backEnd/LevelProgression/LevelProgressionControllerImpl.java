package backEnd.LevelProgression;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.Mode.Mode;
import data.DataController;
import data.XMLReadingException;

/**
 * 
 * @author Riley Nisbet
 *
 */
public class LevelProgressionControllerImpl implements LevelProgressionControllerReader {
	private Map<String,List<String>> gamesMap; //String gameName -> List of Level names
	private DataController myDataController;
	private Mode myMode;
	
	public LevelProgressionControllerImpl(Mode mode, DataController dataController, Map<String,List<String>> gamesMap){
		this.myMode = mode;
		this.gamesMap = gamesMap;
		this.myDataController = dataController;
	}
	
	/* (non-Javadoc)
	 * @see backEnd.LevelProgression.LevelProgressionControllerReader#getGameList()
	 */
	@Override
	public List<String> getGameList(){
		return new ArrayList<String>(gamesMap.keySet());
	}
	
	/* (non-Javadoc)
	 * @see backEnd.LevelProgression.LevelProgressionControllerReader#getLevelList(java.lang.String)
	 */
	@Override
	public List<String> getLevelList(String gameName){
		return gamesMap.get(gameName);
	}
	
	public List<String> getFullLevelList(){
		List<String> allLevels = new ArrayList<String>();
		for (List<String> levels : gamesMap.values()){
			allLevels.addAll(levels);
		}
		return allLevels;
	}
	
	public void setLevelList(String gameName, List<String> levelList){
		gamesMap.put(gameName, levelList);
	}
	
	public void addNewGame(String gameName){
		gamesMap.put(gameName, new ArrayList<String>());
	}
	
	public GameData getNextLevel(String gameName, String currentLevel) throws XMLReadingException, FileNotFoundException{
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
	
	public Mode getMode(){
		return myMode;
	}
}
