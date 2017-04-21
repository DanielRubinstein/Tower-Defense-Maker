package data.GamePrep;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import backEnd.GameData.GameData;
import backEnd.GameData.PlayerStatus.PlayerStatus;
import backEnd.GameData.State.StateImpl;
import data.XMLReader;
import data.XMLReaderImpl;
import data.XMLReadingException;
/**
 * Wrapper class to get GameData without using reflection and hardcoding a method name.
 * This class is much more extendible and hides any ugly implementation details
 * @author Tim
 *
 */
public class DataInputLoader {
	private GameData myGameData;
	private static final String GAME_STATE_DATA_PATH = "data/GameStateData/";
	private XMLReader myXMLReader = new XMLReaderImpl();
	
	
	public DataInputLoader(String s) throws XMLReadingException{
		myGameData = generateGameData(s);
	}
	
	public DataInputLoader(File f) throws XMLReadingException{
		myGameData = generateGameData(f);
	}
	public DataInputLoader(StartingInput input) throws XMLReadingException{
		myGameData = generateGameData(input);
	}
	
	public DataInputLoader(Object o) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends DataInputLoader> cons = this.getClass().getDeclaredConstructor(o.getClass());
		DataInputLoader d = cons.newInstance(o);
		myGameData = d.getGameData();
	}


	public GameData getGameData(){
		return myGameData;	
	}
	
	private GameData generateGameData(String gameName) throws XMLReadingException{
		try{
			return myXMLReader.loadGameStateData(GAME_STATE_DATA_PATH, gameName);
		}catch(Exception e){
			throw new XMLReadingException();
		}
	}
	private GameData generateGameData(File f) throws XMLReadingException{
		try{
			return myXMLReader.loadGameStateData(f);
		}catch(Exception e){
			throw new XMLReadingException();
		}
	}
	private GameData generateGameData(StartingInput startInp) throws XMLReadingException{
		try{
			return createGameData(startInp);
		}catch(Exception e){
			e.printStackTrace();
			throw new XMLReadingException();
		}
	}
	
	private GameData createGameData(StartingInput startingInputs) throws FileNotFoundException {
		StateImpl state = new StateImpl(startingInputs.getNumCols(), startingInputs.getNumRows());
		GameData gameData = new GameData(state, new PlayerStatus() , null);
		return gameData;
	}
	
	
}
