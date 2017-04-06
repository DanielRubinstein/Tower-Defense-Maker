package backEnd.State;

import java.util.HashMap;

import backEnd.State.ComponentGraph;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;

public class StateImpl implements State{
	
	private TileGrid tileGrid;
	private ComponentGraph componentGraph;
	private HashMap<Attribute<String>, String> imageMap; //attribute is name of component, mapping to imagefile path
	
	public TileGrid getTileGrid(){
		return tileGrid;
	}
	
	public ComponentGraph getComponentGraph(){
		return componentGraph;
	}
	
	public void addToImageMap(Attribute<String> componentName, String filePath){
		imageMap.put(componentName, filePath);
	}
	
	public String getFilePathFromMap(Attribute<String> componentName){
		return imageMap.get(componentName);
	}

}
