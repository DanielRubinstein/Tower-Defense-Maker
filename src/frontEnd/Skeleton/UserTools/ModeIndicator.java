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
		authorProperty = myView.getBooleanAuthorModeProperty();
		modeIndicator = new Label();
		setIndicator(authorProperty.getValue());
		authorProperty.addListener((ob, oldV, newV) -> {
			setIndicator(newV);
		});
		pausedIndicator = new Label("Game Status Indicator");
		
		indicatorHolder = new VBox();
		indicatorHolder.getChildren().addAll(pausedIndicator, modeIndicator);
	}
	

	
	private void setIndicator(Boolean newV) {
		if(newV){
			modeIndicator.setText("Author Mode");
		} else {
			modeIndicator.setText("Player Mode");
		}
	}



	public Node getRoot(){
		return indicatorHolder;
	}
	

}
