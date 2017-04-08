package backEnd.Data;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.Bank.BankController;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules;
import backEnd.GameData.State.StateImpl;
import javafx.util.Pair;

public class XMLReaderImpl implements XMLReader {
	private XStream xStream;
	
	public XMLReaderImpl(){
		xStream = new XStream(new DomDriver());
	}

	public Pair<StateImpl,Rules> loadGameStateData(GameDataInterface gameData, String filePath, String gameName){
		
		return null;
	}
	
	public BankController loadUniversalGameData(GameDataInterface gameData, String filePath){
		
		return null;
	}

}
