package resources.constants;

import java.util.ResourceBundle;

/**
 * This class holds the constants that we use in the program.
 * @author Daniel
 *
 */

public class NumericResourceBundle {
	
	private static final String BUNDLE_NAME = "resources.constants.numericResourceBundle";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	
	// Animation Constants
	
	public Double getFramesPerSecond(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("FramesPerSecond"));
	}
	
	public Double getMillisecondDelay(){
		return 1000.0 / getFramesPerSecond();
	}
	
	public Double getSecondDelay(){
		return 1.0 / getFramesPerSecond();
	}
	
	//Tile constants
	public Double getPercentTileIsCenter(){
		return Double.valueOf(RESOURCE_BUNDLE.getString("FramesPerSecond"));
	}
	
	//screen setup constants
	private static final String SCREEN_CONSTANTS_PATH = "resources.screen";
	private static final ResourceBundle SCREEN_CONSTANTS_RESOURCE = ResourceBundle.getBundle(SCREEN_CONSTANTS_PATH);

	public Double getWindowHeight(){
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("WindowHeight"));
	}
	
	public Double getWindowWidth(){
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("WindowWidth"));
	}
	
	public Double getScreenPadding(){
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("ScreenGridPadding"));
	}
	
	private Double getScreenGridHeightFactor(){
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("ScreenGridHeightFactor"));
	}
	
	private Double getScreenGridWidthFactor(){
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("ScreenGridWidthFactor"));
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
	public Double getButtonMenuWidth(){
		return getWindowWidth() * buttonWidthFactor();
	}
	
	private Double buttonWidthFactor() {
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("ButtonMenuWidthFactor"));
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
	
	public Double getPresetSizeInPalette(){
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("PresetSizeInPalette"));
	}
}
