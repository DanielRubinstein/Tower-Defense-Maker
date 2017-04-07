package backEnd.Data;

import backEnd.GameDataInterface;
import backEnd.Rules;
import backEnd.Environment.BankController;
import backEnd.State.StateImpl;
import javafx.util.Pair;

public interface XMLReader {
	
	Pair<StateImpl,Rules> loadGameStateData(GameDataInterface gameData, String filePath, String gameName);
	
	BankController loadUniversalGameData(GameDataInterface gameData, String filePath);

}
