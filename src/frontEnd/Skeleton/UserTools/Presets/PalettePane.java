package frontEnd.Skeleton.UserTools.Presets;

import java.util.Collection;

import backEnd.Attribute.AttributeOwner;
import frontEnd.View;
import frontEnd.Skeleton.SkeletonObject;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * This class holds the two palettes (one for components and one for tiles)
 * 
 * It grabs the presets from Bank Controller.
 * 
 * @author Miguel Anderson
 *
 */

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
		
		Collection<? extends AttributeOwner> presets = null;
		
		if (string.equals("Tiles")){
			presets = myView.getBankControllerReader().getAccessibleTilePresets();
		} else if (string.equals("Components")){
			presets = myView.getBankControllerReader().getAccessibleComponentPresets();
		}
		
		Palette singlePalette = new Palette(myView, presets, string);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(singlePalette.getRoot());
		tab.setContent(scrollPane);
		return tab;
	}

	@Override
	public Node getRoot() {
		return myPalettes;
	}

}
