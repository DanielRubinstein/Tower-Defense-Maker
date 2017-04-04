package frontEnd.Skeleton.View;

import backEnd.Mode.Author;
import backEnd.Mode.Mode;
import backEnd.Mode.Player;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ModeIndicator {
	private Label myIndicator;
	
	public ModeIndicator(){
		myIndicator = new Label("No Mode Set");
	}
	
	public void setMode(Mode mode){
		if (mode instanceof Author){
			this.setMode("Author Mode");
		} else if (mode instanceof Player){
			this.setMode("Player Mode");
		}
	}
	
	public Node getIndicator(){
		return this.myIndicator;
	}
	
	private void setMode(String newMode){
		myIndicator.setText("Current Mode: " + newMode);
	}
}
