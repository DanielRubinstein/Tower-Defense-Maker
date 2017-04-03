package backEnd.Mode;

import backEnd.GameEngine.Component;

/**
 * This class provides functionality for how user interacts with the Front end
 * 
 * Essentially a container for all the possible interactions from the user
 * 
 * @author Derek
 *
 */
public abstract class Mode {

	
	protected Model myModel;
	public Mode(Model myModel) {
		this.myModel = myModel;
	}
	
	/**
	 * adds structure to StructureGrid
	 */
	public abstract void addComponent(Component component, int[] location);
	
	/**
	 * removes structure from StructureGrid
	 */
	public abstract void removeComponent(Component component);
	
	public abstract void moveComponent();
	
	public abstract void changeTile();
	
	public abstract void setComponentBehavior();
	
	public abstract void setGameRule();
	
	public abstract void onComponentClick();
	
	

}