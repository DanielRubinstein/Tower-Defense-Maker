package frontEnd.Skeleton.ScreenGrid;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ModificationFromUser.AttributeOwner.Modification_EditAttribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.ComponentGraphImpl;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.GameData.State.TileImpl;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.AoTools.OnGridTileCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

/**
 * This class is used to represent the actual game, such as the Tiles and
 * Components.
 * 
 * @author Tim
 *
 */

public class ScreenGrid implements SkeletonObject, Observer {
	private View myView;
	private State myState;
	
	private Group myRoot;
	private GridPane myGrid;
	
	private TileGrid observedTileGrid;
	private ComponentGraph observedComponentGraph;
	
	private Set<Component> myComponents;
	private Map<Component, ImageView> myComponentImages;
	private Map<Point2D, Tile> myTiles;
	private Map<Tile, ImageView> myTileImages;
	
	private double myWidth;
	private double myHeight;
	private int numberOfTileCols;
	private int numberOfTileRows;
	private double tileWidth;
	private double tileHeight;

	/**
	 * Constructs a new ScreenGrid object given the view and state. State contains
	 * all the required backend information like location of tiles and
	 * attributes of everything needed to be displayed on screen.
	 * 
	 * @param view
	 * @param state
	 * @param screenGridHeight 
	 * @param screenGridWidth 
	 */
	public ScreenGrid(View view, State state, double screenGridWidth, double screenGridHeight) {
		myView = view;
		myState = state;
		extractAoCollectionsFromState();

		adjustSize(screenGridWidth,screenGridHeight);
		initializeGrid();
		myTiles = new HashMap<>();
		myTileImages = new HashMap<>();
		updateTilesOnGrid();
		addGridToRoot();
		placeComponents();
	}
	
	private void adjustSize(double screenGridWidth, double screenGridHeight){
		myWidth = screenGridWidth;
		myHeight = screenGridHeight;
		numberOfTileCols = observedTileGrid.getNumColsInGrid();
		numberOfTileRows = observedTileGrid.getNumRowsInGrid();
		tileWidth = myWidth / numberOfTileCols;
		tileHeight = myHeight / numberOfTileRows;
		observedTileGrid.setTileSize(tileWidth, tileHeight);
	}

	private void placeComponents() {
		myComponents = new HashSet<>();
		myComponentImages = new HashMap<>();
		updateComponentsOnGrid();
	}

	private void addGridToRoot() {
		myRoot = new Group();
		myRoot.getChildren().add(myGrid);
		myGrid.toBack();
	}

	private void extractAoCollectionsFromState() {
		observedTileGrid = myState.getTileGrid();
		observedTileGrid.addAsObserver(this);
		observedComponentGraph = myState.getComponentGraph();
		observedComponentGraph.addAsObserver(this);
	}

	private void initializeGrid() {
		myGrid = new GridPane();
		myGrid.setMinWidth(numberOfTileCols);
		myGrid.setMinHeight(numberOfTileRows);
		myGrid.setPrefWidth(myWidth);
		myGrid.setPrefHeight(myHeight);
	}

	public Node getRoot() {
		return myRoot;
	}

	private void organizeImageView(ImageView tileView) {
		tileView.setPreserveRatio(false);
		tileView.setFitWidth(tileWidth);
		tileView.setFitHeight(tileHeight);
		tileView.fitWidthProperty().bind(myGrid.widthProperty().divide(numberOfTileCols));
		tileView.fitHeightProperty().bind(myGrid.heightProperty().divide(numberOfTileRows));
	}

	private void setTileInteraction(Node n, Tile t) {
		n.setOnMouseClicked(e -> {
			OnGridTileCommandCenter tileInteractor = new OnGridTileCommandCenter(myView, t, myState);
			tileInteractor.launch("On-Screen Tile" ,e.getScreenX(), e.getScreenY());
		});
	}

