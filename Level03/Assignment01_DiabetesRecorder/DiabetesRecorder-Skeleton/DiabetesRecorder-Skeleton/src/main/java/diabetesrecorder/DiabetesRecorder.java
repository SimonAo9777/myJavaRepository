package diabetesrecorder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jdbc.Controller;
import jdbc.JDBCController;
import jdbc.JDBCModel;
import jdbc.url.MySQLURLBuilder;
import jfx.custom.ConnectDialog;
import jfx.custom.ErrorDialog;
import jfx.custom.GlucoseEntry;
import jfx.custom.GlucoseView;
import jfx.custom.LoginDialog;

public class DiabetesRecorder extends Application {

	private static final String ESCAPE_KEY = String.valueOf( (char) 27);
	private static final String CREDIT_TEXT_PATH = "credit.txt";
	private static final String TITLE = "Diabetes Recorder";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private Controller controller;

	private ConnectDialog connectDialog;
	private ErrorDialog errorDialog;
	private LoginDialog loginDialog;

	private BorderPane root;
	private Alert creditDialog;

	@Override
	public void init() throws Exception {
		controller = new JDBCController(new JDBCModel());
		controller.setURLBuilder( new MySQLURLBuilder());

		loginDialog = new LoginDialog( controller);
		connectDialog = new ConnectDialog( controller);
		errorDialog = new ErrorDialog();

		GlucoseEntry glucosEntryTab = new GlucoseEntry( controller, errorDialog).createGUI();
		GlucoseView glucosViewTab = new GlucoseView( controller, errorDialog).createGUI();

		TabPane tabs = new TabPane( glucosEntryTab.getTab(), glucosViewTab.getTab());
		tabs.setId( "glucos_tabs");

		MenuBar menu = createMenuBar();

		root = new BorderPane();
		root.setTop( menu);
		root.setCenter( tabs);
	}

	@Override
	public void start( Stage primaryStage) throws Exception {
		loginDialog.init();
		connectDialog.init();
		errorDialog.init();

		creditDialog = new Alert( AlertType.INFORMATION);
		creditDialog.setContentText( Files.readString( Paths.get( CREDIT_TEXT_PATH)));

		// scene holds all JavaFX components that need to be displayed in Stage
		Scene scene = new Scene( root, WIDTH, HEIGHT);
		scene.getStylesheets().add( "root.css");
		primaryStage.setScene( scene);
		primaryStage.setTitle( TITLE);
		primaryStage.setResizable( true);
		// when escape key is pressed close the application
		primaryStage.addEventHandler( KeyEvent.KEY_TYPED, ( KeyEvent event) -> {

			if ( !event.isConsumed() && ESCAPE_KEY.equals( event.getCharacter())) {
				primaryStage.hide();
			}
		});
		// display the JavaFX application
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		// nothing for now
	}

	private MenuBar createMenuBar() {
		MenuItem connectDB = new MenuItem( "Connect to DB");
		connectDB.setId( "button_database");
		connectDB.setOnAction( event -> {
			if ( connectDialog.showAndWait()) {
				try {
					controller.connect();
					loginDialog.showAndWait();
				} catch ( Exception ex) {
					Logger.getGlobal().log( Level.SEVERE, ex.getMessage(), ex);
					errorDialog.showAndWait( ex);
				}
			}
		});

		MenuItem login = new MenuItem( "Login/Change Account");
		login.setId( "button_login");
		login.setOnAction( event -> {
			try {
				if ( controller.isConnected()) {
					loginDialog.showAndWait();
				} else if ( connectDialog.showAndWait()) {
					controller.connect();
					loginDialog.showAndWait();
				}
			} catch ( Exception ex) {
				Logger.getGlobal().log( Level.SEVERE, ex.getMessage(), ex);
				errorDialog.showAndWait( ex);
			}
		});

		Menu connectMenu = new Menu( "Connect", null, connectDB, login);
		connectMenu.setId( "button_connect");

		MenuItem about = new MenuItem( "About");
		about.setId( "button_about");
		about.setOnAction( e -> creditDialog.showAndWait());

		Menu helpMenu = new Menu( "Help", null, about);
		helpMenu.setId( "button_help");

		return new MenuBar( connectMenu, helpMenu);
	}

	public static void main( String[] args) {
		launch( args);
	}
}
