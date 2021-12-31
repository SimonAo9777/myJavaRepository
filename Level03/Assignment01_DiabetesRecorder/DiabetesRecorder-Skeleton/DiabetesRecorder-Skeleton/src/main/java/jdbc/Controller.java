package jdbc;

import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import jdbc.url.JDBCUrl;

public interface Controller extends AutoCloseable {

	boolean loginWith( String username, String password);

	Controller setURLBuilder( JDBCUrl builder);

	Controller setDataBase( String address, String port, String catalog);

	Controller addConnectionURLProperty( String key, String value);

	Controller setCredentials( String user, String pass);

	Controller connect();

	List< String> getColumnNamesOfGlucosEntries();

	List< List< Object>> findAllGlucoseNumbersForLoggedAccount();

	void bindInfo( StringProperty name, StringProperty yob, StringProperty weight);

	void addTableListener( ChangeListener< Boolean> change);

	boolean isConnected();

	boolean isLoggedIn();

	void updateInfo( String name, String yob, String weight);

	ObservableList< String> getEntryTypes();
	
	void addGlucoseValue( String string, double glucose);
}
