package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import backEnd.GameEngine.EngineStatus;
import backEnd.GameEngine.Engine.Spawning.SpawnQueueInstantiator;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

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
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();

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
				Double tileWidth = numericResourceBundle.getScreenConstants().getScreenGridWidth() / numColsInGrid;
				Double tileHeight = numericResourceBundle.getScreenConstants().getScreenGridHeight() / numRowsInGrid;
				Point2D pos = new Point2D((col + 0.5) * (tileWidth), (row + 0.5) * (tileHeight)); //0.5: to set at center
				Tile newTile = new TileImpl();
				newTile.getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).setValue(pos);
				newTile.getAttribute(STRING_RESOURCES.getFromAttributeNames("ImageFile")).setValue(myImageResource.getString("default_tile"));
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
			Point2D pos = component.<Point2D>getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
			myComponentGraph.addComponentToGrid(component, pos);
		}
	}

	private void replaceTiles(TileGrid tileGrid){
		
		myTileGrid.setNumCols(tileGrid.getNumColsInGrid());
		myTileGrid.setNumRows(tileGrid.getNumRowsInGrid());

		for(Tile tile : tileGrid.getAllTiles()){
			Point2D pos = tile.<Point2D>getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
			myTileGrid.setTileByScreenPosition(tile, pos);
		}
	}


	public EngineStatus getEngineStatus()
	{
		return myEngineStatus;
	}
	
	@Override
	public int getGridWidth() {
		return numColsInGrid;
	}

	@Override
	public int getGridHeight() {
		return numRowsInGrid;
	}
	
	public boolean gameIsRunning(){
		return myEngineStatus.toString().equals(STRING_RESOURCES.getFromStringConstants("RUNNING"));
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
		return mySpawnQueues;
	}
	
	public Map<String, SpawnQueueInstantiator> getSpawnQueueInstantiators()
	{
		Map<String, SpawnQueueInstantiator> map = new HashMap<String, SpawnQueueInstantiator>();
		
		for (String x : mySpawnQueues.keySet())
		{
			map.put(x, mySpawnQueues.get(x).getInstantiator());
		}
		
		return map;
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