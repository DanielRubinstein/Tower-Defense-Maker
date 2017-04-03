package backEnd;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.geom.Point2D;

import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;

/**
 * This is the Grid class that contains the Component Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class ComponentGrid {
	private int gridWidth;
	private int gridHeight;
	private ArrayList<Component>[][] componentGrid;
	
	public ComponentGrid(int width, int height){
		gridWidth = width;
		gridHeight = height;
	}
	
	/**
	 * Get the list of components at a given location
	 * @param location
	 * @return Tile at the given location
	 */
	public List<Component> getComponentsByLocation(Point2D location){
		return componentGrid[(int) location.x][(int) location.y];
	}
	
	/**
	 * Add a component to the list of components at a given location
	 * @param newComponent
	 * @param location
	 */
	public void addComponentToGrid(Component newComponent, Point2D location){
		componentGrid[(int) location.x][(int) location.y].add(newComponent);
	}
	
	/**
	 * Remove a component from the grid
	 * @param toRemove
	 */
	public void removeComponent(Component toRemove){
		Attribute<?> positionAttribute = toRemove.getAttribute("Position");
		Point2D location = (Point2D) positionAttribute.getValue(currentVal);//what value needs to go into this?
		componentGrid[(int) location.x][(int) location.y].remove(toRemove);
	}

}
