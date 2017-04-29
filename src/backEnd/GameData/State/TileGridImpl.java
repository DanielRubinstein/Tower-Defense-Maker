package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;
import resources.constants.NumericResourceBundle;

/**
 * This is the Grid class that contains the Tile Grid and all of the relevant
 * getters/setters for other modules to use to access the Grid
 * 
 * @author Riley Nisbet
 *
 */

public class TileGridImpl implements TileGrid {
	
	private List<SerializableObserver> observers;

	private int numColsInGrid;
	private int numRowsInGrid;
	private Map<Point2D, Tile> tileGrid;
	private List<Tile> tileList;
	private double tileWidth;
	private double tileHeight;
	private double tileCenterFactor; //ratio of the tile that we consider part of the center of the tile
	private List<List<SerializableObserver>> tileObserverList;
	private static final String BUNDLE_NAME = "resources.constants.numericResourceBundle";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public TileGridImpl(int colsInGrid, int rowsInGrid) {

		observers = new ArrayList<SerializableObserver>();
		numColsInGrid = colsInGrid;
		numRowsInGrid = rowsInGrid;
		tileGrid = new HashMap<>();
		tileCenterFactor=Double.valueOf(RESOURCE_BUNDLE.getString("TileCenterFactor"));

	}

	public TileGridImpl(TileGridInstantiator i) {
		observers = new ArrayList<SerializableObserver>();
		numColsInGrid = i.getNumCols();
		numRowsInGrid = i.getNumRows();
		tileGrid = i.getTileGrid();
		tileCenterFactor=Double.valueOf(RESOURCE_BUNDLE.getString("TileCenterFactor"));
	}

	public TileGridInstantiator getInstantiator() {
		return new TileGridInstantiator(numRowsInGrid, numColsInGrid, tileGrid);
	}

	@Override
	public void setTileSize(double tileWidth, double tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	/*
	 * These middle of tile methods need a lot of refactoring; I'm on it. 
	 * Just wanted to push now to get functionality working.
	 */
	
	
	private Tile generateLeftTile(Point2D screenPosition){
		return getTileByScreenPosition(
				 new Point2D(screenPosition.getX() - tileWidth * tileCenterFactor,
				 screenPosition.getY()));
	}
	
	private Tile generateRightTile(Point2D screenPosition){
		return getTileByScreenPosition(
				 new Point2D(screenPosition.getX() + tileWidth * tileCenterFactor,
				 screenPosition.getY()));
	}
	
	private Tile generateTopTile(Point2D screenPosition){
		return getTileByScreenPosition(
				 new Point2D(screenPosition.getX(),
				 screenPosition.getY() - tileHeight * tileCenterFactor));
	}
	
	private Tile generateBottomTile(Point2D screenPosition){
		return getTileByScreenPosition(
				 new Point2D(screenPosition.getX(),
				 screenPosition.getY() + tileHeight * tileCenterFactor));
	}
	
	
	
		public boolean atMiddleXOfTile(Point2D screenPosition){
			 return (generateLeftTile(screenPosition).equals(getTileByScreenPosition(screenPosition)) && getTileByScreenPosition(screenPosition).equals(generateRightTile(screenPosition)));
		}
		
		
		public boolean atMiddleYOfTile(Point2D screenPosition){
			 return (generateBottomTile(screenPosition).equals(getTileByScreenPosition(screenPosition)) && getTileByScreenPosition(screenPosition).equals(generateTopTile(screenPosition)));
		}


	@Override
	public Tile getTileByScreenPosition(Point2D screenPosition) {
		return tileGrid.get(this.getSnapPosition(screenPosition));
	}

	@Override
	public void setTileByScreenPosition(Tile newTile, Point2D posOfNewTile) {
		if(tileHeight != 0.0 && tileWidth != 0.0){
			posOfNewTile = this.getSnapPosition(posOfNewTile);
			newTile.getAttribute("Position").setValue(posOfNewTile);
		}
		Boolean initialization = false;
		if (!tileGrid.containsKey(posOfNewTile)) {
			initialization = true;
			tileGrid.put(posOfNewTile, newTile);
		}

		if (!initialization) {
			// do not notify ScreenGrid for each initial Tile, only if changed
			// after initialization
			newTile.setObserverList(tileGrid.get(posOfNewTile).getAndClearObservers());
			tileGrid.put(posOfNewTile, newTile);
			notifyObservers(newTile);
		}
	}
	
	private Point2D getSnapPosition(Point2D screenPosition) {
		int column = (int) Math.floor(screenPosition.getX() / tileWidth);
		int row = (int) Math.floor(screenPosition.getY() / tileHeight);
		return new Point2D((column + 0.5) * (tileWidth), (row + 0.5) * (tileHeight));
	}
	

	@Override
	public int getNumColsInGrid() {
		return numColsInGrid;
	}

	@Override
	public int getNumRowsInGrid() {
		return numRowsInGrid;
	}

	public Collection<Tile> getAllTiles() {
		return tileGrid.values();
	}

	@Override
	public double getTileWidth() {
		return this.tileWidth;
	}

	@Override
	public double getTileHeight() {
		return this.tileHeight;
	}

	@Override
	public void addObserver(SerializableObserver o) {
		observers.add(o);
	}
	private void notifyObservers(){
		notifyObservers(null);
	}
	
	private void notifyObservers(Object arg){
		for (SerializableObserver o : observers){
			o.update(null, arg);
		}
	}
	
	
	public void saveAndClearTileObservers()
	{
		tileObserverList = new ArrayList<List<SerializableObserver>>();
		
		for (int i = 0; i < getAllTiles().size(); i++)
		{
			tileObserverList.add(tileList.get(i).getAndClearObservers());
		}

	}

	public void setTileObservers() {
		for (int i = 0; i < tileList.size(); i++) {
			tileList.get(i).setObserverList(tileObserverList.get(i));
		}
	}

	@Override
	public boolean contains(AttributeOwnerReader newAttrOwn) {
		return tileGrid.containsValue(newAttrOwn);
	}

	@Override
	public Object getMap() {

		return tileGrid;
	}

	@Override
	public void setNumCols(int numColsInGrid) {
		this.numColsInGrid = numColsInGrid;
		notifyObservers();
	}

	@Override
	public void setNumRows(int numRowsInGrid) {
		this.numRowsInGrid = numRowsInGrid;
		notifyObservers();
	}

	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers.clear();
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}
	
	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
	
	@Override
	public Collection<AttributeOwner> getAllAttributeOwners() {
		Collection<AttributeOwner> myAOs = new ArrayList<AttributeOwner>();
		for(AttributeOwner ao : tileList){
			myAOs.add(ao);
		}
		return myAOs;
	}
	
}
