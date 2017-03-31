package backEnd;

public interface State {
	
	// not sure what return type for these should be
	// so void for now to compile
	public void getUnmodifiableTileGrid();
	
	public void getUnmodifiableStructureGrid();
	
	public void getTileGrid();
	
	public Grid getStructureGrid();


}