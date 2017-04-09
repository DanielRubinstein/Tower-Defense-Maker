package frontEnd.Skeleton.UserTools;

import frontEnd.ViewReader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusView {
	private VBox myRoot;
	
	private Double score;
	private Double lives;
	private Double cash;
	private Double levelProgress;
	private Double totalKills;
	private Double levelCompletion;

	public StatusView(ViewReader view){
		
		// TODO read SHIT
		
		myRoot = new VBox();
		
		
		score = 0d;
		lives = 1d;
		cash = 10000d;
		
		
		addLabelForValue("Score", score);
		addLabelForValue("Lives", lives);
		addLabelForValue("Cash", cash);
		
	}
	
	private void addLabelForValue(String title, Double value) {
		Label lbl = new Label();
		lbl.setText(String.format(title + ": %.0f%n", value));
		
		myRoot.getChildren().add(lbl);
		
	}

	public Node getRoot(){
		return this.myRoot;
	}
}
