package backEnd.Attribute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ModificationFromUser.AttributeOwner.Modification_Add_StraightToGrid;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;

public class AOObjectExtraction <T> {
	private static final String UNKOWN_ATTRIBUTE_OWNER_ERROR = "Attribute Owner not of known type";
	private static final String DESCRIPTION_ERROR = "AO Interface Casting Error";

	public T cast(AttributeOwner attrOwn) throws Exception{
		try {
			Method add = Modification_Add_StraightToGrid.class.getDeclaredMethod("getObject", attrOwn.getClass());
			add.setAccessible(true);
			return (T) add.invoke(this, attrOwn);
		} catch (NoSuchMethodException e) {
			throw new Exception(UNKOWN_ATTRIBUTE_OWNER_ERROR);
		} catch (Exception e) {
			throw new Exception(DESCRIPTION_ERROR);
		} 
	}
	
	private TileImpl getObject(TileImpl attrOwn){
		return attrOwn;
	}
	
	private Component getObject(Component attrOwn){
		return attrOwn;
	}
	
}
