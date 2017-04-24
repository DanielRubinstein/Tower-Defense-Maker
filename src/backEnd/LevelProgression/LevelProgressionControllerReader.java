package backEnd.LevelProgression;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.Mode.Mode;
import data.XMLReadingException;

public interface LevelProgressionControllerReader {

	List<String> getGameList();

	List<String> getLevelList(String gameName);
	
	List<String> getFullLevelList();
	
	Mode getMode();
	
	String getNextLevel();

}