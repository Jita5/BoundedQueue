import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * The Class ThreadControl.
 */
public class ThreadControl {
	
	/** The capacity. */
	private int capacity;
	
	/** The enq threads. */
	private int enqThreads;
	
	/** The deq threads. */
	private int deqThreads;
	
	/** The push threads. */
	private int pushThreads;
	
	/** The total threads. */
	private int totalThreads;
	
	/** The count. */
	private int count;
	
	/** The type. */
	private String type;
	
	/** The start time. */
	private long startTime;
	
	/** The runtime. */
	private int runtime;

	/**
	 * Instantiates a new thread control.
	 *
	 * @param maxCapacity the max capacity
	 * @param enq the enq
	 * @param deq the deq
	 * @param push the push
	 * @param maxRunTime the max run time
	 * @throws InterruptedException the interrupted exception
	 */
	public ThreadControl(int maxCapacity, int enq, int deq, int push, int maxRunTime) throws InterruptedException {
		this.capacity = maxCapacity;
		this.enqThreads = enq;
		this.deqThreads = deq;
		this.pushThreads = push;
		this.runtime = maxRunTime;

		count = 0;
		totalThreads = enqThreads + deqThreads + pushThreads;
		deqThreads += enqThreads;
		Thread[] threadSet = new Thread[totalThreads];
		BoundedDeQueue<Threads> que = new BoundedDeQueue<>(capacity);

		// reset thread ID's
		ThreadID.reset();

		// Initializing thread set
		for (int i = 0; i < totalThreads; i++) {
			count++;
			//Set number of enq threads
			if (count < enqThreads) {
				type = "E";
				//Set deq threads
			} else if ((count > enqThreads) && (count < deqThreads)) {
				type = "D";
				//Set pushing threads
			} else if (count > deqThreads) {
				type = "P";
			}
			threadSet[i] = new Threads(que, type);
			ThreadID.set(i);
		}

		// Getting time for how long prog should run
		startTime = System.currentTimeMillis();

		// Starting the threads
		for (int i = 0; i < totalThreads; i++) {
			threadSet[i].start();
		}

		// Spinning for run time
		while ((System.currentTimeMillis() - startTime) < runtime) {
		}
		
		//Stop threads from looping
		Threads.kill();
		
		startTime = System.currentTimeMillis();
		// Short wait for threads to stop
		 while ((System.currentTimeMillis() - startTime) < 1500) {
		 }

		System.out.println("	  SYSTEM: Runtime reached, exiting program!");
		
		//Writing output to file
		try {
			String directory = System.getProperty("user.dir");
			File outFile = new File(directory + "\\" + "BoundedQueueOuput.txt");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(Threads.output);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				out.println(line);
			}
			out.close();
		} catch (IOException iox) {
			iox.printStackTrace();
		}
		//Exit program which also kills and threads stuck in sleep mode
		System.exit(0);
	}
}
