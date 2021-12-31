package jdbc.url;

public interface JDBCUrl {
	
	void setURL( String host, String port, String dbName);
	
	void setURL( String host, int port, String dbName);

	void addURLProperty( String key, String value);

	String getURL();

}