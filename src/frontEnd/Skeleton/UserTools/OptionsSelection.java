package frontEnd.Skeleton.UserTools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import ModificationFromUser.Modification_GameRemote;
import ModificationFromUser.savingAndLoading.Modification_RestartLevel;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import frontEnd.Skeleton.SkeletonObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;
import resources.constants.numeric.ScreenConstants;

public class OptionsSelection implements SkeletonObject{

	private VBox myRoot;
	private TilePane myTiles;
	private SettingsView mySettings;
	private List<Button> myButtons;
	private View myView;
	private Map<Button, Tooltip> myButtonsAndTooltips;
	private ScreenConstants screenResources = new ScreenConstants();
	private StringResourceBundle strResources = new StringResourceBundle();
	private NumericResourceBundle numResources = new NumericResourceBundle();
	
	private static final double BUTTON_SIZE_FACTOR = 0.25;
	private String PAUSE_IMAGE = strResources.getFromImageText("pause");
	private String PLAY_IMAGE = strResources.getFromImageText("play");
	private String RESTART_IMAGE = strResources.getFromImageText("restart");
	
	public OptionsSelection(View view) {
		myView = view;
		myRoot = new VBox();
		myTiles = new TilePane(Orientation.HORIZONTAL,0, numResources.getFromSizing("StandardSpacing"));
		mySettings= new SettingsViewImpl(view);
		myRoot.getChildren().add(myTiles);
		setDimensions();
	}
	public Node getRoot(){
		
		return myRoot;
	}
	public void setAlignment(Pos position,Priority priority){
		myTiles.setAlignment(position);
	}
	private void setDimensions(){
		double width = screenResources.getSideWidth();
		double height = screenResources.getBottomHeight()/4;
		myTiles.setPrefWidth(width);
		myRoot.setPrefWidth(width);
		myRoot.setPrefHeight(height);
		myRoot.setMaxHeight(height);
		setUpOptions(width,height); 
	}

	private void setUpOptions(double totalWidth,double totalHeight){
		myButtons = new ArrayList<Button>();
		addButtons(totalWidth * BUTTON_SIZE_FACTOR);
		myTiles.setPrefColumns(myButtons.size());
		myTiles.getChildren().addAll(myButtons);
		addSettingsButton();
	}
	
	private void addSettingsButton() {
		ActionButton settings = new ActionButton("Settings", e -> mySettings.displayOnStage(new Stage()));
		settings.setAlignment(Pos.CENTER);
		myRoot.getChildren().add(settings);
		settings.setAlignment(Pos.CENTER);
	}
	private void addButtons(double size){
		myButtonsAndTooltips=new LinkedHashMap<Button, Tooltip>();
		myButtonsAndTooltips.put(createImageButton(PLAY_IMAGE, e-> myView.sendUserModification(Modification_GameRemote.PLAY) ,size), new Tooltip("Play game"));
		myButtonsAndTooltips.put(createImageButton(PAUSE_IMAGE, e-> myView.sendUserModification(Modification_GameRemote.PAUSE) ,size), new Tooltip("Pause game"));
		myButtonsAndTooltips.put(createImageButton(RESTART_IMAGE, e-> myView.sendUserModification(new Modification_RestartLevel()) ,size), new Tooltip("Restart level"));
		
		for (Button b: myButtonsAndTooltips.keySet()){
			modifyTooltipStartTiming(myButtonsAndTooltips.get(b));
			b.setTooltip(myButtonsAndTooltips.get(b));
			myButtons.add(b);
		}
	}
	
	
	private void modifyTooltipStartTiming(Tooltip tooltip) {
	    try {
	        Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(tooltip);

	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
	    } catch (Exception e) {
			ErrorDialog eD = new ErrorDialog();
			eD.create(strResources.getFromErrorMessages("Default_Error"), e.getMessage());
	    }
	}
	
	
	private Button createImageButton(String imageName, EventHandler<ActionEvent> event, double size){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		ImageView viewImage = new ImageView(image);
		viewImage.setPreserveRatio(false);
		viewImage.setFitWidth(size);
		viewImage.setFitHeight(size);
		Button b = new Button();
		b.setOnAction(event);
		b.setGraphic(viewImage);
		b.setPadding(Insets.EMPTY);
		b.getStyleClass().clear();
		return b;
	}



}
