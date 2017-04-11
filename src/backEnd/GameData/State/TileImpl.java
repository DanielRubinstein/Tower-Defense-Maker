package backEnd.GameData.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.AttributeData;
import backEnd.GameEngine.AttributeFactory;
import backEnd.Mode.GameModeType;
import backEnd.Mode.UserModeType;
import javafx.geometry.Point2D;

public class TileImpl implements Tile, AttributeOwner{
	private final static String DEFAULT_ATTRIBUTE_PATH = "resources/tileDefaults";
	private final static ResourceBundle attributeResources = ResourceBundle.getBundle(DEFAULT_ATTRIBUTE_PATH);
	private Point2D myLocation;
	private AccessPermissions myAccessPerm;
	private AttributeData myAttrData;
	
	public TileImpl(List<GameModeType> gameModeAccessPermissions, List<UserModeType> userModeAccessPermissions , Point2D location){
		this.myLocation = location;
		this.myAccessPerm = new AccessPermissionsImpl(gameModeAccessPermissions, userModeAccessPermissions);
		this.myAttrData = new AttributeData(new HashMap<String,Attribute<?>>());
		AttributeFactory attrFact = new AttributeFactory();
		for (String key : attributeResources.keySet()){
			Attribute<?> myAttribute= attrFact.getAttribute(key);
			addAttribute(key, myAttribute);
		}
		
	}
	
	@Override
	public AccessPermissions getAccessPermissions(){
		return myAccessPerm;
	}
	
	@Override
	public AttributeData getMyAttributes(){
		return myAttrData;
	}
	
	@Override
	public void setAttributeData(AttributeData newAttrData){
		myAttrData = newAttrData;
	}
	
	@Override
	public Point2D getLocation(){
		return myLocation;
	}

	@Override
	public void addAttribute(String name, Attribute<?> value) {
		myAttrData.addAttribute(attributeResources.getString(name), value);
		
	}

	@Override
	public Attribute<?> getAttribute(String name) {
		return myAttrData.get(attributeResources.getString(name));
	}

	@Override
	public Map<String, Attribute<?>> getAttributeMap() {
		return myAttrData.getAttributeMap();
	}

	@Override
	public boolean hasAttribute(String name) {
		return myAttrData.getAttributeMap().containsKey(attributeResources.getString(name));
	}

}
