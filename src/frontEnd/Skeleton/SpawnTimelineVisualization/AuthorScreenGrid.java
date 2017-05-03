package frontEnd.Skeleton.SpawnTimelineVisualization;

import frontEnd.View;
import frontEnd.Skeleton.SkeletonObject;
import frontEnd.Skeleton.ScreenGrid.ScreenGrid;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import resources.constants.StringResourceBundle;

public class AuthorScreenGrid implements SkeletonObject{
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	
	private TabPane myRoot;
	private View myView;
	private ScreenGrid myScreenGrid;
	private Tab screenGridTab;
	
	public AuthorScreenGrid(View view, ScreenGrid screenGrid) {
		myView = view;
		myScreenGrid = screenGrid;
		myRoot = new TabPane();
		myRoot.setOnKeyPressed(e -> e.consume());
		myRoot.setOnKeyTyped(e -> e.consume());
		createGridTab();
		
		createSpawnTab();
	}

	private void createSpawnTab() {
		Tab spawnTabs = new Tab(stringResourceBundle.getFromStringConstants("AuthorScreenGrid.0")); //$NON-NLS-1$
		spawnTabs.setClosable(false);
		SpawnTabPane sTP = new SpawnTabPane(myView);
		spawnTabs.setContent(sTP.getRoot());
		myRoot.getTabs().add(spawnTabs);
	}

	

	private void createGridTab() {
		screenGridTab = new Tab(stringResourceBundle.getFromStringConstants("AuthorScreenGrid.1")); //$NON-NLS-1$
		screenGridTab.setContent(myScreenGrid.getRoot());
		screenGridTab.setClosable(false);
		myRoot.getTabs().add(0, screenGridTab);
	}

	@Override
	public Node getRoot(){
		// force refresh, not sure why this needs to be done
		myRoot.getTabs().remove(screenGridTab); 
		createGridTab();
		myRoot.getSelectionModel().select(screenGridTab);
		return myRoot;
	}

}
