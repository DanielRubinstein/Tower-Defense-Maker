package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.Attribute.AttributeFactory;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeReader;
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
	private final static String DEFAULT_COMPONENT_ATTRS = "resources/defaultComponentAttributes";
	private final static ResourceBundle defaultComponentAttributesResources = ResourceBundle.getBundle(DEFAULT_COMPONENT_ATTRS);
	private Set<String> defaultComponentAttributes = defaultComponentAttributesResources.keySet();
	private final static String DEFAULT_TILE_ATTRS = "resources/defaultTileAttributes";
	private final static ResourceBundle defaultTileAttributesResources = ResourceBundle.getBundle(DEFAULT_TILE_ATTRS);
	private Set<String> defaultTileAttributes = defaultTileAttributesResources.keySet();
	
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

		//System.out.println(filePath);
		HashMap<String, SpawnQueueInstantiator> instantiatorMap = (HashMap<String, SpawnQueueInstantiator>) xStream.fromXML(new File(filePath+"/" + levelName+"/spawns.xml"));
		HashMap<String, SpawnQueues> spawnMap = new HashMap<String, SpawnQueues>();
		
		for (String x : instantiatorMap.keySet())
		{
			spawnMap.put(x, new SpawnQueues(instantiatorMap.get(x)));
		}
		
		TileGrid grid = new TileGridImpl((TileGridInstantiator) xStream.fromXML(new File(filePath + "/" + levelName + "/" + 
				strResources.getFromFilePaths("TileGrid_FileName") + ".xml")));
		ComponentGraph graph = new ComponentGraphImpl((List<Component>) xStream.fromXML(
				new File(filePath+"/" + levelName+"/" + strResources.getFromFilePaths("ComponentGraph_FileName") + ".xml")));
		//Loop through component graph and tile grid and check each component and tile's attributes
		//if it has an attribute that no longer exists, delete it
		//if it does not have an attribute that now exists, add it with default value
		makeAttributeOwnersCompatible(graph.getAllAttributeOwners(), defaultComponentAttributes);
		makeAttributeOwnersCompatible(grid.getAllAttributeOwners(), defaultTileAttributes);
		
		StateImpl state = new StateImpl(grid, graph, spawnMap);
		
		return new GameData(state, (PlayerStatus) xStream.fromXML(new File(filePath+"/" + levelName+"/" + 
				strResources.getFromFilePaths("PlayerStatus_FileName") + ".xml")), (RulesMap) xStream.fromXML(new 
				File(filePath+"/" + levelName+"/" + strResources.getFromFilePaths("Rules_FileName") + ".xml")));

	}

	private void makeAttributeOwnersCompatible(Collection<AttributeOwner> myAttrOwners, Set<String> defaultAttrNames) throws FileNotFoundException {
		AttributeFactory myAF = new AttributeFactoryImpl();
		for(AttributeOwner ao : myAttrOwners){
			//add missing attributes
			for(String attName : defaultAttrNames){
				if(!ao.contains(attName)){
					ao.addAttribute(attName, myAF.getAttribute(attName));
				}
			}
			//remove extra attributes
			for(AttributeReader<?> att : ao.getMyAttributes().getAllAttributeReaders()){
				if(!defaultAttrNames.contains(att.getName())){
					ao.removeAttribute(att.getName());
				}
			}
		}
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
		//Loop through component graph and tile grid and check each component and tile's attributes
		//if it has an attribute that no longer exists, delete it
		//if it does not have an attribute that now exists, add it with default value
		try {
			makeAttributeOwnersCompatible(getAttributeOwnerCollectionComponents(loadedComponentMap.values()), defaultComponentAttributes);
		} catch (FileNotFoundException e) {
			throw new XMLReadingException();
		}
		try {
			makeAttributeOwnersCompatible(getAttributeOwnerCollectionTiles(loadedTileMap.values()), defaultTileAttributes);
		} catch (FileNotFoundException e) {
			throw new XMLReadingException();
		}
		
		
		return Arrays.asList(loadedComponentMap,loadedTileMap);
	}
	
	private Collection<AttributeOwner> getAttributeOwnerCollectionComponents(Collection<Component> myComps){
		Collection<AttributeOwner> myAOs = new ArrayList<AttributeOwner>();
		for(AttributeOwner ao : myComps){
			myAOs.add(ao);
		}
		return myAOs;
	}
	
	private Collection<AttributeOwner> getAttributeOwnerCollectionTiles(Collection<Tile> myTiles){
		Collection<AttributeOwner> myAOs = new ArrayList<AttributeOwner>();
		for(AttributeOwner ao : myTiles){
			myAOs.add(ao);
		}
		return myAOs;
	}

	private Object loadXML(String filePath, String fileName) throws XMLReadingException {
		File xmlFile = new File(filePath + fileName + ".xml");
		try{
			return xStream.fromXML(xmlFile);
		} catch (Exception e){
			e.printStackTrace();
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
