package data;

import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.PlayerStatus;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import backEnd.GameData.State.Component;

public class ComponentGraphCleaner {
	
	private ComponentGraph graph;
	private List<Component> removedComponents = new ArrayList<Component>();
	
	public ComponentGraphCleaner(ComponentGraph graph){
		this.graph = graph;
	}
	
	public void stripNonsavingComponents(){
		for(Component myComp : graph.getAllComponents()){
			if(!myComp.<Boolean>getAttribute("SaveToTemplate").getValue()){
				removedComponents.add(myComp);
				graph.removeComponent(myComp);
			}
		}
	}

	public void addBackNonsavingComponents(){
		for(Component comp : removedComponents){
			graph.addComponentToGrid(comp, comp.<Point2D>getAttribute("Position").getValue());
		}
	}

}
