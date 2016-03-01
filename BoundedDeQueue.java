/**
 * The Class BoundedDeQueue.
 *
 * @param <T> the generic type
 */
@SuppressWarnings("rawtypes")
public class BoundedDeQueue<T> extends BoundedQueue {

	/**
	 * Instantiates a new bounded de queue.
	 *
	 * @param _capacity the _capacity
	 */
	public BoundedDeQueue(int _capacity) {
		super(_capacity);
	}

	/**
	 * Push.
	 *
	 * @param x the x
	 */
	@SuppressWarnings({ "unchecked" })
	public void push(T x) {
		boolean mustWakeDequeuers = false;
		enqLock.lock();
		try {
			while (size.get() == capacity) {
				notFullCondition.await();
			}
			Node e = new Node(x);
			tail.next = e;
			tail = e;
			if (size.getAndIncrement() == 0) {
				mustWakeDequeuers = true;
			}
		} catch (InterruptedException ie) {
			System.out.println("push(): Interrupted Exception");
		} finally {
			enqLock.unlock();
		}

		if (mustWakeDequeuers) {
			deqLock.lock();
			try {
				notEmptyCondition.signalAll();
			} finally {
				deqLock.unlock();
			}
		}
	}
}
