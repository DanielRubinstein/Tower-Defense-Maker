package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import backEnd.GameEngine.EngineStatus;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import javafx.geometry.Point2D;
import resources.constants.NumericResourceBundle;

/**
 * 
 * @author Alex Salas, Christian Martindale
 *
 */
public class StateImpl implements State, SerializableObservable {
	
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	
	private int numColsInGrid;
	private int numRowsInGrid;
	private TileGrid myTileGrid;
	private ComponentGraph myComponentGraph;
	private final static String RESOURCES_PATH = "resources/defaultTileAttributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private final static String IMAGEPATH_RESOURCES_PATH = "resources/images";
	private final static ResourceBundle myImageResource = ResourceBundle.getBundle(IMAGEPATH_RESOURCES_PATH);
	private EngineStatus myEngineStatus;
	private Map<String, SpawnQueues> mySpawnQueues;
	private List<SerializableObserver> observers;
	
	
	
public StateImpl(TileGrid tileGrid, ComponentGraph componentGraph, HashMap<String, SpawnQueues> spawn) throws FileNotFoundException {
		
		this.numColsInGrid = tileGrid.getNumColsInGrid();
		this.numRowsInGrid = tileGrid.getNumRowsInGrid();

		myTileGrid = tileGrid;
		myComponentGraph = componentGraph;
		myEngineStatus = EngineStatus.PAUSED;
		mySpawnQueues = spawn;
		observers = new ArrayList<SerializableObserver>();
	}

	public StateImpl(int numColsInGrid, int numRowsInGrid) throws FileNotFoundException {
		this(new TileGridImpl(numColsInGrid, numRowsInGrid), new ComponentGraphImpl(), new HashMap<String, SpawnQueues>());
		
		setDefaultTileGrid();
	}
	


	private void setDefaultTileGrid() throws FileNotFoundException {
		for (int row = 0; row < numRowsInGrid; row++) {
			for (int col = 0; col < numColsInGrid; col++) {
				Double tileWidth = numericResourceBundle.getScreenGridWidth() / numColsInGrid;
				Double tileHeight = numericResourceBundle.getScreenGridHeight() / numRowsInGrid;
				Point2D pos = new Point2D((col + 0.5) * (tileWidth), (row + 0.5) * (tileHeight));
				Tile newTile = new TileImpl();
				newTile.getAttribute("Position").setValue(pos);
				newTile.getAttribute("ImageFile").setValue(myImageResource.getString("default_tile"));
				myTileGrid.setTileByScreenPosition(newTile, pos);
			}
		}
	
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
		
		for(Component component : componentGraph.getAllComponents()){
			Point2D pos = component.<Point2D>getAttribute("Position").getValue();
			myComponentGraph.addComponentToGrid(component, pos);
		}
	}

	private void replaceTiles(TileGrid tileGrid){
		
		myTileGrid.setNumCols(tileGrid.getNumColsInGrid());
		myTileGrid.setNumRows(tileGrid.getNumRowsInGrid());

		for(Tile tile : tileGrid.getAllTiles()){
			Point2D pos = tile.<Point2D>getAttribute("Position").getValue();
			myTileGrid.setTileByScreenPosition(tile, pos);
		}
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
	/*
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
	*/

	public EngineStatus getEngineStatus()
	{
		return myEngineStatus;
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
	public Collection<Component> getComponentsByTilePosition(Point2D tileGridPosition) {
		TileCorners tileCorners = new TileCorners(tileGridPosition, myTileGrid.getTileWidth(), myTileGrid.getTileHeight());
		return myComponentGraph.getComponentsByTileCorners(tileCorners);
	}

	public void setEngineStatus(EngineStatus engineStatus) {
		myEngineStatus=engineStatus;
		notifyObservers();
	}


	@Override
	public Map<String, SpawnQueues> getSpawnQueues() {
		//System.out.println(mySpawnQueues);
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

	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
}