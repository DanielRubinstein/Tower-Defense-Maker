package backEnd.State;

import java.util.List;

import javafx.geometry.Point2D;

public class TileImpl implements Tile{
	private List<TileAttribute<?>> myAttributes;
	private List<AccessParty> myPermissionAccessList;
	private Point2D myLocation;
	
	public TileImpl(List<TileAttribute<?>> attrList, List<AccessParty> permissionAccessList, Point2D location){
		myAttributes = attrList;
		myLocation = location;
		myPermissionAccessList = permissionAccessList;
	}
	
	public void addAccessPermission(AccessParty newParty){
		myPermissionAccessList.add(newParty);
	}
	
	public boolean permitsAccess(AccessParty party){
		if (myPermissionAccessList.contains(party)){
			return true;
		}
		return false;
	}

	public void addTileAttribute(TileAttribute<?> newAttr){
		if (hasTileAttributeType(newAttr.getType())){
			myAttributes.remove(getAttribute(newAttr.getType()));
		}
		myAttributes.add(newAttr);
	}
	
	public List<TileAttribute<?>> getTileAttributeList(){
		return myAttributes;
	}
	
	public void setTileAttributeList(List<TileAttribute<?>> newAttrList){
		myAttributes = newAttrList;
	}
	
	public boolean hasTileAttributeType(TileAttributeType type){
		for (TileAttribute<?> attr : myAttributes){
			if (attr.getType().equals(type)){
				return true;
			}
		}
		return false;
	}
	
	public TileAttribute<?> getAttribute(TileAttributeType type){
		for (TileAttribute<?> attr : myAttributes){
			if (attr.getType().equals(type)){
				return attr;
			}
		}
		return null;
	}

}
