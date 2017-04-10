package frontEnd.Skeleton.UserTools;

import frontEnd.ViewReader;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ModeIndicator implements SkeletonObject{
	private Label pausedIndicator;
	private Label modeIndicator;
	private VBox indicatorHolder;
	private ViewReader myView;
	private SimpleBooleanProperty authorProperty;
	
	public ModeIndicator(ViewReader view){
		myView=view;
		authorProperty = myView.getAuth();
		modeIndicator = new Label("No mode");
		authorProperty.addListener((ob, oldV, newV) -> {
			if(newV){
				modeIndicator.setText("Author Mode");
			} else {
				modeIndicator.setText("Player Mode");
			}
		});
		pausedIndicator = new Label("Play or pause??");
		
		indicatorHolder = new VBox();
		indicatorHolder.getChildren().addAll(pausedIndicator, modeIndicator);
	}
	

	
	public Node getRoot(){
		return indicatorHolder;
	}
	

}
