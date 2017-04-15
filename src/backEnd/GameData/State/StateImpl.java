package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;

import ModificationFromUser.Modification_EditAttribute;
import backEnd.Coord;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;
import backEnd.Mode.UserModeType;
import javafx.geometry.Point2D;

/**
 * 
 * @author Alex Salas, Christian Martindale
 *
 */
public class StateImpl extends Observable implements State {

	private int numColsInGrid;
	private int numRowsInGrid;
	private TileGrid myTileGrid;
	private ComponentGraph myComponentGraph;
	private final static String RESOURCES_PATH = "resources/defaultTileAttributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private final static String IMAGEPATH_RESOURCES_PATH = "resources/images";
	private final static ResourceBundle myImageResource = ResourceBundle.getBundle(IMAGEPATH_RESOURCES_PATH);

	public StateImpl(int numColsInGrid, int numRowsInGrid) throws FileNotFoundException {
		this.numColsInGrid = numColsInGrid;
		this.numRowsInGrid = numRowsInGrid;
		setDefaultTileGrid();
		myComponentGraph = new ComponentGraphImpl();
	}


	private void setDefaultTileGrid() throws FileNotFoundException {
		myTileGrid = new TileGridImpl(numColsInGrid, numRowsInGrid);
		for (int row = 0; row < numRowsInGrid; row++) {
			for (int col = 0; col < numColsInGrid; col++) {
				Point2D loc = new Point2D(col, row);
				Tile newTile = new TileImpl(Arrays.asList(), Arrays.asList(UserModeType.AUTHOR), loc);
				Attribute<String> imgAttr = (Attribute<String>) newTile.getAttribute("ImageFile");
				imgAttr.setValue(myImageResource.getString("default_tile"));
				myTileGrid.setTile(newTile, loc);

			}
		}
	}
	
	public void addAsObserver(Observer o){
		this.addObserver(o);
	}

	@Override
	public TileGrid getTileGrid() {
		return myTileGrid;
	}

	@Override
	public ComponentGraph getComponentGraph() {
		return myComponentGraph;
	}

	private Map<Tile, Coord> findStartTiles() {
		Map<Tile, Coord> startTiles = new HashMap<Tile, Coord>();
		for (int i = 0; i < numColsInGrid; i++) { // find the start position
			for (int j = 0; j < numRowsInGrid; j++) {
				if ((boolean) myTileGrid.getTileByCoord(i, j).getMyAttributes().getAttributeMap()
						.get(myResources.getString("StartTile")).getValue() == true) {
					startTiles.put(myTileGrid.getTileByCoord(i, j), new Coord(i, j, null));
				}
			}
		}
		return startTiles;
	}
	
	public void updateState(State state){
		this.numColsInGrid = state.getGridWidth();
		this.numRowsInGrid = state.getGridHeight();
		this.myTileGrid = state.getTileGrid();
		this.myComponentGraph = state.getComponentGraph();
		this.setChanged();
		this.notifyObservers();

	}

	/*
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
				String pathDirection = null;

				if(currentPathCoords.getXCoord()-nextPathCoords.getXCoord()!=-1){
					pathDirection = "Right";
				}
				if(currentPathCoords.getXCoord()-nextPathCoords.getXCoord()!= 1){
					pathDirection = "Left";
				}
				if(currentPathCoords.getYCoord()-nextPathCoords.getYCoord()!=-1){
					pathDirection = "Down";
				}
				if(currentPathCoords.getYCoord()-nextPathCoords.getYCoord()!= 1){
					pathDirection = "Up";
				}
				((AttributeImpl<String>) currentPathTile.getAttribute("MoveDirection")).setValue(pathDirection);
			}
		}
	}
	*/

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
		Tile curTile = myTileGrid.getTileByCoord(x,y);
		if(curTile == null || (boolean)curTile.getAttribute(myResources.getString("Traversable")).getValue() == true){
			return false;
		}
		
		return true;
	}
	
	/*
	@Override
	public void calculateShortestPath() {
		// TODO Auto-generated method stub

	}
	*/
	
	@Override
	public int getGridWidth() {
		return numColsInGrid;
	}

	@Override
	public int getGridHeight() {
		return numRowsInGrid;
	}


	@Override
	public Collection<Component> getComponentsByTileGridPosition(Point2D tileGridPosition) {
		TileCorners tileCorners = new TileCorners(tileGridPosition, myTileGrid.getTileWidth(), myTileGrid.getTileHeight());
		return myComponentGraph.getComponentsByTileCorners(tileCorners);
	}


	

}