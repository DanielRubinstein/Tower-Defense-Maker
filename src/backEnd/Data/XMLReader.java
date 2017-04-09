package backEnd.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules;
import backEnd.GameData.State.StateImpl;
import javafx.util.Pair;

public interface XMLReader {
	
	GameData loadGameStateData(String filePath, String gameName);
	
	GameData loadGameStateData(File gameFile);
	
	List<Map<String,?>> loadUniversalGameData(String filePath);

}
