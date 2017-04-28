package data;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import backEnd.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

/**
 * This Class handles saving and loading data in this program
 * @author Riley Nisbet
 *
 */

public class DataController implements DataControllerReader {
	
	private static final String UNIV_GAME_DATA_PATH = "data/UniversalGameData/";
	private static final String GAME_STATE_DATA_PATH = "data/SavedGames/";
	private static final String LEVEL_TEMPLATE_DATA_PATH = "data/LevelTemplates/";
	
	private XMLWriter myXMLWriter;
	private XMLReader myXMLReader;
	private BankController bankController;
	
	public DataController(){
		myXMLWriter = new XMLWriterImpl();
		myXMLReader = new XMLReaderImpl();
	}
	
	public void setBankController(BankController bankController){
		this.bankController = bankController;
	}
	
	public Map<String, Component> loadComponentMap()
	{
		try
		{
			List<Map<String,?>> objectMaps = myXMLReader.loadUniversalGameData(UNIV_GAME_DATA_PATH);
			return (Map<String,Component>) objectMaps.get(0);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public Map<String, Tile> loadTileMap()
	{
		try
		{
			List<Map<String,?>> objectMaps = myXMLReader.loadUniversalGameData(UNIV_GAME_DATA_PATH);
			return (Map<String,Tile>) objectMaps.get(1);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see data.DataControllerSaver#saveUniversalGameData()
	 */
	@Override
	public void saveUniversalGameData(){
		myXMLWriter.saveUniversalGameData(bankController, UNIV_GAME_DATA_PATH);
	}
	
	public void saveCurrentGameStateData(GameData gameData, String gameName){
		myXMLWriter.saveGameStateData(gameData, GAME_STATE_DATA_PATH, gameName);
	}

	public GameData loadGameStateData(String nextLevel) throws XMLReadingException, FileNotFoundException {
		return myXMLReader.loadGameStateData(GAME_STATE_DATA_PATH, nextLevel);
	}
	
	public void saveGamesMap(Map<String,List<String>> gamesMap){
		myXMLWriter.saveGamesMapData(gamesMap, UNIV_GAME_DATA_PATH);
	}
	
	public Map<String,List<String>> loadGamesMapData() throws XMLReadingException{
		Map<String, List<String>> gamesMap = myXMLReader.loadGamesMap(UNIV_GAME_DATA_PATH);
		return gamesMap;
	}

	public void saveLevelTemplate(GameData gameData, String myLevelName)
	{
		myXMLWriter.saveLevelTemplate(gameData, LEVEL_TEMPLATE_DATA_PATH, myLevelName);
	}
}
