package frontEnd.Skeleton.ScreenGrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.State;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class ComponentGraphVisual implements SkeletonObject, SerializableObserver {
	
	private ComponentGraph observedComponentGraph;
	
	private Set<ComponentImpl> myComponents;
	private Map<ComponentImpl, ImageView> myComponentImages;
	private State myState;
	private View myView;
	private Group myRoot;
	
	public ComponentGraphVisual(View view, State state){
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
			GenericCommandCenter comCenter = new GenericCommandCenter(myView, c);
			comCenter.launch("On-Screen Component", e.getSceneX(), e.getSceneY());
		});
	}

	private void updateCorrespondingGrid(ComponentImpl arg) {
		if (!myComponents.contains(arg)) {
			addComponentToGrid(arg);
		}
		if(!observedComponentGraph.getAllComponents().contains(arg)){
			removeComponentFromGrid(arg);
		}
		
	}

	private void updateComponentsOnGrid() {
		for (ComponentImpl c : observedComponentGraph.getAllComponents()) {
			if (!myComponents.contains(c)) {
				//System.out.println("in screenGrid, updateComponentsOnGrid() got called");
				addComponentToGrid(c);
			}
		}
		Set<ComponentImpl> myComponentsCopy=new HashSet<>(myComponents);
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

	private void addComponentToGrid(ComponentImpl c) {
		AttributeOwnerVisual frontAttr = new AttributeOwnerVisualImpl(c);
		frontAttr.refreshXY();
		ImageView frontImage = frontAttr.getImageView();
		//frontImage.setFitWidth(20);
		//frontImage.setFitHeight(40);
		myComponents.add(c);
		myComponentImages.put(c, frontImage);
		myRoot.getChildren().add(frontImage);
		setCommandInteraction(frontImage, c);
	}

	@Override
	public void update(SerializableObservable so, Object obj) {
		updateCorrespondingGrid((ComponentImpl) obj);
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

}
