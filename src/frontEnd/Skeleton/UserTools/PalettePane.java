package frontEnd.Skeleton.UserTools;

import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Component;
import frontEnd.ViewReader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PalettePane implements SkeletonObject {
	private ViewReader myView;
	private TabPane palette;
	
	public PalettePane(ViewReader view){
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
			palette = new PaletteImpl<Tile>(myView.getTilePresets());
		} else if (string.equals("Components")){
			palette = new PaletteImpl<Component>(myView.getComponentPresets());
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
