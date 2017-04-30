package frontEnd.CustomJavafxNodes;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

public class PositionRequester {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private List<String> dialogTitles = Arrays.asList(stringResourceBundle.getFromStringConstants("PositionMain1"), stringResourceBundle.getFromStringConstants("PositionMain2"));
	private List<String> promptLabel = Arrays.asList(stringResourceBundle.getFromStringConstants("PositionSub1"), stringResourceBundle.getFromStringConstants("PositionSub2"));
	private List<String> promptText = Arrays.asList(stringResourceBundle.getFromStringConstants("PositionInitialValue1"), stringResourceBundle.getFromStringConstants("PositionInitialValue2"));
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
}
