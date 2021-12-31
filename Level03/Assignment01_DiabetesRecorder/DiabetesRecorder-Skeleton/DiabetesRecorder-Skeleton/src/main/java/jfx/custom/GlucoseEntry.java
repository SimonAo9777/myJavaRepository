package jfx.custom;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import jdbc.Controller;

import static common.CommonGUIBuilder.createTextField;
import static common.CommonGUIBuilder.createComboBox;
import static common.CommonGUIBuilder.createLabel;

public class GlucoseEntry {

	private Tab tab;
	private Controller controller;
	private ErrorDialog errorDialog;

	public GlucoseEntry( Controller controller, ErrorDialog errorDialog) {
		this.controller = controller;
		this.errorDialog = errorDialog;
		tab = new Tab( "Entry");
		tab.setClosable( false);
		tab.setId( "glucose_entry_tab");
	}

	public GlucoseEntry createGUI() {

		Label nameLabel = createLabel( "Name: ", Priority.SOMETIMES);
		TextField nameField = createTextField( null, "Your Name");
		nameField.setDisable( true);
		Label ageLabel = createLabel( "YoB: ", Priority.SOMETIMES);
		TextField ageField = createTextField( null, "Year of Birth");
		ageField.setDisable( true);
		Label weightLabel = createLabel( "Weight: ", Priority.SOMETIMES);
		TextField weightField = createTextField( null, "Weight");
		weightField.setDisable( true);

		controller.bindInfo( nameField.textProperty(), ageField.textProperty(), weightField.textProperty());

		Button editButton = new Button( "Edit");
		editButton.setMinWidth( 75);
		editButton.setMaxWidth( 75);
		editButton.setOnAction( e -> {
			if ( !controller.isConnected() || !controller.isLoggedIn())
				return;
			if ( !nameField.isDisable()) {
				try {
					controller.updateInfo( nameField.getText(), ageField.getText(), weightField.getText());
				} catch ( Exception ex) {
					Logger.getGlobal().log( Level.SEVERE, ex.getMessage(), ex);
					errorDialog.showAndWait( ex);
				}
				editButton.setText( "Edit");
			} else {
				editButton.setText( "Submit");
			}
			nameField.setDisable( !nameField.isDisable());
			ageField.setDisable( !ageField.isDisable());
			weightField.setDisable( !weightField.isDisable());
		});

		Label entryLabel = createLabel( "Entry Type: ", Priority.SOMETIMES);
		ComboBox< String> entryField = createComboBox( controller.getEntryTypes(), "Entry Type", 0);
		Label glucosLabel = createLabel( "Glucose Value: ", Priority.SOMETIMES);
		TextField glucosField = createTextField( null, "Enter a Decimal value");
		Button submit = new Button( "Add Glucose Value");
		submit.setMinWidth( 125);
		submit.setMaxWidth( 125);
		submit.setOnAction( e -> {
			controller.addGlucoseValue( entryField.getSelectionModel().getSelectedItem(),
					Double.valueOf( glucosField.getText()));
			glucosField.setText( "");
			entryField.getSelectionModel().select( 0);
		});

		GridPane grid = new GridPane();
		grid.setHgap( 3);
		grid.setVgap( 3);
		grid.setPadding( new Insets( 5, 5, 5, 5));

		grid.add( nameLabel, 0, 0, 1, 1);
		grid.add( nameField, 1, 0, 1, 1);
		grid.add( ageLabel, 0, 1, 1, 1);
		grid.add( ageField, 1, 1, 1, 1);
		grid.add( weightLabel, 0, 2, 1, 1);
		grid.add( weightField, 1, 2, 1, 1);
		grid.add( editButton, 1, 3, 1, 1);

		grid.add( new Separator(), 0, 5, 5, 1);

		grid.add( entryLabel, 0, 7, 1, 1);
		grid.add( entryField, 1, 7, 1, 1);
		grid.add( glucosLabel, 0, 8, 1, 1);
		grid.add( glucosField, 1, 8, 1, 1);
		grid.add( submit, 1, 9, 1, 1);

		tab.setContent( grid);
		return this;
	}

	public Tab getTab() {
		return tab;
	}
}
