package backEnd.Bank;

import java.util.Map;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

public interface BankController extends BankControllerReader {

	Map<String, Tile> getAccessibleTileMap();

	Map<String, Component> getAccessibleComponentMap();

	Map<String, Component> getComponentMap();

	Map<String, Tile> getTileMap();
	
	void addNewTile(String name, Tile tile);

	void addNewComponent(String name, Component component);

	void remove(Component component);

	void remove(Tile tile);

	void refreshAccessibleMaps();

}