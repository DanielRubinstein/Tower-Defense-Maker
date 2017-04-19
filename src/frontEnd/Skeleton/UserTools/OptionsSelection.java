package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.List;

import ModificationFromUser.Modification_ChangeMode;
import ModificationFromUser.Modification_GameRemote;
import backEnd.GameEngine.Engine.GameProcessController;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class OptionsSelection implements SkeletonObject{

	private static final String SETTINGS_IMAGE = "resources/images/Tools/Settings.jpg";
	private static final String PAUSE_IMAGE = "resources/images/Tools/pause.jpg";
	private static final String FASTFWD_IMAGE = "resources/images/Tools/fastfwd.jpg";
	private static final String PLAY_IMAGE = "resources/images/Tools/play.jpg";
	private VBox myRoot;
	private TilePane myTiles;
	private SettingsView mySettings;
	private List<Button> myButtons;
	private View myView;
	
	public OptionsSelection(View view) {
		myView = view;
		myRoot = new VBox();
		myTiles = new TilePane(Orientation.HORIZONTAL,0, 0);
		mySettings= new SettingsViewImpl(view, myView.getAppStage());
		myRoot.getChildren().add(myTiles);
		
	}
	public Node getRoot(){
		
		return myRoot;
	}
	public void setAlignment(Pos position,Priority priority){
		myTiles.setAlignment(Pos.TOP_RIGHT);
	}
	public void setSize(double width, double height){
		myTiles.setPrefWidth(width);
		myRoot.setPrefWidth(width);
		myRoot.setPrefHeight(height);
		myRoot.setMaxHeight(height);
		setUpOptions(width,height); //TODO hard coded
	}
	private void setUpOptions(double totalWidth,double totalHeight){
		myButtons = new ArrayList<Button>();
		addButtons(totalWidth/4-1);
		myTiles.setPrefColumns(myButtons.size());
		myTiles.getChildren().addAll(myButtons);
	}
	
	private void addButtons(double size){
		myButtons.add(createImageButton(PLAY_IMAGE, e-> myView.sendUserModification(Modification_GameRemote.PLAY) ,size));
		myButtons.add(createImageButton(PAUSE_IMAGE, e-> myView.sendUserModification(Modification_GameRemote.PAUSE) ,size));
		myButtons.add(createImageButton(FASTFWD_IMAGE, e-> myView.sendUserModification(Modification_GameRemote.FASTFORWARD) ,size));
		myButtons.add(createImageButton(SETTINGS_IMAGE, e-> mySettings.launchSettings(),size));
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
