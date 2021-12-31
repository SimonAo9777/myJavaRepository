package jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.url.JDBCUrl;

/**
 * 
 * @author Simon Ao 040983402
 * @version July 4,2021
 * @References: Professor's tutorials
 *
 *
 */

public class JDBCController implements Controller {

	private JDBCUrl builder;
	private JDBCModel model;
	private boolean isLoggedIn;
	private int activeAccountId;
	private StringProperty name;
	private StringProperty yob;
	private StringProperty weight;
	private BooleanProperty updateTable = new SimpleBooleanProperty();
	private ObservableList<String> entryTypes;

	public JDBCController(JDBCModel model) {
		this.model = model;
	}

	@Override
	public JDBCController setURLBuilder(JDBCUrl builder) {
		this.builder = builder;
		return this;
	}

	@Override
	public JDBCController setDataBase(String address, String port, String catalog) {
		builder.setURL(address, port, catalog);
		return this;
	}

	@Override
	public JDBCController addConnectionURLProperty(String key, String value) {
		this.builder.addURLProperty(key, value);
		return this;
	}

	@Override
	public JDBCController setCredentials(String user, String pass) {
		model.setCredentials(user, pass);
		return this;
	}

	@Override
	public JDBCController connect() {
		String url = builder.getURL();
		model.connectTo(url);
		return this;
	}

	@Override
	public boolean isConnected() {
		return model.isConnected();
	}

	@Override
	public List<List<Object>> findAllGlucoseNumbersForLoggedAccount() {
		return model.getAllGlucoseNumbers(activeAccountId);
	}

	@Override
	public List<String> getColumnNamesOfGlucosEntries() {
		return model.getColumnNames();
	}

	@Override
	public void updateInfo(String name, String yob, String weight) {
		model.updateInfo(name, yob, weight, activeAccountId);
	}

	@Override
	public void addGlucoseValue(String typeName, double glucose) {
		updateTable.set(false);
		model.addGlucoseValue(typeName, activeAccountId, glucose);
		updateTable.set(true);
	}

	@Override
	public boolean isLoggedIn() {
		return activeAccountId > 0;
	}

	@Override
	public void bindInfo(StringProperty name, StringProperty yob, StringProperty weight) {
		this.name = name;
		this.yob = yob;
		this.weight = weight;
	}

	@Override
	public void addTableListener(ChangeListener<Boolean> change) {
		updateTable.addListener(change);
	}

	@Override
	public boolean loginWith(String username, String password) {
		activeAccountId = model.loginWith(username, password);
		if (isLoggedIn()) {
			List<List<String>> list = model.getAccountInfoFor(activeAccountId);
			List<String> values = list.get(0);
			name.setValue(values.get(0));
			yob.setValue(values.get(1));
			weight.setValue(values.get(2));
			List<String> types = model.getEntryTypes();
			entryTypes.clear();
			entryTypes.addAll(types);
		}
		return isLoggedIn();
	}

	@Override
	public ObservableList<String> getEntryTypes() {
		List<String> types = new ArrayList<>();
		entryTypes = FXCollections.observableList(types);
		return entryTypes;
	}

	public void hasValidLogin() throws SQLException {
		if (!isLoggedIn)
			throw new SQLException(" No login.");
	}

	@Override
	public void close() throws Exception {
		model.close();
	}

}
