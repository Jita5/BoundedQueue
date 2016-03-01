import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * The Class Threads.
 */
public class Threads extends Thread {

	/** The que. */
	private BoundedDeQueue<Threads> que;
	
	/** The int val. */
	private int intVal;
	
	/** The thread type. */
	private String threadType;
	
	/** The is alive. */
	private static boolean isAlive;
	
	/** The deq obj. */
	private Threads deqObj;
	
	/** The mean. */
	private double mean = 1000;
	
	/** The std. */
	private double std = 200;
	
	/** The random. */
	Random random = new Random();
	
	/** The rando. */
	private int rando;
	
	/** The sleep time. */
	private int sleepTime;
	
	/** The output. */
	static String output = "";
	
	/** The date format. */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");

	/**
	 * Instantiates a new threads.
	 *
	 * @param boundedQue the bounded que
	 * @param type the type
	 */
	public Threads(BoundedDeQueue<Threads> boundedQue, String type) {
		this.que = boundedQue;
		this.setType(type);
		isAlive = true;
	}

	/** Run for threads
	 * @see java.lang.Thread#run()
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		
		//If thread is enq, do this
		if (getType() == "E") {
			while (isAlive) {
				try {
					rando = (int) (mean + std * random.nextGaussian());
					if (rando < 0) {
						// if random number ever gets negative, default to 1000
						rando = 1000;
					}
					Thread.sleep(rando);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rando = (int) (mean + std * random.nextGaussian());
				if (rando < 0) {
					// if random number ever gets negative, default to 1000
					rando = 1000;
				}
				//setting random generated number to object
				this.setIntVal(rando);
				output += (String.format("%s %3d %6s %5d %5s %6s \n", "Thread:", ThreadID.get(), "Enqueued Item:",
						this.getIntVal(), "At Time:", dateFormat.format(System.currentTimeMillis())));
				System.out.println(String.format("%s %3d %6s %5d %5s %6s", "Thread:", ThreadID.get(), "Enqueued Item:",
						this.getIntVal(), "At Time:", dateFormat.format(System.currentTimeMillis())));
				que.enq(this);
			}
		}
		//If thread is deq, do this
		if (getType() == "D") {
			while (isAlive) {
				deqObj = (Threads) que.deq();
				output += (String.format("%s %3d %6s %5d %5s %6s \n", "Thread:", ThreadID.get(), "Dequeued Item:",
						((Threads) deqObj).getIntVal(), "At Time:", dateFormat.format(System.currentTimeMillis())));
				System.out.println(String.format("%s %3d %6s %5d %5s %6s", "Thread:", ThreadID.get(), "Dequeued Item:",
						((Threads) deqObj).getIntVal(), "At Time:", dateFormat.format(System.currentTimeMillis())));
				//Get random value of object
				sleepTime = ((Threads) deqObj).getIntVal();
				try {
					//sleep that random amount of time
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		//If thread is pushing, do this
		if (getType() == "P") {
			while (isAlive) {
				try {
					rando = (int) (mean + std * random.nextGaussian());
					if (rando < 0) {
						// if random number ever gets negative, default to 1000
						rando = 1000;
					}
					Thread.sleep(rando);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rando = (int) (mean + std * random.nextGaussian());
				if (rando < 0) {
					// if random number ever gets negative, default to 1000
					rando = 1000;
				}
				//setting random generated number to object
				this.setIntVal(rando);
				output += (String.format("%s %3d %6s %7d %5s %6s \n", "Thread:", ThreadID.get(), "Pushed Item:",
						this.getIntVal(), "At Time:", dateFormat.format(System.currentTimeMillis())));
				System.out.println(String.format("%s %3d %6s %7d %5s %6s", "Thread:", ThreadID.get(), "Pushed Item:",
						this.getIntVal(), "At Time:", dateFormat.format(System.currentTimeMillis())));
				que.push(this);
			}
		}
	}

	/**
	 * Kill.
	 */
	public static void kill() {
		isAlive = false;
	}

	/**
	 * Gets the int val.
	 *
	 * @return the int val
	 */
	public int getIntVal() {
		return intVal;
	}

	/**
	 * Sets the int val.
	 *
	 * @param intVal the new int val
	 */
	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return threadType;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.threadType = type;
	}
}
