package data;

import java.util.List;
import java.util.Map;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

/**
 * This Class handles saving and loading data in this program
 * @author Riley Nisbet
 *
 */

public class DataController {
	
	private static final String UNIV_GAME_DATA_PATH = "data/UniversalGameData/";
	private static final String GAME_STATE_DATA_PATH = "data/GameStateData/";
	private XMLWriter myXMLWriter;
	private BankController bankController;
	
	public DataController(){
		myXMLWriter = new XMLWriterImpl();
	}
	
	@SuppressWarnings("unchecked")
	public BankController generateBanks(){
		try{
			List<Map<String,?>> objectMaps = XMLReader.loadUniversalGameData(UNIV_GAME_DATA_PATH);
			Map<String,Component> componentMap = (Map<String,Component>) objectMaps.get(0);
			Map<String,Tile > tileMap = (Map<String,Tile>) objectMaps.get(1);
			return new BankController(tileMap, componentMap);
		} catch (XMLReadingException e){
			return new BankController();
		}
	}
	
	public void saveUniversalGameData(){
		myXMLWriter.saveUniversalGameData(bankController, UNIV_GAME_DATA_PATH);
	}
	
	public void saveCurrentGameStateData(GameData gameData, String gameName){
		myXMLWriter.saveGameStateData(gameData, GAME_STATE_DATA_PATH, gameName);
	}
}
