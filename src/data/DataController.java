package data;

import java.util.List;
import java.util.Map;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.LevelProgression.LevelProgressionControllerReader;

/**
 * This Class handles saving and loading data in this program
 * @author Riley Nisbet
 *
 */

public class DataController {
	
	private static final String UNIV_GAME_DATA_PATH = "data/UniversalGameData/";
	private static final String GAME_STATE_DATA_PATH = "data/GameStateData/";
	private XMLWriter myXMLWriter;
	private XMLReader myXMLReader;
	private BankController bankController;
	
	public DataController(){
		myXMLWriter = new XMLWriterImpl();
		myXMLReader = new XMLReaderImpl();
	}
	
	@SuppressWarnings("unchecked")
	public BankController generateBanks(){
		try{
			List<Map<String,?>> objectMaps = myXMLReader.loadUniversalGameData(UNIV_GAME_DATA_PATH);
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

	public GameData loadGameStateData(String nextLevel) throws XMLReadingException {
		return myXMLReader.loadGameStateData(GAME_STATE_DATA_PATH, nextLevel);
	}
	
	public void saveLevelProgressionData(LevelProgressionControllerReader levelProgression){
		myXMLWriter.saveLevelProgressionData(levelProgression, UNIV_GAME_DATA_PATH);
	}
	
	public LevelProgressionControllerReader loadLevelProgressionData() throws XMLReadingException{
		Map<String, List<String>> gamesMap = myXMLReader.loadGamesMap(UNIV_GAME_DATA_PATH);
		LevelProgressionControllerReader levelProgression = new LevelProgressionControllerImpl(this, gamesMap);
		return levelProgression;
	}
}
