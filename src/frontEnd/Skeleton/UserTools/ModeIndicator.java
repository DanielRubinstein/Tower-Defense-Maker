package frontEnd.Skeleton.UserTools;

import frontEnd.ViewReader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ModeIndicator {
	private Label pausedIndicator;
	private Label modeIndicator;
	private VBox indicatorHolder;
	private ViewReader myView;
	
	public ModeIndicator(ViewReader view){
		myView=view;
		
		// TODO make this smart and binding?
		//myView.getRunStatus()
		//myView.getMode().getName()
		pausedIndicator = new Label("Play or pause??");
		modeIndicator = new Label("Print current mode");
		indicatorHolder = new VBox();
		indicatorHolder.getChildren().addAll(pausedIndicator, modeIndicator);
	}
	

	
	public Node getIndicator(){
		return indicatorHolder;
	}
	

}
