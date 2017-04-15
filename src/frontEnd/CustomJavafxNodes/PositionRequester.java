package frontEnd.CustomJavafxNodes;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;

public class PositionRequester {
	/*
	private List<String> dialogTitles = Arrays.asList("Position Utility", "Please input a location");
	private List<String> promptLabel = Arrays.asList("X Position:", "Y Position:");
	private List<String> promptText = Arrays.asList("0.0", "0.0");
	private DoubleFieldPrompt myDialog;
	
	public PositionRequester(){
		myDialog = new DoubleFieldPrompt(dialogTitles, promptText, promptLabel);
	}
	public Point2D promptUser(){
		List<String> results = myDialog.create();
		return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
	}
	*/
	
	public static Point2D askUserForPosition(){
		List<String> dialogTitles2 = Arrays.asList("Position Utility", "Please input a position");
		List<String> promptLabel2 = Arrays.asList("X Position:", "Y Position:");
		List<String> promptText2 = Arrays.asList("0.0", "0.0");
		DoubleFieldPrompt myDialog2 = new DoubleFieldPrompt(dialogTitles2, promptText2, promptLabel2);
		List<String> results = myDialog2.create();
		return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
	}
}
