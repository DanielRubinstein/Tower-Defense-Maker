package data.GamePrep;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import backEnd.GameData.GameData;
import backEnd.GameData.Rules.RulesMap;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.StateImpl;
import data.XMLReader;
import data.XMLReaderImpl;
import data.XMLReadingException;
/**
 * Wrapper class to get GameData without using reflection and hardcoding a method name.
 * This class is much more extendible and hides any ugly implementation details
 * @author Tim
 * @author Juan
 *
 */
import resources.constants.StringResourceBundle;
public class DataInputLoader
{
	private static final StringResourceBundle STRING_RESOURCE_BUNDLE = new StringResourceBundle();
	private static final String UNIVERSAL_DATA_PATH = STRING_RESOURCE_BUNDLE.getFromFilePaths("Univ_Game_Data_Path");
	
	private XMLReader myXMLReader;
	private GameData myGameData;
	private Map<String, List<String>> gamesMap;
	private String myLevelName, myGameName;
	
	public DataInputLoader(){
		 myXMLReader = new XMLReaderImpl();
		 
		 generateGamesMap();
	}
	

	public DataInputLoader(StartingInput input) throws XMLReadingException{
		this();
		myGameData = generateGameData(input);
		myGameName = input.getGameName();
		myLevelName = STRING_RESOURCE_BUNDLE.getFromStringConstants("DefaultLevelName");
	}
	
	public DataInputLoader(File file) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this();
		myGameData = generateGameData(file.getName(),file.getParent());
		myLevelName = file.getName();
		myGameName = file.getParentFile().getParentFile().getName();
	}
	
	public DataInputLoader(Object o) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this();
		Constructor<? extends DataInputLoader> cons = this.getClass().getDeclaredConstructor(o.getClass());
		
		
		DataInputLoader d = cons.newInstance(o);
		
		myGameData = d.getGameData();
		myLevelName = d.getLevelName();
		myGameName = d.getGameName();
	}
	
	public GameData getGameData(){
		return myGameData;	
	}
	
	public Map<String, List<String>> getGamesMap()
	{
		return gamesMap;
	}
	
	private GameData generateGameData(String levelName, String dataPath
			){
		try{
			return myXMLReader.loadGameStateData(dataPath, levelName);
		}catch(Exception e){
			//throw new XMLReadingException();
			e.printStackTrace();
			return null;
		}
	}

	private GameData generateGameData(StartingInput startInp) throws XMLReadingException
	{
		try{
			return createGameData(startInp);
		}catch(Exception e){
			e.printStackTrace();
			throw new XMLReadingException();
		}
	}
	
	private GameData createGameData(StartingInput startingInputs) throws FileNotFoundException {
		StateImpl state = new StateImpl(startingInputs.getNumCols(), startingInputs.getNumRows());
		GameData gameData = new GameData(state, new PlayerStatus() , new RulesMap());
		return gameData;
	}
	
	private void generateGamesMap() {
		try {
			gamesMap = myXMLReader.loadGamesMap(UNIVERSAL_DATA_PATH);
		 }
		 catch (XMLReadingException e)
		 {
			e.printStackTrace();
		 }
	}

	public String getLevelName(){
		return myLevelName;
	}

	public String getGameName() {

		return myGameName;
	}
	
}
