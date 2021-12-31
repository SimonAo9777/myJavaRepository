package jfx.custom;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import jdbc.Controller;

public class GlucoseView {

	private Tab tab;
	private Controller controller;
	private ErrorDialog errorDialog;
	private TableView< Object> table;

	public GlucoseView( Controller controller, ErrorDialog errorDialog) {
		this.controller = controller;
		this.errorDialog = errorDialog;
		tab = new Tab( "View");
		tab.setClosable( false);
		tab.setId( "glucose_view_tab");
	}

	public GlucoseView createGUI() {
		table = new TableView<>();
		table.setPlaceholder( new Label( "No Data"));

		tab.setOnSelectionChanged( e -> updateTable());
		tab.setContent( table);

		controller.addTableListener( ( v, o, n) -> updateTable());
		return this;
	}

	private void updateTable() {
		if ( tab.isSelected()) {
			try {
				if ( controller.isConnected() && controller.isLoggedIn()) {
					populateTable( controller.findAllGlucoseNumbersForLoggedAccount());
				} else {
					table.setPlaceholder( new Label( "No Data"));
				}
			} catch ( Exception ex) {
				Logger.getGlobal().log( Level.SEVERE, ex.getMessage(), ex);
				errorDialog.showAndWait( ex);
			}
		}
	}

	private void populateTable( List< List< Object>> list) {
		table.getItems().clear();
		table.getItems().addAll( list);
		table.getColumns().clear();
		int i = 0;
		for ( String col : controller.getColumnNamesOfGlucosEntries()) {
			TableColumn< Object, Object> tc = new TableColumn<>( col);

			int index = i;
			tc.setCellValueFactory( ( CellDataFeatures< Object, Object> data) -> new SimpleObjectProperty<>(
					( (List< ?>) data.getValue()).get( index)));
			i++;
			table.getColumns().add( tc);
		}
	}

	public Tab getTab() {
		return tab;
	}
}
