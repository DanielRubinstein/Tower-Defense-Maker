package backEnd.Environment;

import java.util.List;

public interface Environment {

	
	/**
	 * @return StructureBank of all available structures
	 */
	public StructureBank getStructureBank();
	
	/**
	 * sets state of Environment for access by author or player mode
	 */
	public void setState();
	
	/**
	 * sets Mode to new Mode
	 */
	public void setMode();
	
	/**
	 * @param struct 
	 * @return List<Behavior> that contains potential available behaviors for struct
	 */
	public List<Behavior> getStructBehaviorList(Structure struct);
	
	/**
	 * sets list of potential available structure behaviors to newList
	 * 
	 * @param newList
	 */
	public void setStructBehaviorList(List<Behavior> newList);
	
	/**
	 * @param tile
	 * @return List<TileAttribute> that contains potential available attributes for tile
	 */
	public List<TileAttribute> getTileAttributeList(Tile tile);
	
	/**
	 * sets list of potential available tile attributes to newList
	 * 
	 * @param newList
	 */
	public void setTileAttributeList(List<TileAttribute> newList);

}
