package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import backEnd.GameEngine.Attribute;
import backEnd.Mode.UserModeType;
import javafx.geometry.Point2D;

public class StateImpl implements State{
	
	private int gridWidth;
	private int gridHeight;
	private int pointResWidth;
	private int pointResHeight;
	private TileGrid tileGrid;
	private ComponentGraph componentGraph;
	private HashMap<Attribute<String>, String> imageMap; //attribute is name of component, mapping to imagefile path
	
	public StateImpl(int gridWidth, int gridHeight, int pointResolution_Width, int pointResolution_Height){
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.pointResWidth = pointResolution_Width;
		this.pointResHeight = pointResolution_Height;
		setDefaultTileGrid(gridWidth, gridHeight, pointResolution_Width, pointResolution_Height);
		componentGraph = new ComponentGraphImpl(gridWidth, gridHeight, pointResolution_Height, pointResolution_Height);
		imageMap = new HashMap<Attribute<String>, String>();
	}

	private void setDefaultTileGrid(int gridWidth, int gridHeight, int pointResolution_Width, int pointResolution_Height) {
		tileGrid = new TileGridImpl(gridWidth, gridHeight);
		for (int i = 0; i < gridHeight; i++){
			for (int j = 0; j < gridWidth; j++){
				Point2D loc = new Point2D(i,j);
				List<TileAttributeImpl<?>> attrList = new ArrayList<TileAttributeImpl<?>>();
				attrList.add(new TileAttributeImpl<String>(TileAttributeType.IMAGEFILE, "idk where tf it is"));
				Tile newTile = new TileImpl(attrList, new AccessPermissionsImpl(Arrays.asList(), Arrays.asList(UserModeType.AUTHOR)), loc);
				tileGrid.setTile(newTile, loc);
			}
		}
	}
	
	@Override
	public TileGrid getTileGrid(){
		return tileGrid;
	}
	
	@Override
	public ComponentGraph getComponentGraph(){
		return componentGraph;
	}
	
	@Override
	public void addToImageMap(Attribute<String> componentName, String filePath){
		imageMap.put(componentName, filePath);
	}
	
	@Override
	public String getFilePathFromMap(Attribute<String> componentName){
		return imageMap.get(componentName);
	}

}