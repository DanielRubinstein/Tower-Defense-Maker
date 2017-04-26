package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;

/**
 * This is the Grid class that contains the Tile Grid and all of the relevant getters/setters for other modules to use to access
 * the Grid
 * @author Riley Nisbet
 *
 */

public class TileGridImpl extends Observable implements TileGrid {
	
	private List<Observer> observers;
	
	private int numColsInGrid;
	private int numRowsInGrid;
	private Map<Point2D, Tile> tileGrid;
	private List<Tile> tileList;
	private double tileWidth;
	private double tileHeight;
	private List<List<Observer>> tileObserverList;
	
	public TileGridImpl(int colsInGrid, int rowsInGrid){
		
		observers = new ArrayList<Observer>();
		numColsInGrid = colsInGrid;
		numRowsInGrid = rowsInGrid;
		tileGrid = new HashMap<>();
	}
	
	public TileGridImpl(TileGridInstantiator i)
	{
		observers = new ArrayList<Observer>();
		
		numColsInGrid = i.getNumCols();
		numRowsInGrid = i.getNumRows();
		tileGrid = i.getTileGrid();
	}
	
	public TileGridInstantiator getInstantiator()
	{
		return new TileGridInstantiator(numRowsInGrid, numColsInGrid, tileGrid);
	}
	
	@Override
	public void setTileSize(double tileWidth, double tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	@Override
	public Tile getTileByGridPosition(int column, int row){
		checkAgainstBounds(column, row);
		return tileGrid.get(new Point2D(column, row));
	}
	
	
	public boolean atMiddleOfTile(Point2D screenPosition){
		try{
		Tile bottom=getTileByScreenPosition(new Point2D(screenPosition.getX(), screenPosition.getY()-tileHeight/2.1));
		Tile top=getTileByScreenPosition(new Point2D(screenPosition.getX(), screenPosition.getY()+tileHeight/2.1));
		Tile left=getTileByScreenPosition(new Point2D(screenPosition.getX()-tileWidth/2.1, screenPosition.getY()));
		Tile right=getTileByScreenPosition(new Point2D(screenPosition.getX()+tileWidth/2.1, screenPosition.getY()));
		Tile thisTile=getTileByScreenPosition(screenPosition);
		return ((bottom.equals(thisTile)&&thisTile.equals(top))||(left.equals(thisTile)&&thisTile.equals(right)));
		} catch (NullPointerException e){
			return false;
		}
	}

	private void checkAgainstBounds(int column, int row) {
		if (column >= getNumColsInGrid() || row >= getNumRowsInGrid()) {
			return;
			//throw new IndexOutOfBoundsException();
		}
		if (column < 0 || row < 0){
			return;
			//throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public Point2D getGridPositionFromScreenPosition(Point2D screenPosition) {
		int column = (int) Math.floor(screenPosition.getX() / tileWidth);
		int row = (int) Math.floor(screenPosition.getY() / tileHeight);
		return new Point2D(column, row);
	}
	
	@Override
	public Tile getTileByScreenPosition(Point2D screenPosition){		
		Point2D gridPosition = getGridPositionFromScreenPosition(screenPosition);
		return getTileByGridPosition((int) gridPosition.getX(), (int) gridPosition.getY());
	}
	
	@Override
	public void setTileByScreenPosition(Tile newTile, Point2D screenPosition) {
		Point2D gridPosition = getGridPositionFromScreenPosition(screenPosition);
		setTileByGridPosition(newTile, (int) gridPosition.getX(), (int) gridPosition.getY());
	}
	
	@Override
	public void setTileByGridPosition(Tile newTile, int column, int row){
		Point2D posOfNewTile = new Point2D(column, row);
		
		Boolean initialization = false;
		if(!tileGrid.containsKey(posOfNewTile)){
			initialization = true;
			tileGrid.put(posOfNewTile, newTile);
		}

		if(!initialization){ 
			// do not notify ScreenGrid for each initial Tile, only if changed after intialization
			newTile.setObserverList(tileGrid.get(posOfNewTile).getAndClearObservers());
			tileGrid.put(posOfNewTile, newTile);
			this.setChanged();
			this.notifyObservers(newTile);
		}
	}

	@Override
	public int getNumColsInGrid() {
		return numColsInGrid;
	}

	@Override
	public int getNumRowsInGrid() {
		return numRowsInGrid;
	}
	
	public List<Tile> getAllTiles(){
		tileList=new ArrayList<Tile>();
		for (int col = 0; col < numColsInGrid; col++) {
			for (int row = 0; row < numRowsInGrid; row++) {
				tileList.add(tileGrid.get(new Point2D(col, row)));
			}
		}
		return tileList;
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
	public void addAsObserver(Observer o) {
		observers.add(o);
	}
	
	
	@Override
	public void notifyObservers(){
		for (Observer o : observers){
			o.update(this, null);
		}
	}
	
	@Override
	public void notifyObservers(Object arg){
		for (Observer o : observers){
			o.update(this, arg);
		}
	}
	
	
	public void saveAndClearTileObservers()
	{
		tileObserverList = new ArrayList<List<Observer>>();
		
		for (int i = 0; i < getAllTiles().size(); i++)
		{
			tileObserverList.add(tileList.get(i).getAndClearObservers());
		}
		
	}
	
	public void setTileObservers()
	{
		for (int i = 0; i < tileList.size(); i++)
		{
			tileList.get(i).setObserverList(tileObserverList.get(i));
		}
	}
	
	public List<Observer> getObservers()
	{
		return observers;
	}
	
	public void clearObservers()
	{
		observers = new ArrayList<Observer>();
	}
	
	public void setObservers(List<Observer> list)
	{
		observers = list;
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
	public void setNumCols(int numColsInGrid)
	{
		this.numColsInGrid = numColsInGrid;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void setNumRows(int numRowsInGrid)
	{
		this.numRowsInGrid = numRowsInGrid;
		this.setChanged();
		this.notifyObservers();
	}

	

}
