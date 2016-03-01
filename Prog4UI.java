import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * The Class Prog4UI.
 */
public class Prog4UI implements ActionListener {

	/** The frame. */
	private JFrame frame;
	
	/** The enq threads. */
	private JTextField enqThreads;
	
	/** The deq threads. */
	private JTextField deqThreads;
	
	/** The push threads. */
	private JTextField pushThreads;
	
	/** The max capacity. */
	private JTextField maxCapacity;
	
	/** The max runtime. */
	private JTextField maxRuntime;
	
	/** The enq. */
	int enq;
	
	/** The deq. */
	int deq;
	
	/** The push. */
	int push;
	
	/** The cap. */
	int cap;
	
	/** The run. */
	int run;
	
	/** The enq in. */
	String enqIn = "";
	
	/** The deq in. */
	String deqIn = "";
	
	/** The push in. */
	String pushIn = "";
	
	/** The cap in. */
	String capIn = "";
	
	/** The run in. */
	String runIn = "";

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prog4UI window = new Prog4UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Prog4UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(600, 300, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblWelcomeToProgram = new JLabel("Welcome to Program 4!");
		lblWelcomeToProgram.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWelcomeToProgram.setBounds(100, 15, 200, 36);
		frame.getContentPane().add(lblWelcomeToProgram);
		
		JLabel lblInfo = new JLabel("*Enter a value for all inputs, and only integers!");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblInfo.setBounds(90, 40, 275, 20);
		frame.getContentPane().add(lblInfo);

		JLabel lblNumberOfEnque = new JLabel("Number of enque threads:");
		lblNumberOfEnque.setBounds(50, 70, 162, 14);
		frame.getContentPane().add(lblNumberOfEnque);

		JLabel lblNumberOfDeque = new JLabel("Number of deque threads:");
		lblNumberOfDeque.setBounds(50, 100, 162, 14);
		frame.getContentPane().add(lblNumberOfDeque);

		JLabel lblNumberOfPushing = new JLabel("Number of pushing threads:");
		lblNumberOfPushing.setBounds(50, 130, 162, 14);
		frame.getContentPane().add(lblNumberOfPushing);

		JLabel lblMaxCapacityOf = new JLabel("Max capacity of queue:");
		lblMaxCapacityOf.setBounds(50, 160, 162, 14);
		frame.getContentPane().add(lblMaxCapacityOf);

		JLabel lblMaxRuntime = new JLabel("Runtime of program:");
		lblMaxRuntime.setBounds(50, 190, 162, 14);
		frame.getContentPane().add(lblMaxRuntime);

		enqThreads = new JTextField();
		enqThreads.setBounds(270, 70, 50, 20);
		frame.getContentPane().add(enqThreads);
		enqThreads.setColumns(10);

		deqThreads = new JTextField();
		deqThreads.setBounds(270, 100, 50, 20);
		frame.getContentPane().add(deqThreads);
		deqThreads.setColumns(10);

		pushThreads = new JTextField();
		pushThreads.setBounds(270, 130, 50, 20);
		frame.getContentPane().add(pushThreads);
		pushThreads.setColumns(10);

		maxCapacity = new JTextField();
		maxCapacity.setBounds(270, 160, 50, 20);
		frame.getContentPane().add(maxCapacity);
		maxCapacity.setColumns(10);

		maxRuntime = new JTextField();
		maxRuntime.setBounds(270, 190, 50, 20);
		frame.getContentPane().add(maxRuntime);
		maxRuntime.setColumns(10);

		JButton btnStart = new JButton("START!");
		btnStart.setBounds(150, 215, 89, 23);
		frame.getContentPane().add(btnStart);
		btnStart.addActionListener(this);
	}

	/** Action Performed method to handle button click
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		//getting user input and converting to integers
		 enq = Integer.parseInt(enqThreads.getText());
		 deq = Integer.parseInt(deqThreads.getText());
		 push = Integer.parseInt(pushThreads.getText());
		 cap = Integer.parseInt(maxCapacity.getText());
		 run = Integer.parseInt(maxRuntime.getText());
		try {
			new ThreadControl(cap, enq, deq, push, run);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
