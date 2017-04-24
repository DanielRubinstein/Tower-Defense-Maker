package data.GamePrep;

import java.util.function.Consumer;

import data.DataController;
import data.XMLReader;
import data.XMLReaderImpl;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Juan Philippe
 *
 */
public class GameChooserWindow
{
	private VBox gameContainer;
	private VBox levelContainer;
	private XMLReader reader;
	private GridPane myRoot;
	
	public GameChooserWindow(Stage stage, Consumer<Object> consumerLoadData)
	{
		reader = new XMLReaderImpl();
		myRoot = new GridPane();
		
		Scene myScene = new Scene(myRoot, 600,  400);
		stage.setScene(myScene);
		addVBoxes();
		addButton(consumerLoadData);
		stage.show();
	}

	private void addButton(Consumer<Object> consumerLoadData)
	{
		ActionButton button = new ActionButton(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				consumerLoadData.accept("");
			}
			
		});
		myRoot.add(button, 2, 2);
	}

	private void addVBoxes() {
		// TODO Auto-generated method stub
		
	}

	
}
