package backEnd.Data;

import java.io.File;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules;
import backEnd.GameData.State.StateImpl;
import javafx.util.Pair;

public interface XMLReader {
	
	Pair<StateImpl,Rules> loadGameStateData(GameDataInterface gameData, String filePath, String gameName);
	
	BankController loadUniversalGameData(GameDataInterface gameData, String filePath);

	GameData Load(File loadGameFile);

}
