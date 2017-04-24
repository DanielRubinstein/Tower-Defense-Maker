package data.GamePrep;

import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Button;
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
	private DataInputLoader reader;
	private GridPane myRoot;
	private Map<String, List<String>> gamesData;
	private String currentLevel;
	
	public GameChooserWindow(Stage stage, Consumer<Object> consumerLoadData)
	{
		reader = new DataInputLoader();
		gamesData = reader.getGamesMap();
		
		myRoot = new GridPane();
		Scene myScene = new Scene(myRoot, 600,  400);
		stage.setScene(myScene);
		
		addVBoxes();
		addButton(consumerLoadData);
		
		stage.show();
	}

	private void addButton(Consumer<Object> consumerLoadData)
	{
		ActionButton button = new ActionButton("Load Game", new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				consumerLoadData.accept(currentLevel);
			}
			
		});
		myRoot.add(button, 2, 2);
	}

	private void addVBoxes()
	{
		createGameBox();
		createLevelBox();
	}

	private void createLevelBox()
	{
		levelContainer = new VBox();
		myRoot.add(levelContainer, 5, 0);
	}

	private void createGameBox() {
		gameContainer = new VBox();
		
		for (String name : gamesData.keySet())
		{
			gameContainer.getChildren().add(new ActionButton(name, () -> generateLevels(name)));
		}
		myRoot.add(gameContainer, 0, 0);
	}

	private void generateLevels(String gameName)
	{
		levelContainer.getChildren().clear();
		
		for (String level : gamesData.get(gameName))
		{
			levelContainer.getChildren().add(new ActionButton(level, () -> currentLevel = level));
		}
	}
}
