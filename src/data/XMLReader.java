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

/**
 * This class handles loading both game state data and universal game data
 * @author Riley Nisbet
 *
 */

public class XMLReader {

	public static GameData loadGameStateData(String filePath, String gameName) throws XMLReadingException{
		File xmlFile = new File(filePath + gameName + ".xml");
		return loadGameStateData(xmlFile);
	}
	
	public static GameData loadGameStateData(File gameFile) throws XMLReadingException{
		File xmlFile = gameFile;
		XStream xStream = createXStream();
		try{
	        return (GameData) xStream.fromXML(xmlFile);      
	    }catch(Exception e){
	        throw new XMLReadingException(gameFile);
	    }
	}

	private static XStream createXStream() {
		return new XStream(new DomDriver());
	}
	
	public static List<Map<String,?>> loadUniversalGameData(String filePath) throws XMLReadingException{
		@SuppressWarnings("unchecked")
		Map<String, Component> loadedComponentMap = (Map<String,Component>) loadXML(filePath, "ComponentMap");
		@SuppressWarnings("unchecked")
		Map<String, Tile> loadedTileMap = (Map<String,Tile>) loadXML(filePath, "TileMap");
		return Arrays.asList(loadedComponentMap,loadedTileMap);
	}

	private static Object loadXML(String filePath, String fileName) throws XMLReadingException {
		File xmlFile = new File(filePath + fileName + ".xml");
		XStream xStream = createXStream();
		try{
			return xStream.fromXML(xmlFile);
		} catch (Exception e){
			throw new XMLReadingException(xmlFile);
		}

		
	}

}
