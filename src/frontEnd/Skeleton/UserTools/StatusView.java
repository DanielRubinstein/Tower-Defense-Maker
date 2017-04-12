package frontEnd.Skeleton.UserTools;

import backEnd.GameData.UserAttribute;
import frontEnd.ViewReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

public class StatusView {
	private ViewReader myView;
	private TableView<UserAttribute> myRoot;
	private ObservableList<UserAttribute> userAttributes;

	public StatusView(ViewReader view){
		myView = view;
		userAttributes = FXCollections.observableArrayList(myView.getUserAttributes());
		myRoot = createVariableView();
	}
	
	private TableView<UserAttribute> createVariableView() {
		// http://docs.oracle.com/javafx/2/ui_controls/table-view.htm#CJAGDAHE
		// http://stackoverflow.com/questions/20020037/editing-a-number-cell-in-a-tableview
		TableView<UserAttribute> tableUserAttribute = new TableView<UserAttribute>();
		TableColumn<UserAttribute, String> attriNameCol = new TableColumn<UserAttribute, String>("Name");
		attriNameCol.setCellValueFactory(new PropertyValueFactory<UserAttribute, String>("Name"));

		TableColumn<UserAttribute, Double> attriValueCol = new TableColumn<UserAttribute, Double>("Value");
		attriValueCol.setCellValueFactory(new PropertyValueFactory<UserAttribute, Double>("Value"));
		attriValueCol
				.setCellFactory(TextFieldTableCell.<UserAttribute, Double>forTableColumn(new DoubleStringConverter()));


		tableUserAttribute.getColumns().addAll(attriNameCol, attriValueCol);
		tableUserAttribute.setItems(userAttributes);
		return tableUserAttribute;
	}

	public Node getRoot(){
		return this.myRoot;
	}

	public void setWidth(double in) {
		myRoot.setMinWidth(in);
		myRoot.setPrefWidth(in);
	}
}
;