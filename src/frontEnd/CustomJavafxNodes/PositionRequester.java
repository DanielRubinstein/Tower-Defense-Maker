package frontEnd.CustomJavafxNodes;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;

public class PositionRequester {
	
	private static final List<String> dialogTitles = Arrays.asList("Position Utility", "Please input a location");
	private static final List<String> promptLabel = Arrays.asList("X Position:", "Y Position:");
	private static final List<String> promptText = Arrays.asList("0.0", "0.0");
	private DoubleFieldPrompt myDialog;
	
	public PositionRequester(){
		myDialog = new DoubleFieldPrompt(dialogTitles, promptText, promptLabel);
	}
	public Point2D promptUser(Point2D oldPoint){
		List<String> results = myDialog.create();
		try{
			return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
		} catch (NullPointerException e){
			return oldPoint;
		}
	}
	
	
	/*
	
	public static Point2D askUserForPosition(){
		return askUserForPosition(new Point2D(0,0));
	}
	
	public static Point2D askUserForPosition(Point2D oldPoint){
		List<String> dialogTitles2 = Arrays.asList("Position Utility", "Please input a position");
		List<String> promptLabel2 = Arrays.asList("X Position:", "Y Position:");
		List<String> promptText2 = Arrays.asList("0.0", "0.0");
		DoubleFieldPrompt myDialog2 = new DoubleFieldPrompt(dialogTitles2, promptText2, promptLabel2);
		List<String> results = myDialog2.create();
		try{
			return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
		} catch (NullPointerException e){
			return oldPoint;
		}
	}
	
	*/
}
