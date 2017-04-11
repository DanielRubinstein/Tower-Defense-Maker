package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;

import backEnd.Coord;
import backEnd.Attribute.AttributeImpl;
import backEnd.Mode.UserModeType;
import javafx.geometry.Point2D;

/**
 * 
 * @author Alex Salas, Christian Martindale
 *
 */
public class StateImpl implements State {

	private int gridWidth;
	private int gridHeight;
	private int pointResWidth;
	private int pointResHeight;
	private TileGrid stateGrid;
	private ComponentGraph componentGraph;
	private final static String RESOURCES_PATH = "resources/attributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);

	public StateImpl(int gridWidth, int gridHeight, int pointResolution_Width, int pointResolution_Height) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.pointResWidth = pointResolution_Width;
		this.pointResHeight = pointResolution_Height;
		setDefaultTileGrid(gridWidth, gridHeight, pointResolution_Width, pointResolution_Height);
		componentGraph = new ComponentGraphImpl(gridWidth, gridHeight, pointResolution_Height, pointResolution_Height);
	}

	private void setDefaultTileGrid(int gridWidth, int gridHeight, int pointResolution_Width,
			int pointResolution_Height) {
		stateGrid = new TileGridImpl(gridWidth, gridHeight);
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridWidth; j++) {
				Point2D loc = new Point2D(i, j);
				Tile newTile = new TileImpl(Arrays.asList(), Arrays.asList(UserModeType.AUTHOR), loc);
				stateGrid.setTile(newTile, loc);
			}
		}
	}

	@Override
	public TileGrid getTileGrid() {
		return stateGrid;
	}

	@Override
	public ComponentGraph getComponentGraph() {
		return componentGraph;
	}

	private Map<Tile, Coord> findStartTiles() {
		Map<Tile, Coord> startTiles = new HashMap<Tile, Coord>();
		for (int i = 0; i < gridWidth; i++) { // find the start position
			for (int j = 0; j < gridHeight; j++) {
				if ((boolean) stateGrid.getTileByCoord(i, j).getMyAttributes().getAttributeMap()
						.get(myResources.getString("StartTile")).getValue() == true) {
					startTiles.put(stateGrid.getTileByCoord(i, j), new Coord(i, j, null));
				}

			}
		}
		return startTiles;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void formShortestPath(){
		Map<Tile, Coord> startTiles = findStartTiles();
		
		for(Tile current : startTiles.keySet()){
			Queue <Coord> path = new LinkedList<Coord>();
			Set<Coord> visitedTiles = new HashSet<Coord>();
			path.add(startTiles.get(current));
			visitedTiles.add(startTiles.get(current));
			Coord finalPath = null;
			while(!path.isEmpty()){
				Coord cur = path.remove();
				ArrayList<Coord> adjacentTiles = getAdjacents(cur);
				adjacentTiles.removeAll(visitedTiles); 
				path.addAll(adjacentTiles);
				if((boolean) stateGrid.getTileByCoord(cur.getXCoord(), cur.getYCoord()).getAttribute(myResources.getString("GoalTile")).getValue() == true){
					finalPath = cur;
					break; //DONE
				}
			}
			if(finalPath == null){
				//No good path found smh lmao
			}
			Coord pathStep = finalPath;
			Stack<Coord> pathStack = new Stack<Coord>();
			while(pathStep != null){
				pathStack.add(pathStep);
				pathStep = pathStep.getLastCoord();
			}
			Coord currentPathCoords = null;
			Coord nextPathCoords = null;
			while(!(pathStack.size()==1)){
				currentPathCoords = nextPathCoords;
				nextPathCoords = pathStack.pop();
				if(currentPathCoords == null){continue;}
				Tile currentPathTile = stateGrid.getTileByCoord(currentPathCoords.getXCoord(),currentPathCoords.getYCoord());
				MoveDirections pathDirection = null;

				if(currentPathCoords.getXCoord()-nextPathCoords.getXCoord()!=-1){
					pathDirection = MoveDirections.RIGHT;
				}
				if(currentPathCoords.getXCoord()-nextPathCoords.getXCoord()!= 1){
					pathDirection = MoveDirections.LEFT;
				}
				if(currentPathCoords.getYCoord()-nextPathCoords.getYCoord()!=-1){
					pathDirection = MoveDirections.DOWN;
				}
				if(currentPathCoords.getYCoord()-nextPathCoords.getYCoord()!= 1){
					pathDirection = MoveDirections.UP;
				}
				((AttributeImpl<MoveDirections>) currentPathTile.getAttribute("MoveDirection")).setValue(pathDirection);
			}
		}
	}

	private ArrayList<Coord> getAdjacents(Coord current) {
		ArrayList<Coord> adjacents = new ArrayList<Coord>();
		if(isPassable(current.getXCoord()+1, current.getYCoord()  )){
			adjacents.add(new Coord(current.getXCoord()+1, current.getYCoord(), current));
		}
		if(isPassable(current.getXCoord()-1, current.getYCoord()  )){
			adjacents.add(new Coord(current.getXCoord()-1, current.getYCoord(), current));
		}
		if(isPassable(current.getXCoord()  , current.getYCoord()+1)){
			adjacents.add(new Coord(current.getXCoord(), current.getYCoord()+1, current));
		}
		if(isPassable(current.getXCoord()  , current.getYCoord()-1)){
			adjacents.add(new Coord(current.getXCoord(), current.getYCoord()-1, current));
		}
		return adjacents;
	}

	private boolean isPassable(int x, int y){
		Tile curTile = stateGrid.getTileByCoord(x,y);
		if(curTile == null || (boolean)curTile.getAttribute(myResources.getString("Traversable")).getValue() == true){
			return false;
		}
		
		return true;
	}
	
	@Override
	public void calculateShortestPath() {
		// TODO Auto-generated method stub

	}

}