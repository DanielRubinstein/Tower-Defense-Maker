package backEnd.Data;

import java.util.List;

import backEnd.Bank.BankController;
import backEnd.GameData.GameDataInterface;

public interface XMLWriter {
	
	void saveGameStateData(GameDataInterface gameData, String filePath, String GameName);
	
	void saveUniversalGameData(BankController bankController, String filePath);
	
}
