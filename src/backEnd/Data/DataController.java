package backEnd.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.StateImpl;
import backEnd.GameData.State.Tile;
import frontEnd.Splash.StartingInput;

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
	
	public DataController(){
		myXMLReader = new XMLReaderImpl();
		myXMLWriter = new XMLWriterImpl();
	}
	
	@SuppressWarnings("unchecked")
	public BankController generateBanks(){
		try{
			List<Map<String,?>> objectMaps = myXMLReader.loadUniversalGameData(UNIV_GAME_DATA_PATH);
			componentMap = (Map<String,Component>) objectMaps.get(0);
			tileMap = (Map<String,Tile>) objectMaps.get(1);
			return new BankController(tileMap, componentMap);
		} catch (XMLReadingException e){
			return new BankController();
		}
	}

	public GameData generateGameData(String gameName) throws XMLReadingException{
		try{
			return myXMLReader.loadGameStateData(GAME_STATE_DATA_PATH, gameName);
		}catch(Exception e){
			throw new XMLReadingException();
		}
	}
	public GameData generateGameData(File f) throws XMLReadingException{
		try{
			return myXMLReader.loadGameStateData(f);
		}catch(Exception e){
			throw new XMLReadingException();
		}
	}
	public GameData generateGameData(StartingInput startInp) throws XMLReadingException{
		try{
			return createGameData(startInp);
		}catch(Exception e){
			e.printStackTrace();
			throw new XMLReadingException();
		}
	}

	
	
	
	private GameData createGameData(StartingInput dim) throws FileNotFoundException {
		StateImpl state = new StateImpl(dim.getNumCols(), dim.getNumRows());
		GameData gameData = new GameData(state, null);
		return gameData;
		
	}
	
	public Map<String, Component> getComponentsMap(){
		return componentMap;
	}
	
	public Map<String, Tile> getTileMap(){
		return tileMap;
	}
	
	public void saveUniversalGameData(){
		myXMLWriter.saveUniversalGameData(bankController, UNIV_GAME_DATA_PATH);
	}
	
	public void saveCurrentGameStateData(GameData gameData, String gameName){
		myXMLWriter.saveGameStateData(gameData, GAME_STATE_DATA_PATH, gameName);
	}
}
