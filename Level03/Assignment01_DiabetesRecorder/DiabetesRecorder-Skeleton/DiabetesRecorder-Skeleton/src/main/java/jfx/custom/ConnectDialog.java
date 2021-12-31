package jfx.custom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import static common.CommonGUIBuilder.createButton;
import static common.CommonGUIBuilder.createComboBox;
import static common.CommonGUIBuilder.createListView;
import static common.CommonGUIBuilder.createPasswordField;
import static common.CommonGUIBuilder.createTextField;

import java.util.Optional;

import jdbc.Controller;

public class ConnectDialog {

	/**
	 * title of the Dialog
	 */
	private static final String TITLE = "Connect To";
	/**
	 * Default username to the DB
	 */
	private static final String USERNAME = "cst8288";
	/**
	 * Default password to the DB
	 */
	private static final String PASSWORD = "8288";
	/**
	 * Default host to the DB
	 */
	private static final String HOST = "localhost";
	/**
	 * Default port to the DB
	 */
	private static final String PORT = "3306";
	/**
	 * Default database name to the DB
	 */
	private static final String DATABASE = "diabetesrecord";

	private Dialog< ButtonType> dialog;
	private Controller controller;
	private TextField hostText;
	private TextField portText;
	private TextField dbNameText;
	private TextField userText;
	private PasswordField passText;
	private ObservableList< Pair< String, String>> properties;

	public ConnectDialog( Controller controller) {
		this.controller = controller;
		properties = FXCollections.observableArrayList();
	}

	public void init() {
		dialog = new Dialog<>();
		createGUI();
	}

	public boolean showAndWait() {
		Optional< ButtonType> result = dialog.showAndWait();
		if ( result.isPresent() && !result.get().getButtonData().isCancelButton()) {
			controller.setDataBase( hostText.getText(), portText.getText(), dbNameText.getText())
					.addConnectionURLProperty( "serverTimezone", "UTC").addConnectionURLProperty( "useUnicode", "true")
					.setCredentials( userText.getText(), passText.getText());
			return true;
		}
		return false;
	}

	private void createGUI() {
		dialog.setTitle( TITLE);

		ComboBox< String> dbTypeCombo = createComboBox( FXCollections.observableArrayList( "mysql"), "DB Type", 0);
		hostText = createTextField( HOST, "Host Name");
		portText = createTextField( PORT, "Port Number");
		dbNameText = createTextField( DATABASE, "DB Name");
		userText = createTextField( USERNAME, "Username");
		passText = createPasswordField( PASSWORD, "Password");
		TextField keyText = createTextField( "serverTimezone", "Key");
		TextField valueText = createTextField( "UTC", "Value");
		ListView< Pair< String, String>> propertiesList = createListView( properties, 165);

		Button addProperty = createButton( "Add",
				e -> properties.add( new Pair<>( keyText.getText(), valueText.getText())));
		ButtonType connectButton = new ButtonType( "Connect", ButtonData.OK_DONE);

		GridPane grid = new GridPane();
		grid.setHgap( 3);
		grid.setVgap( 3);
		grid.setPadding( new Insets( 5, 5, 5, 5));

		grid.add( dbTypeCombo, 0, 0);
		grid.add( hostText, 0, 1);
		grid.add( portText, 0, 2);
		grid.add( dbNameText, 0, 3);
		grid.add( userText, 0, 4);
		grid.add( passText, 0, 5);
		grid.add( propertiesList, 1, 0, 2, 6);
		grid.add( keyText, 0, 7);
		grid.add( valueText, 1, 7);
		grid.add( addProperty, 2, 7);

		dialog.getDialogPane().getButtonTypes().addAll( connectButton, ButtonType.CANCEL);
		dialog.getDialogPane().setContent( grid);
	}
}
