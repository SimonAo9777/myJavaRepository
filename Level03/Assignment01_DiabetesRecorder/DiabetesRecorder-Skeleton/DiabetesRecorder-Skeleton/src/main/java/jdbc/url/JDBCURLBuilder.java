package jdbc.url;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class JDBCURLBuilder implements JDBCUrl {

	protected static final String JDBC = "jdbc";

	protected String portNumber;
	protected String dbTypeName;
	protected String hostAddress;
	protected String databaseName;
	protected Map< String, String> properties;

	protected JDBCURLBuilder() {
		properties = new HashMap<>();
	}

	@Override
	public void setURL( String host, String port, String dbName) {
		Objects.requireNonNull( host, "Host address cannot be null");
		Objects.requireNonNull( port, "port cannot be null");
		Objects.requireNonNull( dbName, "DataBase name cannot be null");
		this.hostAddress = host.trim();
		this.portNumber = port.trim();
		this.databaseName = dbName.trim();
	}

	@Override
	public void setURL( String host, int port, String dbName) {
		setURL( host, String.valueOf( port), dbName);
	}

	@Override
	public void addURLProperty( String key, String value) {
		Objects.requireNonNull( key, "key cannot be null");
		Objects.requireNonNull( value, "value cannot be null");
		properties.put( key, value);
	}

	protected void setDBType( String dbType) {
		Objects.requireNonNull( dbType, "DB type cannot be null");
		this.dbTypeName = dbType;
	}
}
