package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.ComponentGraphImpl;
import backEnd.GameData.State.StateImpl;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.GameData.State.TileGridImpl;
import backEnd.GameData.State.TileGridInstantiator;
import backEnd.GameData.Rules.RulesMap;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;
import backEnd.GameData.State.PlayerStatus;


/**
 * This class handles loading both game state data and universal game data
 * @author Riley Nisbet, Juan Philippe
 *
 */

public class XMLReaderImpl implements XMLReader{
	private XStream xStream;
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
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
	public GameData loadGameStateData(String filePath, String levelName) throws XMLReadingException, FileNotFoundException
	{

		TileGrid grid = new TileGridImpl((TileGridInstantiator) xStream.fromXML(new File(filePath + "/" + levelName + "/" + 
				strResources.getFromFilePaths("TileGrid_FileName") + ".xml")));
		ComponentGraph graph = new ComponentGraphImpl((HashMap<Point2D, List<Component>>) xStream.fromXML(
				new File(filePath+"/" + levelName+"/" + strResources.getFromFilePaths("ComponentGraph_FileName") + ".xml")));
		StateImpl state = new StateImpl(grid.getNumRowsInGrid(), grid.getNumColsInGrid(), grid, graph);
		
		return new GameData(state, (PlayerStatus) xStream.fromXML(new File(filePath+"/" + levelName+"/" + 
				strResources.getFromFilePaths("PlayerStatus_FileName") + ".xml")), (RulesMap) xStream.fromXML(new 
				File(filePath+"/" + levelName+"/" + strResources.getFromFilePaths("Rules_FileName") + ".xml")));

	}
	
	/* (non-Javadoc)
	 * @see data.XMLReader#loadUniversalGameData(java.lang.String)
	 */
	@Override
	public List<Map<String,?>> loadUniversalGameData(String filePath) throws XMLReadingException
	{
		@SuppressWarnings("unchecked")
		Map<String, Component> loadedComponentMap = (Map<String,Component>) loadXML(filePath, strResources.getFromFilePaths("ComponentMap_FileName"));
		@SuppressWarnings("unchecked")
		Map<String, Tile> loadedTileMap = (Map<String,Tile>) loadXML(filePath, strResources.getFromFilePaths("TileMap_FileName"));
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
		Map<String, List<String>> gamesMap = (Map<String, List<String>>) loadXML(filePath, strResources.getFromFilePaths("GamesMap_FileName"));
		return gamesMap;
	}

}
