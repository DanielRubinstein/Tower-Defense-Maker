package backEnd;

import java.util.List;

import backEnd.Environment.Tile;
import backEnd.GameEngine.Component;

public interface State {
	
	// not sure what return type for these should be
	// so void for now to compile
	public Tile[][] getUnmodifiableTileGrid();
	
	public List<Component>[][] getUnmodifiableStructureGrid();
	
	public Tile[][] getTileGrid();
	
	public List<Component>[][] getStructureGrid();


}