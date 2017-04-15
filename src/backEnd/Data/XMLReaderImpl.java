package backEnd.Data;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import backEnd.GameData.Rules;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;

/**
 * This class handles loading both game state data and universal game data
 * @author Riley Nisbet
 *
 */

public class XMLReaderImpl implements XMLReader {
	private XStream xStream;
	
	public XMLReaderImpl(){
		xStream = new XStream(new DomDriver());
	}

	public GameData loadGameStateData(String filePath, String gameName) throws XMLReadingException{
		
		StateImpl state = new StateImpl((TileGrid) xStream.fromXML(new File(filePath+gameName+"_tilegrid.xml")), 
				(ComponentGraph) xStream.fromXML(new File(filePath+gameName+"_componentlocmap.xml")));
		
		return new GameData(state,(Rules)xStream.fromXML(new File(filePath+gameName+"_rules")));
	}
	
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

}
