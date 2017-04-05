package backEnd.Mode;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.Component;
import javafx.geometry.Point2D;

/**
 * This class provides functionality for how user interacts with the Front end
 * 
 * @author Derek
 *
 */
public abstract class Mode {

	protected Model myModel;

	public Mode(Model myModel) {
		this.myModel = myModel;
	}

	public abstract void addComponent(Component component, Point2D location);

	public abstract void removeComponent(Component component);

	public void addComponentBehavior(Component component, Behavior newBehavior){
		component.addBehavior(newBehavior);
	}

	public void removeComponentBehavior(Component component, Behavior behavior){
		component.removeBehavior(behavior);
	}

	public void addComponentAttribute(Component component, Attribute newAttribute){
		component.addAttribute(newAttribute);
	}

	public void removeComponentAttribute(Component component, Attribute attribute){
		component.removeAttribute(attribute);
	}

	public abstract void addTile() throws ModeException;

	public abstract void addTileAttribute() throws ModeException;

	public abstract void removeTileAttribute() throws ModeException;

	public void moveComponent(Component component, Point2D newLoc){
		myModel.getState().getComponentGraph().moveComponent(component, newLoc);
	}

	public abstract void moveTile() throws ModeException;

	public abstract void editRules() throws ModeException;

	public Collection<Rules> viewRules(){
		return myModel.getRules();
	}

	public abstract void play() throws ModeException;
	
	public abstract void pause();
	
	public void changeMode(){
		myModel.changeMode();
	}
}