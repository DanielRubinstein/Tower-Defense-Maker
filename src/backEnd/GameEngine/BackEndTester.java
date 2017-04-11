package backEnd.GameEngine;

import com.sun.javafx.geom.Point2D;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeImpl;

public class BackEndTester {

	public static void main(String[] args) {
		Attribute<Point2D> myCoorAttribute=new AttributeImpl<Point2D>();
		System.out.println(myCoorAttribute.getClass().getName());
	}

}
