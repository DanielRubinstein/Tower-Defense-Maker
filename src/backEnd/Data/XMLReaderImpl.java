package backEnd.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules;
import backEnd.GameData.State.StateImpl;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Component;
import javafx.util.Pair;

public class XMLReaderImpl implements XMLReader {
	private XStream xStream;
	
	public XMLReaderImpl(){
		xStream = new XStream(new DomDriver());
	}

	public GameData loadGameStateData(String filePath, String gameName){
		File xmlFile = new File(filePath + gameName + ".xml");
		return loadGameStateData(xmlFile);
	}
	
	public GameData loadGameStateData(File gameFile){
		GameData loadedGameData = null;
		File xmlFile = gameFile;
		try{
	        loadedGameData = (GameData) xStream.fromXML(xmlFile);       
	    }catch(Exception e){
	        throw new XMLReadingException();
	    }
		return loadedGameData;
	}
	
	public List<Map<String,?>> loadUniversalGameData(String filePath){
		@SuppressWarnings("unchecked")
		Map<String, Component> loadedComponentMap = (Map<String,Component>) loadXML(filePath, "ComponentMap");
		@SuppressWarnings("unchecked")
		Map<String, Tile> loadedTileMap = (Map<String,Tile>) loadXML(filePath, "TileMap");
		return Arrays.asList(loadedComponentMap,loadedTileMap);
	}

	private Object loadXML(String filePath, String fileName) {
		File xmlFile = null;
		try{
			xmlFile = new File(filePath + fileName + ".xml");
	            
	    }catch(Exception e){
	        throw new XMLReadingException();
	    }
		return xStream.fromXML(xmlFile);
	}

}
