package backEnd.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;

public interface XMLReader {
	
	GameData loadGameStateData(String filePath, String gameName);
	
	GameData loadGameStateData(File gameFile);
	
	List<Map<String,?>> loadUniversalGameData(String filePath);

}
