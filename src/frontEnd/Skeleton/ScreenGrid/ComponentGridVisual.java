package frontEnd.Skeleton.ScreenGrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.SerializableObservableGen;
import backEnd.GameData.State.SerializableObserverGen;
import backEnd.GameData.State.State;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import resources.constants.StringResourceBundle;

/**
 * This class represents all the components on the grid and how they should be displayed visually.
 * @author Tim
 *
 */
public class ComponentGridVisual implements SkeletonObject, SerializableObserverGen<Component> {
	
	private ComponentGraph observedComponentGraph;
	private Set<Component> myComponents;
	private Map<Component, ImageView> myComponentImages;
	private State myState;
	private View myView;
	private Group myRoot;
	
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private String hoverAttributeDisplay = stringResourceBundle.getFromAttributeNames("UpgradeCost");
	
	/**
	 * Creates a new instance of this class using View and State. State allows this class to know which Components
	 * exist in the backend.
	 * @param view
	 * @param state
	 */
	public ComponentGridVisual(View view, State state){
		myRoot = new Group();
		myView = view;
		myState = state;
		observedComponentGraph = myState.getComponentGraph();
		observedComponentGraph.addObserver(this);
		placeComponents();
	}
	
	private void placeComponents() {
		myComponents = new HashSet<>();
		myComponentImages = new HashMap<>();
		updateComponentsOnGrid();
	}
	
	private void setCommandInteraction(Node n, AttributeOwner c) {
		n.setOnMouseClicked(e -> {
			if(e.getClickCount()==2){
				GenericCommandCenter comCenter = new GenericCommandCenter(myView, c);
				comCenter.launch("On-Screen Component", e.getSceneX(), e.getSceneY());
			}
		});
	}

	private void updateCorrespondingGrid(Component arg) {
		if (!myComponents.contains(arg)) {
			addComponentToGrid(arg);
		}
		if(!observedComponentGraph.getAllComponents().contains(arg)){
			removeComponentFromGrid(arg);
		}
	}

	private void updateComponentsOnGrid() {
		for (Component c : observedComponentGraph.getAllComponents()) {
			if (!myComponents.contains(c)) {
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
	
	private void removeComponentFromGrid(Component c){
		myComponents.remove(c);
		myRoot.getChildren().remove(myComponentImages.get(c));
		myComponentImages.remove(c);
	}

	private void addComponentToGrid(Component c){
		AttributeOwnerVisual frontAttr = new AttributeOwnerVisualImpl(c);
		frontAttr.refreshXY();
		ImageView frontImage = frontAttr.getImageView();
		addHover(frontImage,c);
		myComponents.add(c);
		myComponentImages.put(c, frontImage);
		myRoot.getChildren().add(frontImage);
		setCommandInteraction(frontImage, c);
	}
	
	private void addHover(ImageView n,Component c){
		String format = hoverAttributeDisplay +stringResourceBundle.getFromStringConstants("SingleIntegerWithColon");
		Tooltip hover = new Tooltip();
		n.hoverProperty().addListener((o, oldV, newV) -> {
			if (newV) {
				hover.setText(String.format(format,c.getAttribute(hoverAttributeDisplay).getValue()));
				Bounds scenePos = n.localToScreen(n.getBoundsInLocal());
				hover.show(n, scenePos.getMaxX(), scenePos.getMinY());
			} else {
				hover.hide();
			}
		});
	}

	@Override
	public Node getRoot(){
		return myRoot;
	}

	@Override
	public void update(SerializableObservableGen<Component> object, Component obj) {
		updateCorrespondingGrid(obj);
	}

}
