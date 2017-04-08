package backEnd.Data;

import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Component;

public class DataController {
	private XMLReader myXMLReader;
	private XMLWriter myXMLWriter;
	private Map<String,Component> componentMap;
	private Map<String,Tile >tileMap;
	
	@SuppressWarnings("unchecked")
	public DataController (){
		myXMLReader = new XMLReaderImpl();
		myXMLWriter = new XMLWriterImpl();
		List<Map<String,?>> objectMaps = myXMLReader.loadUniversalGameData("data/UniversalGameData/");
		componentMap = (Map<String,Component>) objectMaps.get(0);
		tileMap = (Map<String,Tile>) objectMaps.get(1);
	}
	
	public Map<String, Component> getComponentsMap(){
		return componentMap;
	}
	
	public GameData getGameData(String gameName){
		return myXMLReader.loadGameStateData("data/GameStateData/", gameName);
	}
	
	public Map<String, Tile> getTileMap(){
		return tileMap;
	}
	
	

}
