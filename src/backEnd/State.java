package backEnd;

import java.util.List;

import backEnd.Environment.Structure;
import backEnd.Environment.Tile;

public interface State {
	
	// not sure what return type for these should be
	// so void for now to compile
	public Tile[][] getUnmodifiableTileGrid();
	
	public List<Structure>[][] getUnmodifiableStructureGrid();
	
	public Tile[][] getTileGrid();
	
	public List<Structure>[][] getStructureGrid();


}
