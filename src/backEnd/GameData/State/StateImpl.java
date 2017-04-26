package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;

import ModificationFromUser.AttributeOwner.Modification_EditAttribute;
import backEnd.Coord;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameEngine.EngineStatus;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;
import javafx.geometry.Point2D;

/**
 * 
 * @author Alex Salas, Christian Martindale
 *
 */
public class StateImpl implements State, SerializableObservable {

	private int numColsInGrid;
	private int numRowsInGrid;
	private TileGrid myTileGrid;
	private ComponentGraph myComponentGraph;
	private final static String RESOURCES_PATH = "resources/defaultTileAttributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private final static String IMAGEPATH_RESOURCES_PATH = "resources/images";
	private final static ResourceBundle myImageResource = ResourceBundle.getBundle(IMAGEPATH_RESOURCES_PATH);
	private EngineStatus myEngineStatus;
	private Map<String, SpawnQueue> mySpawnQueues;
	private List<SerializableObserver> observers;
	
	public StateImpl(int numColsInGrid, int numRowsInGrid) throws FileNotFoundException {
		this(numColsInGrid, numRowsInGrid, setDefaultTileGrid(numColsInGrid, numRowsInGrid), new ComponentGraphImpl());
	}
	
	public StateImpl(int numRowsInGrid, int numColsInGrid, TileGrid tileGrid, ComponentGraph componentGraph) throws FileNotFoundException {
		this.numColsInGrid = numColsInGrid;
		this.numRowsInGrid = numRowsInGrid;
		myTileGrid = tileGrid;
		myComponentGraph = componentGraph;
		myEngineStatus = EngineStatus.PAUSED;
		mySpawnQueues = new HashMap<String,SpawnQueue>();
		observers = new ArrayList<SerializableObserver>();
	}


	private static TileGrid setDefaultTileGrid(int cols, int rows) throws FileNotFoundException {
		TileGrid tileGrid = new TileGridImpl(cols, rows);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Point2D loc = new Point2D(col, row);
				
				Tile newTile = new TileImpl(Arrays.asList(), Arrays.asList(), Arrays.asList(), loc);
				
				Attribute<String> imgAttr = newTile.getAttribute("ImageFile");
				imgAttr.setValue(myImageResource.getString("default_tile"));
				
				tileGrid.setTileByGridPosition(newTile, col, row);

			}
		}
		return tileGrid;
	}
	
	public void addAsObserver(SerializableObserver o){
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
		for (int col = 0; col < numColsInGrid; col++) { // find the start position
			for (int row = 0; row < numRowsInGrid; row++) {
				Tile tile = myTileGrid.getTileByGridPosition(col, row);
				if (tile.getMyAttributes()
						.<Boolean>get(myResources.getString("StartTile")).getValue() == true) {
					startTiles.put(tile, new Coord(col, row, null));
				}
			}
		}
		return startTiles;
	}
	
	public void updateState(State state){
		this.numColsInGrid = state.getGridWidth();
		this.numRowsInGrid = state.getGridHeight();
		replaceTiles(state.getTileGrid());
		replaceComponents(state.getComponentGraph());
		this.myEngineStatus = state.getEngineStatus();
		notifyObservers();
	}

	private void replaceComponents(ComponentGraph componentGraph)
	{
		myComponentGraph.clearComponents();
		for (Point2D x : componentGraph.getComponentMap().keySet())
		{
			for (Component y : componentGraph.getComponentMap().get(x))
			{
				myComponentGraph.addComponentToGrid(y, x);
			}
		}
	}

	private void replaceTiles(TileGrid tileGrid)
	{
		
		myTileGrid.setNumCols(tileGrid.getNumColsInGrid());
		myTileGrid.setNumRows(tileGrid.getNumRowsInGrid());
		
		for (int x = 0; x < tileGrid.getNumRowsInGrid(); x++)
		{
			for (int y = 0; y < tileGrid.getNumColsInGrid(); y++)
			{
				myTileGrid.setTileByGridPosition(tileGrid.getTileByGridPosition(y, x), y, x);
			}
		}
		System.out.println(myTileGrid.getTileByGridPosition(0, 0).getAttribute("ImageFile").getValue() + " GGG");
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
		if(isTraversable(current.getXCoord()+1, current.getYCoord()  )){
			adjacents.add(new Coord(current.getXCoord()+1, current.getYCoord(), current));
		}
		if(isTraversable(current.getXCoord()-1, current.getYCoord()  )){
			adjacents.add(new Coord(current.getXCoord()-1, current.getYCoord(), current));
		}
		if(isTraversable(current.getXCoord()  , current.getYCoord()+1)){
			adjacents.add(new Coord(current.getXCoord(), current.getYCoord()+1, current));
		}
		if(isTraversable(current.getXCoord()  , current.getYCoord()-1)){
			adjacents.add(new Coord(current.getXCoord(), current.getYCoord()-1, current));
		}
		return adjacents;
	}

	public EngineStatus getEngineStatus()
	{
		return myEngineStatus;
	}
	
	private boolean isTraversable(int x, int y){
		Tile curTile = myTileGrid.getTileByGridPosition(x,y);
		if(curTile == null || curTile.<Boolean>getAttribute(myResources.getString("Traversable")).getValue() == true){
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
	
	public boolean gameIsRunning(){
		return myEngineStatus.toString().equals("RUNNING");
	}

	@Override
	public Collection<Component> getComponentsByTileGridPosition(Point2D tileGridPosition) {
		TileCorners tileCorners = new TileCorners(tileGridPosition, myTileGrid.getTileWidth(), myTileGrid.getTileHeight());
		return myComponentGraph.getComponentsByTileCorners(tileCorners);
	}

	public void setEngineStatus(EngineStatus engineStatus) {
		myEngineStatus=engineStatus;
		notifyObservers();
	}


	@Override
	public Map<String, SpawnQueue> getSpawnQueues() {
		return mySpawnQueues;
	}
	
	private void notifyObservers() {
		for (SerializableObserver o : observers){
			o.update(this, null);
		}
	}

	@Override
	public void setComponentGraph(ComponentGraph componentGraph) {
		myComponentGraph = componentGraph;
	}

	@Override
	public void addObserver(SerializableObserver o) {
		observers.add(o);
	}

	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers = null;
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}
}