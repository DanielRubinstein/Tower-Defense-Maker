package backEnd.LevelProgression;

import java.util.List;
import java.util.Map;

import backEnd.Mode.Mode;

public interface LevelProgressionControllerReader {

	List<String> getGameList();

	List<String> getLevelList(String gameName);
	
	List<String> getFullLevelList();
	
	Mode getMode();

}