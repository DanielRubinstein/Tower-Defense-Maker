package frontEnd.Skeleton.UserTools;

import frontEnd.ViewReader;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ModeIndicator implements SkeletonObject{
	private Label pausedIndicator;
	private Label userModeIndicator;
	private Label gameModeIndicator;
	private Label levelModeIndicator;
	private VBox indicatorHolder;
	private ViewReader myView;
	private SimpleBooleanProperty authorProperty;
	private SimpleStringProperty gameProperty;
	private SimpleStringProperty levelProperty;
	private SimpleStringProperty runProperty;
	
	public ModeIndicator(ViewReader view){
		myView=view;
		authorProperty = myView.getBooleanAuthorModeProperty();
		userModeIndicator = new Label();
		setIndicator(authorProperty.getValue());
		authorProperty.addListener((ob, oldV, newV) -> {
			setIndicator(newV);
		});
		
		gameProperty = myView.getStringGameModeProperty();
		gameModeIndicator = new Label();
		gameModeIndicator.setText("Game: " + gameProperty.getValue());
		gameProperty.addListener((ob, oldV, newV) -> {
			gameModeIndicator.setText(newV);
		});
		
		levelProperty = myView.getStringLevelModeProperty();
		levelModeIndicator = new Label();
		levelModeIndicator.setText("Level: " + levelProperty.getValue());
		levelProperty.addListener((ob, oldV, newV) -> {
			levelModeIndicator.setText(newV);
		});
		
		runProperty = myView.getRunStatus();
		pausedIndicator = new Label();
		pausedIndicator.setText(runProperty.getValue());
		runProperty.addListener((ob, oldV, newV) -> {
			pausedIndicator.setText(newV);
		});
		
		indicatorHolder = new VBox();
		indicatorHolder.getChildren().addAll(pausedIndicator, userModeIndicator, gameModeIndicator, levelModeIndicator);
	}
	
	private void setIndicator(Boolean newV) {
		String text = "Mode: ";
		if(newV){
			text += "Author";
		} else {
			text+= "Player";
		}
		userModeIndicator.setText(text);
	}



	public Node getRoot(){
		return indicatorHolder;
	}
	

}
