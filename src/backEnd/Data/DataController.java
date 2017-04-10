package backEnd.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Component;

/**
 * This Class handles saving and loading data in this program
 * @author Riley Nisbet
 *
 */

public class DataController {
	private static final String GAME_STATE_DATA_PATH = "data/GameStateData/";
	private static final String UNIV_GAME_DATA_PATH = "data/UniversalGameData/";
	private XMLReader myXMLReader;
	private XMLWriter myXMLWriter;
	private Map<String,Component> componentMap;
	private Map<String,Tile >tileMap;
	private BankController bankController;
	
	@SuppressWarnings("unchecked")
	public DataController (BankController bankController){
		this.bankController = bankController;
		myXMLReader = new XMLReaderImpl();
		myXMLWriter = new XMLWriterImpl();
		List<Map<String,?>> objectMaps = myXMLReader.loadUniversalGameData(UNIV_GAME_DATA_PATH);
		componentMap = (Map<String,Component>) objectMaps.get(0);
		tileMap = (Map<String,Tile>) objectMaps.get(1);
	}
	
	public Map<String, Component> getComponentsMap(){
		return componentMap;
	}
	
	public GameData getGameDataFromName(String gameName){
		return myXMLReader.loadGameStateData(GAME_STATE_DATA_PATH, gameName);
	}
	
	public GameData getGameDataFromFile(File file){
		return myXMLReader.loadGameStateData(file);
	}
	
	public Map<String, Tile> getTileMap(){
		return tileMap;
	}
	
	public void saveUniversalGameData(){
		myXMLWriter.saveUniversalGameData(bankController, UNIV_GAME_DATA_PATH);
	}
}
