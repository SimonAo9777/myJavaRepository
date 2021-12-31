package jfx.custom;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public class ErrorDialog {

	private TextArea area;
	private Alert dialog;

	public ErrorDialog() {
		area = new TextArea();
	}

	public void init() {
		dialog = new Alert( AlertType.ERROR);
		createGUI();
	}

	public void showAndWait( Throwable ex) {
		StringBuilder builder = new StringBuilder( ex.getMessage());
		Throwable cause = ex;
		while ( cause.getCause() != null && cause.getCause() != ex) {
			cause = cause.getCause();
			builder.append( System.lineSeparator()).append( System.lineSeparator()).append( "Caused by: ")
					.append( cause.getMessage());

		}
		area.setText( builder.toString());
		dialog.showAndWait();
	}

	private void createGUI() {
		dialog.getDialogPane().setContent( area);
	}
}
