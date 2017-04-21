package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
<<<<<<< HEAD
import java.util.HashMap;
=======
import java.util.List;
>>>>>>> ecb9e3800ae6366ed3d14cd2f320159997a3d621
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
<<<<<<< HEAD
import backEnd.GameData.State.TileGrid;
=======
import backEnd.LevelProgression.LevelProgressionController;
>>>>>>> ecb9e3800ae6366ed3d14cd2f320159997a3d621

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

		xStream.alias("BankController", BankController.class);
		xStream.alias("TileGrid", TileGrid.class);
	}
	
	public void saveGameStateData(GameDataInterface gameData, String filePath, String gameName)
	{
		
		String rulesXML = xStream.toXML(gameData.getRules());
		saveToXML(filePath, gameName+ "_rules", rulesXML);
		
		String componentMapXML = xStream.toXML(gameData.getState().getComponentGraph().getComponentMap());
		saveToXML(filePath, gameName + "_componentlocmap", componentMapXML);
		
		String tileGridXML = xStream.toXML(gameData.getState().getTileGrid());
		saveToXML(filePath, gameName + "_tilegrid", tileGridXML);
		
		
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
	public void saveLevelProgressionData(LevelProgressionController levelProgression, String filePath) {
		Map<String, List<String>> gamesMap = levelProgression.getGamesMap();
		String gamesMapXML = xStream.toXML(gamesMap);
		saveToXML(filePath, "GamesMap", gamesMapXML);
	}

}
