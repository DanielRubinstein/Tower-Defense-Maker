package backEnd.LevelProgression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.GameData.GameData;
import backEnd.Mode.Mode;
import data.DataController;
import data.XMLReadingException;

/**
 * 
 * @author Riley Nisbet
 *
 */
public class LevelProgressionControllerImpl implements LevelProgressionControllerReader, LevelProgressionControllerEditor {
	private Map<String,List<String>> gamesMap; //String gameName -> List of Level names
	private DataController myDataController;
	private Mode myMode;
	private static final String LEVEL_TEMPLATE_PATH = "data/GameStateData/";
	
	public LevelProgressionControllerImpl(Mode mode, DataController dataController, Map<String,List<String>> gamesMap){
		this.myMode = mode;
		this.gamesMap = gamesMap;
		this.myDataController = dataController;
	}
	

	@Override
	public List<String> getGameList(){
		return new ArrayList<String>(gamesMap.keySet());
	}
	
	
	@Override
	public List<String> getLevelList(String gameName){
		return gamesMap.get(gameName);
	}
	
	
	@Override
	public List<String> getFullLevelList(){
		File file = new File(LEVEL_TEMPLATE_PATH);
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		/*List<String> allLevels = new ArrayList<String>();
		for (List<String> levels : gamesMap.values()){
			allLevels.addAll(levels);
		}*/
		return Arrays.asList(directories);
	}
	
	
	@Override
	public void setLevelList(String gameName, List<String> levelList){
		gamesMap.put(gameName, levelList);
	}
	
	
	@Override
	public void addNewGame(String gameName){
		gamesMap.put(gameName, new ArrayList<String>());
	}
	
	@Override
	public void removeGame(String gameName){
		gamesMap.remove(gameName);
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
	
	@Override
	public void addLevelToGame(String gameName, String level){
		List<String> levelPathsList = gamesMap.get(gameName);
		levelPathsList.add(level);
		gamesMap.put(gameName, levelPathsList);
	}
	
	@Override
	public void removeLevelFromGame(String gameName, String level){
		List<String> levelPathsList = gamesMap.get(gameName);
		levelPathsList.remove(level);
		gamesMap.put(gameName, levelPathsList);
	}

	public Map<String,List<String>> getGamesMap(){
		return gamesMap;
	}
	
	public Mode getMode(){
		return myMode;
	}
}
