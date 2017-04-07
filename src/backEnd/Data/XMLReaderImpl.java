package backEnd.Data;

import backEnd.GameDataInterface;
import backEnd.Rules;
import backEnd.Environment.BankController;
import backEnd.State.StateImpl;
import javafx.util.Pair;

public class XMLReaderImpl implements XMLReader {
	private XStream xStream;
	
	public XMLReaderImpl(){
		
	}

	public Pair<StateImpl,Rules> loadGameStateData(GameDataInterface gameData, String filePath, String gameName){
		
		return null;
	}
	
	public BankController loadUniversalGameData(GameDataInterface gameData, String filePath){
		
		return null;
	}

}
