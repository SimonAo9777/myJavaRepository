package binding;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import counter.Counter;

/**
 * 
 * @author Simon Ao
 * @version Aug 5, 2021
 * 
 * This class is to create new CounterBinding
 * add BindingManager to the thread pool,ExecutorService.
 */

public class BindingManager {
	private ExecutorService executor;
	private List<CounterBinding> counters;
	
	public BindingManager() {
		counters = new LinkedList<>();
		executor = Executors.newCachedThreadPool();

	}

	public void shutdown() {
		counters.forEach(CounterBinding::shutdown);
		executor.shutdown();

	}
	public CounterBinding getNewBinding() {

		CounterBinding cb = new CounterBinding(new Counter());
		counters.add(cb);
		executor.submit(cb);

		return cb;
	}
}
