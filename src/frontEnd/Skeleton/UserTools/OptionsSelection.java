package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.List;

import ModificationFromUser.Modification_GameRemote;
import backEnd.GameEngine.Engine.GameProcessController;
import frontEnd.View;
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

public class OptionsSelection {

	private static final String SETTINGS_IMAGE = "resources/images/Tools/Settings.jpg";
	private static final String PAUSE_IMAGE = "resources/images/Tools/pause.jpg";
	private static final String FASTFWD_IMAGE = "resources/images/Tools/fastfwd.jpg";
	private static final String PLAY_IMAGE = "resources/images/Tools/play.jpg";
	private TilePane myRoot;
	private SettingsView mySettings;
	private List<Button> myButtons;
	private View myView;
	
	public OptionsSelection(View view) {
		myView = view;
		myRoot = new TilePane(Orientation.HORIZONTAL,0, 0);
		mySettings= new SettingsViewImpl(view, myView.getAppStage());
	}
	public Node getNode(){
		return myRoot;
	}
	public void setAlignment(Pos position,Priority priority){
		myRoot.setAlignment(Pos.TOP_RIGHT);
	}
	public void setSize(double width, double height){
		myRoot.setPrefWidth(width);
		myRoot.setPrefHeight(height);
		setUpOptions(width); //TODO hard coded
	}
	private void setUpOptions(double totalWidth){
		myButtons = new ArrayList<Button>();
		addButtons(totalWidth/4-1);
		myRoot.getChildren().addAll(myButtons);
		addFacebook();
	}
	
	private void addFacebook() {
		OptionsFacebook fb = new OptionsFacebook(myView,myView.getAppStage());
		myRoot.getChildren().add(fb.getRoot());
		
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
