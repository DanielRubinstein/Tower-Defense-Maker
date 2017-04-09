package backEnd.GameData.State;

import java.util.List;

import javafx.geometry.Point2D;

public class TileImpl implements Tile{
	private List<TileAttribute<?>> myAttributes;
	private Point2D myLocation;
	private AccessPermissions myAccessPerm;
	
	public TileImpl(List<TileAttribute<?>> attrList, AccessPermissionsImpl AccessPerm, Point2D location){
		this.myAttributes = attrList;
		this.myLocation = location;
		this.myAccessPerm = AccessPerm;
	}
	
	@Override
	public AccessPermissions getAccessPermissions(){
		return myAccessPerm;
	}

	@Override
	public void addTileAttribute(TileAttribute<?> newAttr){
		if (hasTileAttributeType(newAttr.getType())){
			myAttributes.remove(getAttribute(newAttr.getType()));
		}
		myAttributes.add(newAttr);
	}
	
	@Override
	public List<TileAttribute<?>> getTileAttributeList(){
		return myAttributes;
	}
	
	@Override
	public void setTileAttributeList(List<TileAttribute<?>> newAttrList){
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
