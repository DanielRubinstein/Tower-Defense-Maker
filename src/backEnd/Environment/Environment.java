package backEnd.Environment;

import java.util.List;

import backEnd.State;
import backEnd.GameEngine.Component;

public interface Environment {

	
	/**
	 * @return StructureBank of all available structures
	 */
	public ComponentBank getStructureBank();
	
	/**
	 * sets state of Environment for access by author or player mode
	 */
	public void setState(State state);
	

	/**	
	 * sets Mode to new Mode
	 * @param newMode
	 */
	public void setMode(Mode mode);
	
	/**
	 * @param struct 
	 * @return List<Behavior> that contains potential available behaviors for struct
	 */
	public List<Behavior> getComponentBehaviorList(Component component);
	
	/**
	 * sets list of potential available structure behaviors to newList
	 * 
	 * @param newList
	 */
	public void setComponentBehaviorList(List<Behavior> newList);
	
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

	/**
	 * Changes from the current Mode to another concrete implementation of Mode (e.g. going from God Mode to Player Mode)
	 * @return
	 */
	public Mode changeMode();
}
