package data;

import java.util.Map;

import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.Tile;

public interface DataControllerReader {

	void saveUniversalGameData();
	
	Map<String, ComponentImpl> loadComponentMap();
	
	Map<String, Tile> loadTileMap();

}