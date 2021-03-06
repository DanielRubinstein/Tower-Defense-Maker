package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

/**
 * This is the Grid class that contains the Tile Grid and all of the relevant
 * getters/setters for other modules to use to access the Grid
 * 
 * @author Riley Nisbet
 *
 */

public class TileGridImpl implements TileGrid {
	
	private List<SerializableObserverGen<Tile>> observers;

	private int numColsInGrid;
	private int numRowsInGrid;
	private Map<Point2D, Tile> tileGrid;
	private List<Set<Tile>> tileGroups;
	private double tileWidth;
	private double tileHeight;
	private double tileCenterFactor; //ratio of the tile that we consider part of the center of the tile
	private static final NumericResourceBundle NUMERIC_RESOURCE_BUNDLE = new NumericResourceBundle();
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();


	public TileGridImpl(int colsInGrid, int rowsInGrid) {

		observers = new ArrayList<SerializableObserverGen<Tile>>();
		numColsInGrid = colsInGrid;
		numRowsInGrid = rowsInGrid;
		tileGrid = new HashMap<>();
		tileCenterFactor=Double.valueOf(NUMERIC_RESOURCE_BUNDLE.getFromSizing("TileCenterFactor"));

	}

	public TileGridImpl(TileGridInstantiator i) {
		observers = new ArrayList<SerializableObserverGen<Tile>>();
		numColsInGrid = i.getNumCols();
		numRowsInGrid = i.getNumRows();
		tileGrid = i.getTileGrid();
		tileCenterFactor=Double.valueOf(NUMERIC_RESOURCE_BUNDLE.getFromSizing("TileCenterFactor"));
	}

	public TileGridInstantiator getInstantiator() {
		return new TileGridInstantiator(numRowsInGrid, numColsInGrid, tileGrid);
	}

	@Override
	public void setTileSize(double tileWidth, double tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	
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
	
	
	
	public boolean atMiddleXOfTile(Point2D screenPosition)
	{
		try
		{
			 return (generateLeftTile(screenPosition).equals(getTileByScreenPosition(screenPosition)) && getTileByScreenPosition(screenPosition).equals(generateRightTile(screenPosition)));
		}
		catch (NullPointerException e)
		{
				return false;
		}
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
			newTile.getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).setValue(posOfNewTile);
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
		return new Point2D((column + 0.5) * (tileWidth), (row + 0.5) * (tileHeight)); //0.5 to center tile (0=left, 1=right, 0.5=middle)
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
	
	private void notifyObservers(Tile arg){
		for (SerializableObserverGen<Tile> o : observers){
			o.update(null, arg);
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
	}

	@Override
	public void setNumRows(int numRowsInGrid) {
		this.numRowsInGrid = numRowsInGrid;
	}

	@Override
	public List<SerializableObserverGen<Tile>> getObserversGen() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers.clear();
	}

	@Override
	public void setObserversGen(List<SerializableObserverGen<Tile>> observersave) {
		observers = observersave;
	}
	@Override
	public void addObserver(SerializableObserverGen<Tile> o) {
		observers.add(o);
	}
	
	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
	
	@Override
	public Collection<AttributeOwner> getAllAttributeOwners() {
		Collection<AttributeOwner> myAOs = new ArrayList<AttributeOwner>();
		for(AttributeOwner ao : tileGrid.values()){
			myAOs.add(ao);
		}
		return myAOs;
	}
	
	@Override
	public List<Set<Tile>> getTileGroups() throws Exception{
		buildTileGroups();
		return tileGroups;
	}
	
	private void buildTileGroups() throws Exception {
		tileGroups = new ArrayList<Set<Tile>>();
		Tile[][] tileGridThing = new Tile[numColsInGrid][numRowsInGrid];
		for(Point2D point2d : tileGrid.keySet()){
			//System.out.println(this.getClass().getSimpleName() + ": Point: " + point2d);
			int colEq = (int)(point2d.getX() / getTileWidth());
			int rowEq = (int)(point2d.getY() / getTileHeight());
			//System.out.println(this.getClass().getSimpleName() + ": Width: " + getTileWidth() + ", Height: " + getTileHeight());
			//System.out.println(this.getClass().getSimpleName() + ": R,C: " + rowEq + ", " + colEq);
			if(tileGridThing[colEq][rowEq] != null || rowEq < 0 || colEq < 0 || rowEq >= getNumRowsInGrid() || colEq >= getNumColsInGrid()){
				throw new Exception("Error Building Tile Groups");
			}
			tileGridThing[colEq][rowEq] = tileGrid.get(point2d);
		}
		for (int i = 0; i < numRowsInGrid; i++) {
			for (int j = 0; j < numColsInGrid; j++) {
				if (tileGridThing[j][i] == null) {
					throw new Exception("Error Building Tile Groups");
				}
				Set<Tile> tempSet = new HashSet<Tile>();
				tempSet.add(tileGridThing[j][i]);
				tileGroups.add(tempSet);
			}
		}
		for (int j = 0; j < numColsInGrid; j++) {
			for (int i = 0; i < numRowsInGrid; i++) {
				Tile tempTile = tileGridThing[j][i];
				String moveDir = tempTile.<String>getAttribute("MoveDirection").getValue();
				if(moveDir.equals(STRING_RESOURCES.getFromStringConstants("Up")) && i > 0){
					joinSets(tempTile, tileGridThing[j][i-1]);
				} else if (moveDir.equals(STRING_RESOURCES.getFromStringConstants("Down")) && i < numRowsInGrid-1) {
					joinSets(tempTile, tileGridThing[j][i+1]);					
				} else if (moveDir.equals(STRING_RESOURCES.getFromStringConstants("Left")) && j > 0) {
					joinSets(tempTile, tileGridThing[j-1][i]);
				} else if (moveDir.equals(STRING_RESOURCES.getFromStringConstants("Right")) && j < numColsInGrid-1) {
					joinSets(tempTile, tileGridThing[j+1][i]);					
				} else {
				}
			}
		}
	}
	
	private void joinSets(Tile tile1, Tile tile2) {
		int tileSet1 = -1;
		int tileSet2 = -1;
		for (int i = 0; i < tileGroups.size(); i++) {
			Set<Tile> tempSet = tileGroups.get(i);
			if (tempSet.contains(tile1)) {
				tileSet1 = i;
			}
			if (tempSet.contains(tile2)) {
				tileSet2 = i;
			}
		}
		if (tileSet1 == -1 || tileSet2 == -1 || tileSet1 == tileSet2) {
			return;
		}
		tileGroups.get(tileSet1).addAll(tileGroups.get(tileSet2));
		tileGroups.remove(tileSet2);
	}
	
}
