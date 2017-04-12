package backEnd.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.Tile;

/**
 * This class handles saving both game state data and universal game data
 * @author Riley Nisbet
 *
 */
public class XMLWriterImpl implements XMLWriter{
	private XStream xStream;
	
	public XMLWriterImpl(){
		xStream = new XStream(new DomDriver());
		xStream.alias("GameData", GameData.class);
		xStream.alias("BankController", GameData.class);
	}
	
	public void saveGameStateData(GameDataInterface gameData, String filePath, String gameName){
		String gameDataXML = xStream.toXML(gameData);
		saveToXML(filePath, gameName, gameDataXML);
	}
	
	public void saveUniversalGameData(BankController bankController, String filePath){
		Map<String, ComponentImpl> componentMap = bankController.getComponentMap();
		String componentMapXML = xStream.toXML(componentMap);
		Map<String, Tile> tileMap = bankController.getTileMap();
		String tileMapXML = xStream.toXML(tileMap);
		saveToXML(filePath, "ComponentMap", componentMapXML);
		saveToXML(filePath, "TileMap", tileMapXML);
	}
	
	private void saveToXML(String filePath, String fileName, String xmlToWrite) {
		FileOutputStream fos = null;
		try {
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

}
