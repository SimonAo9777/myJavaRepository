package common;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * A set of common method for creating some GUI components. This is bad practice we usually don't want to do this. By
 * using FXML we can remove this class. However, for our need this is sufficient. This is a utility class with bunch of
 * static methods for creating some common JavaFX nodes.
 * 
 * @author Shariar (Shawn) Emami
 */
public interface CommonGUIBuilder {

	public static Button createButton( String name, EventHandler< ActionEvent> onClick) {
		return createButton( name, onClick, Priority.ALWAYS, Priority.ALWAYS);
	}

	public static Button createButton( String name, EventHandler< ActionEvent> onClick, Priority pH, Priority pV) {
		Button node = new Button( name);
		node.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE);
		node.setOnAction( onClick);
		if ( pH != null)
			GridPane.setHgrow( node, pH);
		if ( pV != null)
			GridPane.setVgrow( node, pV);
		return node;
	}

	public static TextField createTextField( String value, String promptText) {
		return createTextField( value, promptText, Priority.ALWAYS);
	}

	public static TextField createTextField( String value, String promptText, Priority p) {
		TextField node = new TextField( value);
		node.setPromptText( promptText);
		if ( p != null)
			GridPane.setHgrow( node, p);
		return node;
	}

	public static PasswordField createPasswordField( String value, String promptText) {
		return createPasswordField( value, promptText, Priority.ALWAYS);
	}

	public static PasswordField createPasswordField( String value, String promptText, Priority p) {
		PasswordField node = new PasswordField();
		node.setText( value);
		node.setPromptText( promptText);
		if ( p != null)
			GridPane.setHgrow( node, p);
		return node;
	}

	public static Label createLabel( String value) {
		return createLabel( value, Priority.ALWAYS);
	}

	public static Label createLabel( String value, Priority p) {
		Label node = new Label( value);
		if ( p != null)
			GridPane.setHgrow( node, p);
		return node;
	}

	public static < T> ComboBox< T> createComboBox( ObservableList< T> value, String promptText, int selectedIndex) {
		return createComboBox( value, promptText, selectedIndex, Priority.ALWAYS);
	}

	public static < T> ComboBox< T> createComboBox( ObservableList< T> value, String promptText, int selectedIndex, Priority p) {
		ComboBox< T> node = new ComboBox<>( value);
		node.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE);
		node.setPromptText( promptText);
		node.getSelectionModel().select( selectedIndex);
		GridPane.setHgrow( node, p);
		return node;
	}

	public static < T> ListView< T> createListView( ObservableList< T> list, double height) {
		ListView< T> node = new ListView<>( list);
		node.setMaxHeight( height);
		GridPane.setHgrow( node, Priority.ALWAYS);
		return node;
	}
}
