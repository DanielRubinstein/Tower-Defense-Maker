package frontEnd.Skeleton.UserTools;

import java.io.File;
import java.net.MalformedURLException;

import frontEnd.Menus.ButtonMenu;
import frontEnd.Menus.ButtonMenuImpl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class HelpOptions {

	
	private static final String DEFAULT_TOWER_DEFENSE = "src/resources/html/tower_defense.htm";
	private ButtonMenu allOptions;
	public HelpOptions(){
		allOptions = new ButtonMenuImpl("Help");
		addAllOptions();
		allOptions.display(new Stage());
	}
	public void launch(){
		
		
		
	}
	private void addAllOptions(){
		allOptions.addSimpleButton("What is Tower Defense?",e -> loadHTMLPage(DEFAULT_TOWER_DEFENSE));
	}
	
	
	private void loadHTMLPage(String path){
		try {
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			webEngine.load(new File(path).toURI().toURL().toExternalForm());
			browser.autosize();
			Stage dialog = new Stage();
			Scene scene = new Scene(browser, browser.getPrefWidth(), browser.getPrefHeight());
			dialog.setScene(scene);
			dialog.show();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
