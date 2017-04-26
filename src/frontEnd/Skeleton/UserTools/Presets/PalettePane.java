package frontEnd.Skeleton.UserTools.Presets;

import java.util.Map;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PalettePane implements SkeletonObject {
	private View myView;
	private TabPane myPalettes;
	
	public PalettePane(View view){
		this.myView = view;
		initializePalette();
	}

	private void initializePalette() {
		//http://stackoverflow.com/questions/29085983/create-vertical-tabs-in-tabpane-javafx
		myPalettes = new TabPane();
		myPalettes.setSide(Side.LEFT);
		myPalettes.getTabs().add(createPalette("Components"));
		myPalettes.getTabs().add(createPalette("Tiles"));		
	}

	private Tab createPalette(String string) {
		Tab tab = new Tab(string);
		tab.setClosable(false);
		
		Palette singlePalette = null;
		
		if (string.equals("Tiles")){
			Map<String, Tile> presets = null;
			presets = myView.getBankController().getAccessibleTileMap();
			singlePalette = new Palette(myView, presets , string);
		} else if (string.equals("Components")){
			Map<String, Component> presets = null;
			presets = myView.getBankController().getAccessibleComponentMap();
			singlePalette = new Palette(myView, presets, string);
		}

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(singlePalette.getRoot());
		tab.setContent(scrollPane);
		return tab;
	}

	@Override
	public Node getRoot() {
		return myPalettes;
	}

}
