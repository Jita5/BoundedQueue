import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Class BoundedQueue.
 *
 * @param <T> the generic type
 */
public class BoundedQueue<T> {
	
	/** The deq lock. */
	ReentrantLock enqLock, deqLock;
	
	/** The not full condition. */
	Condition notEmptyCondition, notFullCondition;
	
	/** The size. */
	AtomicInteger size;
	
	/** The tail. */
	volatile Node head, tail;
	
	/** The capacity. */
	int capacity;

	/**
	 * Instantiates a new bounded queue.
	 *
	 * @param _capacity the _capacity
	 */
	public BoundedQueue(int _capacity) {
		capacity = _capacity;
		head = new Node(null);
		tail = head;
		size = new AtomicInteger(0);
		enqLock = new ReentrantLock();
		notFullCondition = enqLock.newCondition();
		deqLock = new ReentrantLock();
		notEmptyCondition = deqLock.newCondition();
	}

	/**
	 * Enq.
	 *
	 * @param x the x
	 */
	public void enq(T x) {
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
			System.out.println("enq(): Interrupted Exception");
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

	/**
	 * Deq.
	 *
	 * @return the t
	 */
	public T deq() {
		T result = null;
		boolean mustWakeEnqueuers = false;
		deqLock.lock();
		try {
			while (size.get() == 0) {
				notEmptyCondition.await();
			}
			result = head.next.value;
			head = head.next;
			if (size.getAndDecrement() == capacity) {
				mustWakeEnqueuers = true;
			}
		} catch (InterruptedException ie) {
			System.out.println("deq(): Interrupted Exception");
		} finally {
			deqLock.unlock();
		}
		if (mustWakeEnqueuers) {
			enqLock.lock();
			try {
				notFullCondition.signalAll();
			} finally {
				enqLock.unlock();
			}
		}
		return result;
	}

	/**
	 * The Class Node.
	 */
	protected class Node {
		
		/** The value. */
		public T value;
		
		/** The next. */
		public volatile Node next;

		/**
		 * Instantiates a new node.
		 *
		 * @param x the x
		 */
		public Node(T x) {
			value = x;
			next = null;
		}
	}
}
