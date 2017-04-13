package frontEnd.Skeleton.UserTools;

import java.util.Collection;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
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
				Collection<Tile> presets = null;
				try{
					presets = myView.getTilePresets();
				} catch (NullPointerException e) {
					System.out.println("No presets here");
				}
				palette = new Palette<Tile>(myView, presets , string);
			} else if (string.equals("Components")){
				Collection<Component> presets = null;
				try{
					presets = myView.getComponentPresets();
				} catch (NullPointerException e) {
					System.out.println("No presets here");
				}
				palette = new Palette<Component>(myView, presets, string);
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
