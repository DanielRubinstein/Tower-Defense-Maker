package frontEnd.Skeleton.UserTools;

import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Component;
import frontEnd.View;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PalettePane implements SkeletonObject {
	private View myView;
	private TabPane palette;
	
	public PalettePane(View view){
		this.myView = view;
		initializePalette();
	}

	private void initializePalette() {
		//http://stackoverflow.com/questions/29085983/create-vertical-tabs-in-tabpane-javafx
		palette = new TabPane();
		palette.setSide(Side.LEFT);
		palette.getTabs().add(createPalette("Tiles"));
		palette.getTabs().add(createPalette("Components"));
		
	}

	private Tab createPalette(String string) {
		Tab tab = new Tab(string);
		tab.setClosable(false);
		
		
		
		
		
		Palette<?> palette = null;
		if (string.equals("Tiles")){
			palette = new Palette<Tile>(myView, myView.getTilePresets());
		} else if (string.equals("Components")){
			palette = new Palette<Component>(myView, myView.getComponentPresets());
		}
		
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(palette.getRoot());
		tab.setContent(scrollPane);
		return tab;
	}

	@Override
	public Node getRoot() {
		return palette;
	}

}
