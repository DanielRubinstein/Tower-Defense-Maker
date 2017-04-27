package resources.constants;

import java.util.ResourceBundle;

/**
 * This class holds the constants that we use in the program.
 * @author Daniel
 *
 */

public class NumericConstants {
	
	private static final String BUNDLE_NAME = "resources.numericConstants";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	
	// Animation Constants
	
	public Double getFramesPerSecond(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("FramesPerSecond"));
	}
	
	public Double getMillisecondDelay(){
		return 1000 / getFramesPerSecond();
	}
	
	public Double getSecondDelay(){
		return 1.0 / getFramesPerSecond();
	}
	
	//screen setup constants
	
	public Double getWindowHeight(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("WindowHeight"));
	}
	
	public Double getWindowWidth(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("WindowWidth"));
	}
	
	public Double getScreenPadding(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("ScreenPadding"));
	}
	
	private Double getScreenGridHeightFactor(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("ScreenGridHeightFactor"));
	}
	
	private Double getScreenGridWidthFactor(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("ScreenGridWidthFactor"));
	}
	
	private Double getBottomHeightFactor(){
		return 1 - getScreenGridHeightFactor();
	}
	
	private Double getSideWidthFactor(){
		return 1 - getScreenGridWidthFactor();
	}
	
	public Double getScreenGridWidth(){
		return getWindowWidth() * getScreenGridWidthFactor() - getScreenPadding();
	}
	
	public Double getScreenGridHeight(){
		return getWindowHeight() * getScreenGridHeightFactor() - getScreenPadding();
	}
	
	public Double getBottomHeight(){
		return getBottomHeightFactor() * getWindowHeight();
	}
	
	public Double getSideWidth(){
		return getSideWidthFactor() * getWindowWidth();
	}
}
