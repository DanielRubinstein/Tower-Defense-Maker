package backEnd.Data;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.GameDataInterface;
import backEnd.Rules;
import backEnd.Environment.BankController;
import backEnd.State.StateImpl;
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
