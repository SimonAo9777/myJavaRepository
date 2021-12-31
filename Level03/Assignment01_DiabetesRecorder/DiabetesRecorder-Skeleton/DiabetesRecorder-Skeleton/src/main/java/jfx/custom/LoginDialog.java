package jfx.custom;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import static common.CommonGUIBuilder.createPasswordField;
import static common.CommonGUIBuilder.createTextField;
import static common.CommonGUIBuilder.createLabel;

import java.util.Optional;
import jdbc.Controller;

public class LoginDialog {

	/**
	 * Title of the Dialog
	 */
	private static final String TITLE = "Login to Account";
	/**
	 * Default username to display
	 */
	private static final String USERNAME = "cst8288";
	/**
	 * Default password to display
	 */
	private static final String PASSWORD = "8288";

	private Dialog< ButtonType> dialog;
	private Controller controller;

	public LoginDialog( Controller controller) {
		this.controller = controller;
	}

	public void init() {
		dialog = new Dialog<>();
		createGUI();
	}

	public boolean showAndWait() {
		Optional< ButtonType> result = dialog.showAndWait();
		return result.isPresent() && !result.get().getButtonData().isCancelButton();
	}

	private void createGUI() {

		Label description = createLabel( "Input your login credentials");
		TextField usernameField = createTextField( USERNAME, "Username");
		PasswordField passwordField = createPasswordField( PASSWORD, "Password");

		GridPane grid = new GridPane();
		grid.setHgap( 3);
		grid.setVgap( 3);
		grid.setPadding( new Insets( 5, 5, 5, 5));

		grid.add( description, 0, 0);
		grid.add( usernameField, 0, 1);
		grid.add( passwordField, 0, 2);

		ButtonType connectButton = new ButtonType( "Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll( connectButton, ButtonType.CANCEL);
		dialog.getDialogPane().setContent( grid);
		Button btOk = (Button) dialog.getDialogPane().lookupButton( connectButton);
		btOk.addEventFilter( ActionEvent.ACTION, e -> {
			if ( !controller.loginWith( usernameField.getText(), passwordField.getText())) {
				description.setText( "Invalid username and/or password");
				description.setTextFill( Color.RED);
				e.consume();
			}
		});
		dialog.setTitle( TITLE);
	}
}
