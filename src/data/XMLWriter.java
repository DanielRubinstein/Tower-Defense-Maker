package data;

import java.util.List;
import java.util.Map;

import backEnd.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.LevelProgression.LevelProgressionControllerReader;

public interface XMLWriter {
	
	void saveGameStateData(GameDataInterface gameData, String filePath, String GameName);
	
	void saveUniversalGameData(BankController bankController, String filePath);
	
	void saveGamesMapData(Map<String,List<String>> gamesMap, String filePath);

	void saveLevelTemplate(GameData gameData, String levelTemplateDataPath, String myLevelName);
	
}