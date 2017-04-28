package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;


/**
 * This class handles saving both game state data and universal game data
 * @author Riley Nisbet, Juan Philippe
 *
 */
public class XMLWriterImpl implements XMLWriter{
	private XStream xStream;
	
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
		
		saveToXML(filePath + levelName +"/", "rules", rulesXML);
		
		gameData.getState().getComponentGraph().saveAndClearObservers();
		
		String componentMapXML = xStream.toXML(gameData.getState().getComponentGraph().getComponentMap());
		saveToXML(filePath+ levelName +"/", "componentgraph", componentMapXML);
		
		gameData.getState().getComponentGraph().setComponentObservers();
		
		String playerStatusXML = xStream.toXML(gameData.getStatus());
		saveToXML(filePath+ levelName +"/", "playerstatus", playerStatusXML);
		
		gameData.getState().getTileGrid().saveAndClearTileObservers();
		
		String tileGridXML = xStream.toXML(gameData.getState().getTileGrid().getInstantiator());
		saveToXML(filePath + levelName +"/", "tilegrid", tileGridXML);
		
		gameData.getState().getTileGrid().setTileObservers();
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
		saveToXML(filePath, "GamesMap", gamesMapXML);
	}

	@Override
	public void saveLevelTemplate(GameData gameData, String levelTemplateDataPath, String levelName)
	{
		String rulesXML = xStream.toXML(gameData.getRules());
		saveToXML(levelTemplateDataPath + levelName +"/", "rules", rulesXML);
		
		StripAndSaveObservers componentsStripper = new StripAndSaveObservers(new ArrayList<SerializableObservable>(gameData.getState().getComponentGraph().getAllComponents()));
		String componentMapXML = xStream.toXML(gameData.getState().getComponentGraph().getComponentMap());
		componentsStripper.giveBackObservers();
		saveToXML(levelTemplateDataPath+ levelName +"/", "componentgraph", componentMapXML);
		
		String playerStatusXML = xStream.toXML(new PlayerStatus());
		saveToXML(levelTemplateDataPath+ levelName +"/", "playerstatus", playerStatusXML);
		
		StripAndSaveObservers tilesStripper = new StripAndSaveObservers(new ArrayList<SerializableObservable>(gameData.getState().getTileGrid().getAllTiles()));
		String tileGridXML = xStream.toXML(gameData.getState().getTileGrid().getInstantiator());
		tilesStripper.giveBackObservers();
		saveToXML(levelTemplateDataPath + levelName +"/", "tilegrid", tileGridXML);
	}

	public void saveUniversalGameData(BankController bankController, String filePath){
		Map<String, Component> componentMap = bankController.getComponentMap();
		
		StripAndSaveObservers componentsStripper = new StripAndSaveObservers(new ArrayList<SerializableObservable>(componentMap.values()));
		componentsStripper.stripObservers();
		String componentMapXML = xStream.toXML(componentMap);
		componentsStripper.giveBackObservers();
		Map<String, Tile> tileMap = bankController.getTileMap();
		
		StripAndSaveObservers tilesStripper = new StripAndSaveObservers(new ArrayList<SerializableObservable>(tileMap.values()));
		tilesStripper.stripObservers();
		String tileMapXML = xStream.toXML(tileMap);
		saveToXML(filePath, "ComponentMap", componentMapXML);
		saveToXML(filePath, "TileMap", tileMapXML);
	}
}
