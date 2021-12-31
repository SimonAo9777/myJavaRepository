package jdbc.url;

import java.util.Map.Entry;

public class MySQLURLBuilder extends JDBCURLBuilder {

	public MySQLURLBuilder() {
		setDBType( "mysql");
	}

	@Override
	public String getURL() {
		StringBuilder builder = new StringBuilder( JDBC);
		builder.append( ":");
		builder.append( dbTypeName);
		builder.append( "://");
		builder.append( hostAddress);
		builder.append( ":");
		builder.append( portNumber);
		builder.append( "/");
		builder.append( databaseName);
		if ( !properties.isEmpty()) {
			builder.append( "?");
			String delimiter = "";
			for ( Entry< String, String> parameter : properties.entrySet()) {
				builder.append( delimiter);
				builder.append( parameter.getKey());
				builder.append( "=");
				builder.append( parameter.getValue());
				delimiter = "&";
			}
		}
		return builder.toString();
	}

}
