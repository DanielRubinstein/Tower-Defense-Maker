package frontEnd.Skeleton.UserTools;

import static org.junit.Assert.*;

import org.junit.Test;

public class BottomRootTest {

	@Test
	public void testHeight() {
		BottomRoot myRoot = new BottomRoot();
		myRoot.setHeight(100.0);
		//allow some error
		assertEquals(100.0,myRoot.getRoot().prefHeight(0),0.1);
	
	}
	

}
