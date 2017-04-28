package data;

import java.util.Map;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

public interface DataControllerReader {

	void saveUniversalGameData();
	
	Map<String, Component> loadComponentMap();
	
	Map<String, Tile> loadTileMap();

}