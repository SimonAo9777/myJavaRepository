package binding;

import java.util.concurrent.TimeUnit;

import counter.Counter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 * 
 * @author Simon Ao
 * @version Aug 5, 2021
 * 
 * CounterBinding Class handles connection between model and view. 
 * The class handles simple logic how Counter is updated and in turn view
 * is updated.
 * This class is to step the counter 
 * and update the view of changes of counter and vice versa.         
 */

public class CounterBinding extends Task<Void> {
	private Counter counter;
	private StringProperty time;
	private Object stepLock;

	public CounterBinding(Counter counter) {

		this.counter = counter;
		stepLock = new Object();
		time = new SimpleStringProperty();
		/**
		 * updateMessage() is used to set new value for the message property
		 */
		updateMessage(counter.toString());
		/**
		 * messageProperty() can be binded to other properties for auto updates.
		 */
		time.bind(messageProperty());

	}
	
	
	
	@Override
	protected Void call() throws Exception {
		while (!isCancelled()) {
			try {
				if (counter.isPaused())
					synchronized (stepLock) {
						counter.wait();
					}

				if (!counter.step()) {

					TimeUnit.MILLISECONDS.sleep(50);
				} else {
					updateMessage(counter.toString());
				}

			} catch (InterruptedException e) {

			}

		}
		return null;
	}
	public StringProperty timeProperty() {

		return time;

	}
	/**
	 *stepLock.notifyAll() change the state of all the threads 
	 *in a wait state to running state.
	 *The stepLock used is very import. 
	 */
	public void start() {

		synchronized (stepLock) {
			counter.start();
			stepLock.notifyAll();
		}
	}

	public void stop() {
		counter.stop();

	}

	public void pause() {
		counter.pause();
	}

	public void shutdown() {
		cancel();
	}


}
