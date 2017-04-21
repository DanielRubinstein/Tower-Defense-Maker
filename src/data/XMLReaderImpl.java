package data;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.LevelProgression.LevelProgressionControllerImpl;

/**
 * This class handles loading both game state data and universal game data
 * @author Riley Nisbet
 *
 */

public class XMLReaderImpl implements XMLReader{
	private XStream xStream;
	
	public XMLReaderImpl(){
		xStream = createXStream();
	}
	
	private XStream createXStream() {
		return new XStream(new DomDriver());
	}

	/* (non-Javadoc)
	 * @see data.XMLReader#loadGameStateData(java.lang.String, java.lang.String)
	 */
	@Override
	public GameData loadGameStateData(String filePath, String gameName) throws XMLReadingException{
		File xmlFile = new File(filePath + gameName + ".xml");
		return loadGameStateData(xmlFile);
	}
	
	/* (non-Javadoc)
	 * @see data.XMLReader#loadGameStateData(java.io.File)
	 */
	@Override
	public GameData loadGameStateData(File gameFile) throws XMLReadingException{
		File xmlFile = gameFile;
		try{
	        return (GameData) xStream.fromXML(xmlFile);      
	    }catch(Exception e){
	        throw new XMLReadingException(gameFile);
	    }
	}
	
	/* (non-Javadoc)
	 * @see data.XMLReader#loadUniversalGameData(java.lang.String)
	 */
	@Override
	public List<Map<String,?>> loadUniversalGameData(String filePath) throws XMLReadingException{
		@SuppressWarnings("unchecked")
		Map<String, Component> loadedComponentMap = (Map<String,Component>) loadXML(filePath, "ComponentMap");
		@SuppressWarnings("unchecked")
		Map<String, Tile> loadedTileMap = (Map<String,Tile>) loadXML(filePath, "TileMap");
		return Arrays.asList(loadedComponentMap,loadedTileMap);
	}

	private Object loadXML(String filePath, String fileName) throws XMLReadingException {
		File xmlFile = new File(filePath + fileName + ".xml");
		try{
			return xStream.fromXML(xmlFile);
		} catch (Exception e){
			throw new XMLReadingException(xmlFile);
		}
	}

	@Override
	public Map<String,List<String>> loadGamesMap(String filePath) throws XMLReadingException {
		//TODO: error checking, properties file
		Map<String, List<String>> gamesMap = (Map<String, List<String>>) loadXML(filePath, "GamesMap");
		return gamesMap;
	}

}
