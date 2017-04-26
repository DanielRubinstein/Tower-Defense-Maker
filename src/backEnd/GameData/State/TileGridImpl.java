package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;

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
	private List<List<SerializableObserver>> tileObserverList;

	public TileGridImpl(int colsInGrid, int rowsInGrid) {

		observers = new ArrayList<SerializableObserver>();
		numColsInGrid = colsInGrid;
		numRowsInGrid = rowsInGrid;
		tileGrid = new HashMap<>();
	}

	public TileGridImpl(TileGridInstantiator i) {
		observers = new ArrayList<SerializableObserver>();
		numColsInGrid = i.getNumCols();
		numRowsInGrid = i.getNumRows();
		tileGrid = i.getTileGrid();
	}

	public TileGridInstantiator getInstantiator() {
		return new TileGridInstantiator(numRowsInGrid, numColsInGrid, tileGrid);
	}

	@Override
	public void setTileSize(double tileWidth, double tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	@Override
	public Tile getTileByGridPosition(int column, int row) {
		checkAgainstBounds(column, row);
		return tileGrid.get(new Point2D(column, row));
	}

	public boolean atMiddleOfTile(Point2D screenPosition) {
		// Tile bottom = getTileByScreenPosition(
		// new Point2D(screenPosition.getX(), screenPosition.getY() - tileHeight
		// / 2.1));
		// Tile top = getTileByScreenPosition(
		// new Point2D(screenPosition.getX(), screenPosition.getY() + tileHeight
		// / 2.1));
		// Tile left = getTileByScreenPosition(
		// new Point2D(screenPosition.getX() - tileWidth / 2.1,
		// screenPosition.getY()));
		// Tile right = getTileByScreenPosition(
		// new Point2D(screenPosition.getX() + tileWidth / 2.1,
		// screenPosition.getY()));
		// Tile thisTile = getTileByScreenPosition(screenPosition);
		// return ((bottom.equals(thisTile) && thisTile.equals(top)) ||
		// (left.equals(thisTile) && thisTile.equals(right)));
		// Almost got cancer and died from reading the above
		Tile thisTile = getTileByScreenPosition(screenPosition);
		Point2D xyTilePosition = thisTile.<Point2D> getAttribute("Position").getValue();
		Point2D position = new Point2D((int) (xyTilePosition.getX() / tileWidth),
				(int) (xyTilePosition.getY() / tileHeight));
		double midMagicNumLmao = (.1 / 2.1);// TODO fix this (it is the percent
											// of the tile considered the
											// middle)
		double topMiddle = (position.getY() + (1 - midMagicNumLmao) / 2) * tileHeight; 
			// Top as in higher visually
		double bottomMiddle = (position.getY() + (1 + midMagicNumLmao) / 2) * tileHeight; 
			// Bottom as in lower visually
		double leftMiddle = (position.getX() + (1 - midMagicNumLmao) / 2) * tileWidth;
		double rightMiddle = (position.getX() + (1 + midMagicNumLmao) / 2) * tileWidth;
		double xPos = screenPosition.getX();
		double yPos = screenPosition.getY();
		boolean middleTopBottom = topMiddle < yPos && yPos < bottomMiddle;
		boolean middleLeftRight = leftMiddle < xPos && xPos < rightMiddle;
		return middleTopBottom || middleLeftRight;
	}

	private void checkAgainstBounds(int column, int row) {
		if (column >= getNumColsInGrid() || row >= getNumRowsInGrid()) {
			return;
			// throw new IndexOutOfBoundsException();
		}
		if (column < 0 || row < 0) {
			return;
			// throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public Point2D getGridPositionFromScreenPosition(Point2D screenPosition) {
		int column = (int) Math.floor(screenPosition.getX() / tileWidth);
		int row = (int) Math.floor(screenPosition.getY() / tileHeight);
		return new Point2D(column, row);
	}

	@Override
	public Tile getTileByScreenPosition(Point2D screenPosition) {
		Point2D gridPosition = getGridPositionFromScreenPosition(screenPosition);
		return getTileByGridPosition((int) gridPosition.getX(), (int) gridPosition.getY());
	}

	@Override
	public void setTileByScreenPosition(Tile newTile, Point2D screenPosition) {
		Point2D gridPosition = getGridPositionFromScreenPosition(screenPosition);
		setTileByGridPosition(newTile, (int) gridPosition.getX(), (int) gridPosition.getY());
	}

	@Override
	public void setTileByGridPosition(Tile newTile, int column, int row) {
		Point2D posOfNewTile = new Point2D(column, row);

		Boolean initialization = false;
		if (!tileGrid.containsKey(posOfNewTile)) {
			initialization = true;
			tileGrid.put(posOfNewTile, newTile);
		}

		if (!initialization) {
			// do not notify ScreenGrid for each initial Tile, only if changed
			// after intialization
			newTile.setObserverList(tileGrid.get(posOfNewTile).getAndClearObservers());
			tileGrid.put(posOfNewTile, newTile);
			notifyObservers(newTile);
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

	public List<Tile> getAllTiles() {
		tileList = new ArrayList<Tile>();
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
		observers = null;
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}
}
