package data;

import java.util.List;
import java.util.Map;

import backEnd.Bank.BankController;
import backEnd.GameData.GameDataInterface;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.LevelProgression.LevelProgressionControllerReader;

public interface XMLWriter {
	
	void saveGameStateData(GameDataInterface gameData, String filePath, String GameName);
	
	void saveUniversalGameData(BankController bankController, String filePath);
	
	void saveLevelProgressionData(LevelProgressionControllerImpl levelProgression, String filePath);
	
}