	private void setCommandInteraction(Node n, AttributeOwner c) {
		n.setOnMouseClicked(e -> {
			GenericCommandCenter comCenter = new GenericCommandCenter(myView, c);
			comCenter.launch("On-Screen Component", e.getSceneX(), e.getSceneY());
		});
		n.setOnDragDetected(e -> {
			Dragboard db = n.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString(myView.getBankController().getAOName(c));
			db.setContent(content);
			String imagePath = c.<String>getAttribute("ImageFile").getValue();
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
			db.setDragView(image);
		});
		myRoot.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		
		myRoot.setOnDragDropped(e -> {
			Point2D newPos = new Point2D(e.getSceneX(),e.getSceneY());
			myView.sendUserModification(new Modification_EditAttribute(c,c.getAttribute("Position"),newPos));
		});
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == observedComponentGraph || o == observedTileGrid){
			try{
				Method updateMethod = ScreenGrid.class.getDeclaredMethod("updateCorrespondingGrid", arg.getClass());
				updateMethod.setAccessible(false);
				updateMethod.invoke(this, arg);
			} catch (NoSuchMethodException e){
				
			} catch (Exception e){
				
			}
			
		}

	}

	private void updateCorrespondingGrid(Component arg) {
		if (!myComponents.contains(arg)) {
			addComponentToGrid(arg);
		}
		if(!observedComponentGraph.getAllComponents().contains(arg)){
			removeComponentFromGrid(arg);
		}
		
	}
	
	private void updateCorrespondingGrid(TileImpl arg) {
		Point2D newTileScreenPosition = arg.getMyAttributes().<Point2D>get("Position").getValue();
		Point2D newTileGridPosition =observedTileGrid.getGridPositionFromScreenPosition(newTileScreenPosition);
		addTileToGrid(arg, newTileGridPosition);

	}

	private void updateTilesOnGrid() {
		for (int row = 0; row < numberOfTileRows; row++) {
			for (int col = 0; col < numberOfTileCols; col++) {
				Tile t = observedTileGrid.getTileByGridPosition(col, row);
				Point2D pos = new Point2D(col, row);
				if(!myTiles.containsKey(pos) || !myTiles.get(pos).equals(t)){
					addTileToGrid(t, pos);
				}
			}
		}
	}

	private void addTileToGrid(Tile t, Point2D pos) {
		FrontEndAttributeOwner attrOwner = new FrontEndAttributeOwnerImpl(t);
		ImageView tileView = attrOwner.getImageView();
		organizeImageView(tileView);
		setTileInteraction(tileView,  t);
		if(myTiles.containsKey(pos) && !myTiles.get(pos).equals(t)){
			myGrid.getChildren().remove(myTileImages.get(myTiles.get(pos)));
		}
		myGrid.add(tileView, (int) pos.getX(), (int) pos.getY()); 
		// this is correct, when you add to gridPane it is (node, col, row)
		myTiles.put(pos, t);
		myTileImages.put(t, tileView);
	}

	private void updateComponentsOnGrid() {
		for (Component c : observedComponentGraph.getAllComponents()) {
			if (!myComponents.contains(c)) {
				//System.out.println("in screenGrid, updateComponentsOnGrid() got called");
				addComponentToGrid(c);
			}
		}
		Set<Component> myComponentsCopy=new HashSet<>(myComponents);
		for(Component c : myComponentsCopy){
			if(!observedComponentGraph.getAllComponents().contains(c)){
				removeComponentFromGrid(c);
			}
		}
	}
	
	private void removeComponentFromGrid(Component c) {
		myComponents.remove(c);
		myRoot.getChildren().remove(myComponentImages.get(c));
		myComponentImages.remove(c);
	}

	private void addComponentToGrid(Component c) {
		FrontEndAttributeOwner frontAttr = new FrontEndAttributeOwnerImpl(c);
		frontAttr.refreshXY();
		ImageView frontImage = frontAttr.getImageView();
		frontImage.setFitWidth(tileWidth / 2);
		frontImage.setFitHeight(tileHeight / 2);
		setCommandInteraction(frontImage, c);
		myComponents.add(c);
		myComponentImages.put(c, frontImage);
		myRoot.getChildren().add(frontImage);
	}

}