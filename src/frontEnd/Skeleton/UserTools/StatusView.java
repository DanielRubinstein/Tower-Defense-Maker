package frontEnd.Skeleton.UserTools;

import java.util.Arrays;
import java.util.List;

import frontEnd.ViewReader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class StatusView {
	private ViewReader myView;
	private GridPane grid;

	public StatusView(ViewReader view){
		myView = view;
		// FIXME get from backEnd
		List<String> a = Arrays.asList("Score", "Lives", "Health");
		init();
        createTitleAndVertSep(a.size());
        int counter = 1;
        for (String str : a){
            createLabels(counter, str);
            counter += 2;
        }
	}

	private void createLabels(int counter, String str) {
		Label friday = new Label(str);
		friday.setFont(Font.font("Verdana", 18));
		GridPane.setConstraints(friday, 0, counter);
		GridPane.setColumnSpan(friday, 3);
		grid.getChildren().add(friday);
		
		Label value = new Label("0.0");
		value.setFont(Font.font("Verdana", 18));
		GridPane.setConstraints(value, 4, counter);
		GridPane.setColumnSpan(value, 3);
		grid.getChildren().add(value);
		
		final Separator sepHor = new Separator();
		sepHor.setValignment(VPos.CENTER);
		GridPane.setConstraints(sepHor, 0, counter + 1);
		GridPane.setColumnSpan(sepHor, 6);
		grid.getChildren().add(sepHor);
	}

	private void createTitleAndVertSep(int numOfValues) {
		Label caption = new Label("User Status");
        caption.setFont(Font.font("Verdana", 20));
        caption.setUnderline(true);
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 6);
        GridPane.setHalignment(caption, HPos.CENTER);
        grid.getChildren().add(caption);
        
        final Separator sepVert1 = new Separator();
        sepVert1.setOrientation(Orientation.VERTICAL);
        sepVert1.setValignment(VPos.CENTER);
        //sepVert1.setPrefHeight(300);
        GridPane.setConstraints(sepVert1, 3, 1);
        GridPane.setRowSpan(sepVert1, numOfValues * 2 + 1);
        grid.getChildren().add(sepVert1);
	}

	private void init() {
		grid = new GridPane();
		grid.setPadding(new Insets(2, 2, 2, 2));
        grid.setVgap(2);
        grid.setHgap(2);
	}

	public Node getRoot(){
		return this.grid;
	}

	public void setWidth(double in) {
		grid.setMinWidth(in);
		grid.setPrefWidth(in);
		grid.setMaxWidth(in);
	}
}
;