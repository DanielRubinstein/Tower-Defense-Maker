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
import backEnd.GameEngine.Engine.Spawning.SpawnQueueInstantiator;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import backEnd.GameData.Rules.RulesMap;
import javafx.geometry.Point2D;
import backEnd.GameData.State.PlayerStatus;


/**
 * This class handles loading both game state data and universal game data
 * @author Riley Nisbet, Juan Philippe
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
	public GameData loadGameStateData(String filePath, String levelName) throws XMLReadingException, FileNotFoundException
	{

		TileGrid grid = new TileGridImpl((TileGridInstantiator) xStream.fromXML(new File(filePath+"/" + levelName+"/tilegrid.xml")));
		ComponentGraph graph = new ComponentGraphImpl((HashMap<Point2D, List<Component>>) xStream.fromXML(new File(filePath+"/" + levelName+"/componentgraph.xml")));
		
		HashMap<String, SpawnQueueInstantiator> instantiatorMap = (HashMap<String, SpawnQueueInstantiator>) xStream.fromXML(new File(filePath+"/" + levelName+"/spawns.xml"));
		HashMap<String, SpawnQueues> spawnMap = new HashMap<String, SpawnQueues>();
		
		for (String x : instantiatorMap.keySet())
		{
			spawnMap.put(x, new SpawnQueues(instantiatorMap.get(x)));
		}
		
		StateImpl state = new StateImpl(grid, graph, spawnMap);
		
		return new GameData(state, (PlayerStatus) xStream.fromXML(new File(filePath+"/" + levelName+"/playerstatus.xml")),
				(RulesMap) xStream.fromXML(new File(filePath+"/" + levelName+"/rules.xml")));

	}
	
	/* (non-Javadoc)
	 * @see data.XMLReader#loadUniversalGameData(java.lang.String)
	 */
	@Override
	public List<Map<String,?>> loadUniversalGameData(String filePath) throws XMLReadingException
	{
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
