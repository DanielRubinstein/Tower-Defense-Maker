package data.GamePrep;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import frontEnd.CustomJavafxNodes.ActionButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import resources.Constants;

/**
 * 
 * @author Juan Philippe
 *
 */
public class GameChooserWindow
{
	private VBox overallContainer;
	private HBox selectorContainer;
	private VBox gameContainer;
	private VBox levelContainer;
	private DataInputLoader reader;
	
	private Map<String, List<String>> gamesData;
	private String currentLevel;
	
	public GameChooserWindow(Stage stage, Consumer<Object> consumerLoadData)
	{
		reader = new DataInputLoader();
		gamesData = reader.getGamesMap();
		
		overallContainer = new VBox();
		
		populateOverallContainer(consumerLoadData);
		
		Scene myScene = new Scene(overallContainer, 600,  400);
		myScene.getStylesheets().add(Constants.DEFAULT_CSS);
		stage.setScene(myScene);
		
		stage.show();
	}

	private void populateOverallContainer(Consumer<Object> consumerLoadData)
	{
		selectorContainer = new HBox();
		overallContainer.getChildren().add(selectorContainer);
		createGameBox();
		createLevelBox();
		addButton(consumerLoadData);
	}

	private void addButton(Consumer<Object> consumerLoadData)
	{
		ActionButton button = new ActionButton("Load Game", new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				consumerLoadData.accept(currentLevel);
			}
			
		});
		overallContainer.getChildren().add(button);
	}

	private void createLevelBox()
	{
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setPrefHeight(300);
		levelContainer = new VBox();
		levelContainer.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		
		scroll.setContent(levelContainer);
		selectorContainer.getChildren().add(scroll);
		
	}

	private void createGameBox() {
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setPrefHeight(300);
		gameContainer = new VBox();
		gameContainer.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		
		scroll.setContent(gameContainer);
		selectorContainer.getChildren().add(scroll);
		
		for (String name : gamesData.keySet())
		{
			gameContainer.getChildren().add(new ActionButton(name, () -> generateLevels(name)));
		}
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
