package frontEnd.Skeleton.UserTools;

import backEnd.Mode.Author;
import backEnd.Mode.Mode;
import backEnd.Mode.Player;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ModeIndicator {
	private Label pausedIndicator;
	private Label modeIndicator;
	private VBox indicatorHolder;
	
	public ModeIndicator(){
		pausedIndicator = new Label("Game is paused");
		modeIndicator = new Label("random mode");
		indicatorHolder = new VBox();
		indicatorHolder.getChildren().addAll(pausedIndicator,modeIndicator);
	}
	
	public void setMode(Mode mode){
		if (mode instanceof Author){
			this.setMode("Author Mode");
		} else if (mode instanceof Player){
			this.setMode("Player Mode");
		}
	}
	
	public Node getIndicator(){
		return indicatorHolder;
	}
	
	private void setMode(String newMode){
		//myIndicator.setText("Current Mode: " + newMode);
	}
}
