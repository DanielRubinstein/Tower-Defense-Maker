package resources.constants.numeric;

import java.util.ResourceBundle;
import java.util.Set;

public class NumericResourceBundle {
	
	///////////////////////////////////////////////////////////////////////////
	// Screen
	///////////////////////////////////////////////////////////////////////////
	
	private static final ScreenConstants SCREEN_CONSTANTS = new ScreenConstants();
	
	public ScreenConstants getScreenConstants(){
		return SCREEN_CONSTANTS;
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Engine
	///////////////////////////////////////////////////////////////////////////
	
	private static final String ENGINE_BUNDLE_NAME = "resources.constants.numeric.engine";
	private static final ResourceBundle ENGINE_RESOURCE_BUNDLE = ResourceBundle.getBundle(ENGINE_BUNDLE_NAME);
	
	public Double getFramesPerSecond(){
		return Double.valueOf(ENGINE_RESOURCE_BUNDLE.getString("FramesPerSecond"));
	}
	
	public Double getMillisecondDelay(){
		return 1000.0 / getFramesPerSecond();
	}
	
	public Double getSecondDelay(){
		return 1.0 / getFramesPerSecond();
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Sizing
	///////////////////////////////////////////////////////////////////////////
	
	private static final String SIZING_BUNDLE_NAME = "resources.constants.numeric.sizing";
	private static final ResourceBundle SIZING_RESOURCE_BUNDLE = ResourceBundle.getBundle(SIZING_BUNDLE_NAME);
	
	public Double getFromSizing(String str){
		return Double.valueOf(SIZING_RESOURCE_BUNDLE.getString(str));
	}
	
	public Set<String> getKeysFromSizing(){
		return SIZING_RESOURCE_BUNDLE.keySet();
	}
	
}
