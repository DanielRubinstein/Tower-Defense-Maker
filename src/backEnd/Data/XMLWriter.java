package backEnd.Data;

import java.util.List;

import backEnd.GameDataInterface;
import backEnd.Environment.BankController;

public interface XMLWriter {
	
	void saveGameStateData(GameDataInterface gameData, String filePath, String GameName);
	
	void saveUniversalGameData(BankController bankController, String filePath);
	
	

}
