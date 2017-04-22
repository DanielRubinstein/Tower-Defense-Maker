package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.LevelProgression.LevelProgressionControllerReader;



/**
 * This class handles saving both game state data and universal game data
 * @author Riley Nisbet
 *
 */
public class XMLWriterImpl implements XMLWriter{
	private XStream xStream;
	
	public XMLWriterImpl(){
		xStream = new XStream(new DomDriver());
		xStream.alias("Rules", Rules.class);
		
		xStream.alias("ComponentGraph", ComponentGraph.class);
		xStream.alias("TileGrid", TileGrid.class);
	}
	
	public void saveGameStateData(GameDataInterface gameData, String filePath, String levelName)
	{
		
		String rulesXML = xStream.toXML(gameData.getRules());
		saveToXML(filePath + levelName +"/", "rules", rulesXML);
		
		String componentMapXML = xStream.toXML(gameData.getState().getComponentGraph().getComponentMap());
		saveToXML(filePath+ levelName +"/", "componentgraph", componentMapXML);
		
		String playerStatusXML = xStream.toXML(gameData.getState().getPlayerStatus());
		saveToXML(filePath+ levelName +"/", "playerstatus", playerStatusXML);
		
		String tileGridXML = xStream.toXML(gameData.getState().getTileGrid());
		saveToXML(filePath + levelName +"/", "tilegrid", tileGridXML);
		
		
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
			System.out.println(filePath + fileName + ".xml");
			File gameFile = new File(filePath + fileName + ".xml");
		    fos = new FileOutputStream(gameFile, false);
		    fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
		    byte[] bytes = xmlToWrite.getBytes("UTF-8");
		    fos.write(bytes);
		} catch(Exception e) {
		    throw new XMLWritingException("Error writing " + fileName + ".xml");
		}finally{
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
	public void saveLevelProgressionData(LevelProgressionControllerImpl levelProgression, String filePath) {
		Map<String, List<String>> gamesMap = levelProgression.getGamesMap();
		String gamesMapXML = xStream.toXML(gamesMap);
		saveToXML(filePath, "GamesMap", gamesMapXML);
	}

}
