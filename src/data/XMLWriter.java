package data;

import java.util.List;
import java.util.Map;

import backEnd.Bank.BankController;
import backEnd.GameData.GameDataInterface;

public interface XMLWriter {
	
	void saveGame(GameDataInterface gameData, String gamePath, String levelName);
	
	void saveUniversalGameData(BankController bankController, String filePath);
	
	void saveGamesMapData(Map<String,List<String>> gamesMap, String filePath);

	void saveLevelTemplate(GameDataInterface gameData, String gameName, String levelName);
	
}