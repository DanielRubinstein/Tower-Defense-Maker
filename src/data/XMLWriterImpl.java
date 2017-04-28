package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import resources.constants.StringResourceBundle;


/**
 * This class handles saving both game state data and universal game data
 * @author Riley Nisbet, Juan Philippe
 *
 */
public class XMLWriterImpl implements XMLWriter{
	private XStream xStream;
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
	public XMLWriterImpl(){
		xStream = new XStream(new DomDriver());
		//TODO: this should be a List<Rule>
		xStream.alias("Rules", Rule.class);
		xStream.alias("ComponentGraph", ComponentGraph.class);
		xStream.alias("TileGrid", TileGrid.class);
	}
	
	public void saveGameStateData(GameDataInterface gameData, String filePath, String levelName)
	{
		
		String rulesXML = xStream.toXML(gameData.getRules());
		
		saveToXML(filePath + levelName +"/", strResources.getFromFilePaths("Rules_FileName"), rulesXML);
		
		gameData.getState().getComponentGraph().saveAndClearObservers();
		
		String componentMapXML = xStream.toXML(gameData.getState().getComponentGraph().getComponentMap());
		saveToXML(filePath+ levelName +"/", strResources.getFromFilePaths("ComponentGraph_FileName"), componentMapXML);
		
		gameData.getState().getComponentGraph().setComponentObservers();
		
		String playerStatusXML = xStream.toXML(gameData.getStatus());
		saveToXML(filePath+ levelName +"/", strResources.getFromFilePaths("PlayerSatus_FileName"), playerStatusXML);
		
		gameData.getState().getTileGrid().saveAndClearTileObservers();
		
		String tileGridXML = xStream.toXML(gameData.getState().getTileGrid().getInstantiator());
		saveToXML(filePath + levelName +"/", strResources.getFromFilePaths("TileGrid_FileName"), tileGridXML);
		
		gameData.getState().getTileGrid().setTileObservers();
	}

	

	public void saveUniversalGameData(BankController bankController, String filePath){
		Map<String, Component> componentMap = bankController.getComponentMap();
		String componentMapXML = xStream.toXML(componentMap);
		Map<String, Tile> tileMap = bankController.getTileMap();
		String tileMapXML = xStream.toXML(tileMap);
		saveToXML(filePath, "ComponentMap", componentMapXML);
		saveToXML(filePath, "TileMap", tileMapXML);
	}
	
	private void saveToXML(String filePath, String fileName, String xmlToWrite) {
		FileOutputStream fos = null;
		try {
			
			File folderFile = new File(filePath);
			folderFile.mkdir();
			
			System.out.println(filePath + fileName + ".xml");
			File gameFile = new File(filePath + fileName + ".xml");
			gameFile.createNewFile();
		    fos = new FileOutputStream(gameFile, false);

		    fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
		    byte[] bytes = xmlToWrite.getBytes("UTF-8");
		    fos.write(bytes);
		} catch(Exception e) {
		    //throw new XMLWritingException("Error writing " + fileName + ".xml");
			e.printStackTrace();
		}
		finally{
	        if(fos != null){
	            try{
	                fos.close();
	            }catch (IOException e) {
	            	throw new XMLWritingException("Error closing " + fileName + ".xml");
	            }
	        }
	    }
	}

	@Override
	public void saveGamesMapData(Map<String, List<String>> gamesMap, String filePath) {
		String gamesMapXML = xStream.toXML(gamesMap);
		saveToXML(filePath, strResources.getFromFilePaths("GamesMap_FileName"), gamesMapXML);
	}

	@Override
	public void saveLevelTemplate(GameData gameData, String levelTemplateDataPath, String levelName)
	{
		String rulesXML = xStream.toXML(gameData.getRules());
<<<<<<< HEAD
		
		saveToXML(levelTemplateDataPath + levelName +"/", "rules", rulesXML);
=======
		saveToXML(levelTemplateDataPath + levelName +"/", strResources.getFromFilePaths("Rules_FileName"), rulesXML);
>>>>>>> 24bb9c3fa1bf8e7df3482775321bbe43dd9b7326
		
		gameData.getState().getComponentGraph().saveAndClearObservers();
		
		String componentMapXML = xStream.toXML(gameData.getState().getComponentGraph().getComponentMap());
<<<<<<< HEAD
		saveToXML(levelTemplateDataPath+ levelName +"/", "componentgraph", componentMapXML);
=======
		componentsStripper.giveBackObservers();
		saveToXML(levelTemplateDataPath+ levelName +"/", strResources.getFromFilePaths("ComponentMap_FileName"), componentMapXML);
>>>>>>> 24bb9c3fa1bf8e7df3482775321bbe43dd9b7326
		
		gameData.getState().getComponentGraph().setComponentObservers();
		
		String playerStatusXML = xStream.toXML(new PlayerStatus());
		saveToXML(levelTemplateDataPath+ levelName +"/", strResources.getFromFilePaths("PlayerStatus_FileName"), playerStatusXML);
		
		gameData.getState().getTileGrid().saveAndClearTileObservers();
		
		String tileGridXML = xStream.toXML(gameData.getState().getTileGrid().getInstantiator());
<<<<<<< HEAD
		saveToXML(levelTemplateDataPath + levelName +"/", "tilegrid", tileGridXML);
		
		gameData.getState().getTileGrid().setTileObservers();
=======
		tilesStripper.giveBackObservers();
		saveToXML(levelTemplateDataPath + levelName +"/", strResources.getFromFilePaths("TileGrid_FileName"), tileGridXML);
	}

	public void saveUniversalGameData(BankController bankController, String filePath){
		Map<String, ComponentImpl> componentMap = bankController.getComponentMap();
		
		StripAndSaveObservers componentsStripper = new StripAndSaveObservers(new ArrayList<SerializableObservable>(componentMap.values()));
		componentsStripper.stripObservers();
		String componentMapXML = xStream.toXML(componentMap);
		componentsStripper.giveBackObservers();
		Map<String, Tile> tileMap = bankController.getTileMap();
		
		StripAndSaveObservers tilesStripper = new StripAndSaveObservers(new ArrayList<SerializableObservable>(tileMap.values()));
		tilesStripper.stripObservers();
		String tileMapXML = xStream.toXML(tileMap);
		tilesStripper.giveBackObservers();
		saveToXML(filePath, strResources.getFromFilePaths("ComponentMap_FileName"), componentMapXML);
		saveToXML(filePath, strResources.getFromFilePaths("TileMap_FileName"), tileMapXML);
>>>>>>> 24bb9c3fa1bf8e7df3482775321bbe43dd9b7326
	}

}
