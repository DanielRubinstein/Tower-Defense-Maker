package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.List;

import ModificationFromUser.Modification_ChangeMode;
import frontEnd.ViewEditor;
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

	private static final String SETTINGS_IMAGE = "images/Settings.jpg";
	private static final String PAUSE_IMAGE = "images/pause.jpg";
	private static final String FASTFWD_IMAGE = "images/fastfwd.jpg";
	private static final String PLAY_IMAGE = "images/play.jpg";
	private TilePane myRoot;
	private SettingsView mySettings;
	private List<Button> myButtons;
	private ViewEditor myView;
	
	public OptionsSelection(ViewEditor view) {
		myView = view;
		myRoot = new TilePane(Orientation.HORIZONTAL,0, 0);
		mySettings= new SettingsViewImpl(view);
	}
	public Node getNode(){
		return myRoot;
	}
	public void setAlignment(Pos position,Priority priority){
		myRoot.setAlignment(Pos.BOTTOM_RIGHT);
	}
	public void setSize(double width, double height){
		myRoot.setPrefWidth(width);
		myRoot.setPrefHeight(height);
		setUpOptions(width/4);
	}
	private void setUpOptions(double buttonWidth){
		myButtons = new ArrayList<Button>();
		addButtons(buttonWidth);
		myRoot.getChildren().addAll(myButtons);
	}
	
	private void addButtons(double size){
		// TODO replace with correct Modification classes
		addButtonImage(PLAY_IMAGE, e-> myView.sendUserModification(new Modification_ChangeMode()) ,size);
		addButtonImage(PAUSE_IMAGE, e-> myView.sendUserModification(new Modification_ChangeMode()) ,size);
		addButtonImage(FASTFWD_IMAGE, e-> myView.sendUserModification(new Modification_ChangeMode()) ,size);
		
		addButtonImage(SETTINGS_IMAGE, e-> mySettings.launchSettings(),size);
	}
	
	
	private void addButtonImage(String imageName, EventHandler<ActionEvent> event, double size){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		ImageView viewImage = new ImageView(image);
		viewImage.setFitWidth(size);
		viewImage.setPreserveRatio(true);
		Button b = new Button();
		b.setOnAction(event);
		b.setGraphic(viewImage);
		b.setPadding(Insets.EMPTY);
		b.getStyleClass().clear();
		myButtons.add(b);
	}



}
