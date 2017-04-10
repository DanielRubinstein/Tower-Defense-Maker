package frontEnd.Skeleton.UserTools;

import java.util.Collection;

import backEnd.GameEngine.Attribute;
import frontEnd.ViewReader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

/**
 * http://stackoverflow.com/questions/27182323/working-on-creating-image-gallery-in-javafx-not-able-to-display-image-properly
 * 
 * @author Miguel Anderson
 *
 * @param <T>
 */
public class PaletteImpl<T> implements Palette<T>{
	private ViewReader myView;
	private TilePane tile;
	
	public PaletteImpl(Collection<T> objects){
		initializePane();
		
	}

	private void initializePane() {
		tile = new TilePane();
		tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
	}

	@Override
	public Node getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObjects(Collection<T> options) {
		// TODO Auto-generated method stub
		
	}

}
