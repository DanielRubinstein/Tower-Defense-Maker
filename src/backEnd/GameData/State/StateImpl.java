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
	
	public StateImpl(int numColsInGrid, int numRowsInGrid) throws FileNotFoundException {
		this.numColsInGrid = numColsInGrid;
		this.numRowsInGrid = numRowsInGrid;
		initialize(setDefaultTileGrid(numColsInGrid, numRowsInGrid), new ComponentGraphImpl());
	}
	
	public StateImpl(int numRowsInGrid, int numColsInGrid, TileGrid tileGrid, ComponentGraph componentGraph) throws FileNotFoundException {
		this.numColsInGrid = numColsInGrid;
		this.numRowsInGrid = numRowsInGrid;
		initialize(tileGrid, componentGraph);
	}
	
	private void initialize(TileGrid tileGrid, ComponentGraph componentGraph){
		myTileGrid = tileGrid;
		myComponentGraph = componentGraph;
		myEngineStatus = EngineStatus.PAUSED;
		mySpawnQueues = new HashMap<String,SpawnQueues>();
		observers = new ArrayList<SerializableObserver>();
	}


	private TileGrid setDefaultTileGrid(int cols, int rows) throws FileNotFoundException {
		TileGrid tileGrid = new TileGridImpl(cols, rows);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Double tileWidth = numericResourceBundle.getScreenGridWidth() / cols;
				Double tileHeight = numericResourceBundle.getScreenGridHeight() / rows;
				Point2D pos = new Point2D((col + 0.5) * (tileWidth), (row + 0.5) * (tileHeight));
				Tile newTile = new TileImpl();
				newTile.getAttribute("Position").setValue(pos);
				newTile.getAttribute("ImageFile").setValue(myImageResource.getString("default_tile"));
				tileGrid.setTileByScreenPosition(newTile, pos);
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
}