package backEnd.GameEngine;

import com.sun.javafx.geom.Point2D;

public class AttributeFactory {
	public Attribute<?> getAttribute(String key, String value){
		switch (key) {
		case "Coordinates":
			Attribute<Point2D> myCoorAttribute=new Attribute<Point2D>();
			int x=Integer.parseInt(value.split(",")[0]);
			int y=Integer.parseInt(value.split(",")[1]);
			myCoorAttribute.setValue(new Point2D(x, y));
			return myCoorAttribute;
		case "MoveDirection":
			Attribute<String> myDirAttribute=new Attribute<String>();
			myDirAttribute.setValue(value);
			return myDirAttribute;
		case "OnCollision":
			Attribute<String> myColAttribute=new Attribute<String>();
			myColAttribute.setValue(value);
			return myColAttribute;
		case "Health":
			Attribute<Integer> myHealthAttribute=new Attribute<Integer>();
			myHealthAttribute.setValue(Integer.parseInt(value));
			return myHealthAttribute;
		default: throw new IllegalArgumentException(); //FIX THIS	
		}
	}

}
