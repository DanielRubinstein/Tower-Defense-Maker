package backEnd.GameData.State;

import java.util.List;

import javafx.geometry.Point2D;

public class TileImpl implements Tile{
	private List<TileAttributeImpl<?>> myAttributes;
	private Point2D myLocation;
	private AccessPermissions myAccessPerm;
	
	public TileImpl(List<TileAttributeImpl<?>> attrList, AccessPermissionsImpl AccessPerm, Point2D location){
		this.myAttributes = attrList;
		this.myLocation = location;
		this.myAccessPerm = AccessPerm;
	}
	
	@Override
	public AccessPermissions getAccessPermissions(){
		return myAccessPerm;
	}

	@Override
	public void addTileAttribute(TileAttributeImpl<?> newAttr){
		if (hasTileAttributeType(newAttr.getType())){
			myAttributes.remove(getAttribute(newAttr.getType()));
		}
		myAttributes.add(newAttr);
	}
	
	@Override
	public List<TileAttributeImpl<?>> getTileAttributeList(){
		return myAttributes;
	}
	
	@Override
	public void setTileAttributeList(List<TileAttributeImpl<?>> newAttrList){
		myAttributes = newAttrList;
	}
	
	@Override
	public boolean hasTileAttributeType(TileAttributeType type){
		for (TileAttribute<?> attr : myAttributes){
			if (attr.getType().equals(type)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public TileAttribute<?> getAttribute(TileAttributeType type){
		for (TileAttribute<?> attr : myAttributes){
			if (attr.getType().equals(type)){
				return attr;
			}
		}
		return null;
	}

}
