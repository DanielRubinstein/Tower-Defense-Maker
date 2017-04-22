package backEnd.LevelProgression;

import java.util.List;
import java.util.Map;

public interface LevelProgressionControllerReader {

	List<String> getGameList();

	List<String> getLevelList(String gameName);

}