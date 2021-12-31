package example;

import java.time.Instant;

public class TimeExample {

	public static void main( String[] args) {
		Instant time = Instant.now();
		long epoch = time.toEpochMilli();
		System.out.println( time);
		System.out.println( epoch);
		System.out.println( Instant.ofEpochMilli( epoch));
	}

}
