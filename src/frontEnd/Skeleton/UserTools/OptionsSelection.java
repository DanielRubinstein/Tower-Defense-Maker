package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.List;

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
	
	public OptionsSelection(SettingsView settings, double width, double height) {
		myRoot = new TilePane(Orientation.HORIZONTAL,0, 0);
		formatRoot(width,height);
		mySettings=settings;
		myButtons = new ArrayList<Button>();
		setUpOptions(width/4);
		myRoot.getChildren().addAll(myButtons);
	}
	public Node getNode(){
		return myRoot;
	}
	public void setAlignment(Pos position,Priority priority){
		myRoot.setAlignment(Pos.BOTTOM_RIGHT);
	}
	private void formatRoot(double width, double height){
		myRoot.setPrefWidth(width);
		myRoot.setPrefHeight(height);
	}
	private void setUpOptions(double buttonWidth){
		addButtons(buttonWidth);
	}

	
	private void addButtons(double size){
		addButtonImage(PLAY_IMAGE, e-> System.out.println("play"),size);
		addButtonImage(PAUSE_IMAGE, e-> System.out.println("pausing"),size);
		addButtonImage(FASTFWD_IMAGE, e-> System.out.println("fast forwarding"),size);
		addButtonImage(SETTINGS_IMAGE, e-> mySettings.launchSettings(400, 400),size);
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
		myButtons.add(b);
	}


}
