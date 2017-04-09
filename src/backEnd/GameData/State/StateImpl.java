package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import backEnd.GameEngine.Attribute;
import javafx.geometry.Point2D;

public class StateImpl implements State{
	
	private int gridWidth;
	private int gridHeight;
	private TileGrid tileGrid;
	private ComponentGraph componentGraph;
	private HashMap<Attribute<String>, String> imageMap; //attribute is name of component, mapping to imagefile path
	
	public StateImpl(int gridWidth, int gridHeight, int pointResolution_Width, int pointResolution_Height){
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		setDefaultTileGrid(gridWidth, gridHeight, pointResolution_Width, pointResolution_Height);
		componentGraph = new ComponentGraph(gridWidth, gridHeight);
		imageMap = new HashMap<Attribute<String>, String>();
	}

	private void setDefaultTileGrid(int gridWidth, int gridHeight, int pointResolution_Width, int pointResolution_Height) {
		tileGrid = new TileGrid(gridWidth, gridHeight);
		for (int i = 0; i < gridHeight; i++){
			for (int j = 0; j < gridWidth; j++){
				Point2D loc = new Point2D(i,j);
				List<TileAttribute<?>> attrList = new ArrayList<TileAttribute<?>>();
				attrList.add(new TileAttribute<String>(TileAttributeType.IMAGEFILE, "idk where tf it is"));
				List<AccessParty> approvedAccessPartyList = new ArrayList<AccessParty>();
				approvedAccessPartyList.add(AccessParty.AUTHOR_MODE);
				Tile newTile = new TileImpl(attrList, approvedAccessPartyList, loc);
				tileGrid.setTile(newTile, loc);
			}
		}
	}
	
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