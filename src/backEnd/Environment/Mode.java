package backEnd.Environment;

import backEnd.GameEngine.Component;

public abstract class Mode {

	public Mode() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * adds structure to StructureGrid
	 */
	public abstract void addStruct(Component struct, int[] location);
	
	/**
	 * removes structure from StructureGrid
	 */
	public abstract void removeStruct(Component struct);
	
	public abstract void moveStruct();
	
	public abstract void changeTile();
	
	public abstract void setStructBehavior();
	
	public abstract void setGameRule();
	
	public abstract void onStructClick();
	
	

}
