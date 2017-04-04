package backEnd.Environment;

import java.util.List;

import javafx.geometry.Point2D;

public class TileImpl implements Tile{
	private List<TileAttribute<?>> myAttributes;
	private Point2D myLocation;
	
	public TileImpl(List<TileAttribute<?>> attrList, Point2D location){
		myAttributes = attrList;
		myLocation = location;
	}
	
	public TileAttribute<?> getTileAttribute(String attrType){
		//TODO: How do I access a particular Attribute type?
		return null;
	}
	
	public void addTileAttribute(TileAttribute<?> newAttr){
		myAttributes.add(newAttr);
	}
	
	public List<TileAttribute<?>> getTileAttributeList(){
		return myAttributes;
	}
	
	public void setTileAttributeList(List<TileAttribute<?>> newAttrList){
		myAttributes = newAttrList;
	}

}
