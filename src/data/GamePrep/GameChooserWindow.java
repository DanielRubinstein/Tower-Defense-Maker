package data.GamePrep;

import java.util.function.Consumer;

import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import javafx.stage.Stage;

/**
 * 
 * @author Juan Philippe
 *
 */
public class GameChooserWindow
{
	private ButtonMenuImpl tableContainer;
	
	public GameChooserWindow(Stage stage, Consumer<Object> consumerLoadData)
	{
		tableContainer = new ButtonMenuImpl("See Games and Levels");
		
		addTables();
		addButton(consumerLoadData);
		tableContainer.display(stage);
	}

	private void addButton(Consumer<Object> consumerLoadData)
	{
		
	}

	private void addTables()
	{
		
	}
}
