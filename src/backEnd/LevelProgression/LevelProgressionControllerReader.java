package backEnd.LevelProgression;

import java.util.List;

import backEnd.Mode.Mode;

public interface LevelProgressionControllerReader {

	List<String> getGameList();

	List<String> getLevelList(String gameName);
		
	List<String> getOptions(String category);
	
	Mode getMode();
	
	String getNextLevel();
	
	List<String> getModeCategories();
	
	boolean contains(String mode);
	
	List<String> getCurrentLevelList();
	
}