//package hw4;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Client{
	JFrame loginFrame,mainFrame;
	JPanel loginPanel,mainPanel,userListPanel,controlPanel,loginPanel1,loginPanel2,loginPanel3;
	JLabel loginNameL,ipNameL,portNameL;
	JTextField loginName,ipName,portName,messageContent;
	JTextArea userInput,history;
	JButton login,sendToSellected,sendToAll;
	JCheckBox check[];
	JScrollPane historyScroller,inputScroller,userScroller;

	int myNumber = -1;
	String myName;
	int userListLength;
	int checkNum[];
	boolean flag = true;

	Listening l = null;

	Socket chatSocket = null;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;

	public Client(){
		loginFrame = new JFrame("Log In");
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.Y_AXIS));
		loginPanel1 = new JPanel();
		loginPanel2 = new JPanel();
		loginPanel3 = new JPanel();
		loginPanel.add(loginPanel1);
		loginPanel.add(loginPanel2);
		loginPanel.add(loginPanel3);
		ipNameL = new JLabel("       IP:        ");
		ipName = new JTextField("127.0.0.1",20);
		loginPanel1.add(ipNameL);
		loginPanel1.add(ipName);
		portNameL = new JLabel("     Port:      ");
		portName = new JTextField("10007",20);
		loginPanel2.add(portNameL);
		loginPanel2.add(portName);
		loginNameL = new JLabel("Username:");
		loginName = new JTextField(20);
		loginPanel3.add(loginNameL);
		loginPanel3.add(loginName);
		loginFrame.getContentPane().add(BorderLayout.CENTER, loginPanel);
		login = new JButton("Log in");
		loginPanel.add(login);
		login.addActionListener(new LoginButtonListener());

		loginFrame.pack();
		loginFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		loginFrame.setVisible(true);

		//		check = new JCheckBox[200];
		//		checkNum = new int[200];

		mainFrame = new JFrame("Chatroom");
		controlPanel = new JPanel();
		userListPanel = new JPanel();
		userListPanel.setLayout(new BoxLayout(userListPanel,BoxLayout.Y_AXIS));

		userScroller = new JScrollPane(userListPanel);
		userScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		userScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		userScroller.setPreferredSize(new Dimension(300,200));

		sendToSellected = new JButton("Send message to sellected people");
		sendToSellected.addActionListener(new SendToSellectedButtonListener());
		sendToAll = new JButton("Send message to all");
		sendToAll.addActionListener(new SendToAllButtonListener());
		controlPanel.add(sendToSellected);
		controlPanel.add(sendToAll);

		history = new JTextArea(15,30);
		history.setEditable(false);
		history.setLineWrap(true);
		historyScroller = new JScrollPane(history);
		historyScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		historyScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		userInput = new JTextArea(5,30);
		userInput.setEditable(true);
		userInput.requestFocus();
		inputScroller = new JScrollPane(userInput);
		inputScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		inputScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


		mainFrame.getContentPane().add(BorderLayout.NORTH, historyScroller);
		mainFrame.getContentPane().add(BorderLayout.CENTER, inputScroller);
		mainFrame.getContentPane().add(BorderLayout.SOUTH, controlPanel);
		mainFrame.getContentPane().add(BorderLayout.EAST, userScroller);

		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					quit();
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});



		try {
			chatSocket = new Socket(ipName.getText(),Integer.parseInt(portName.getText()));
			out = new ObjectOutputStream(chatSocket.getOutputStream());
			in = new ObjectInputStream(chatSocket.getInputStream());
		} catch (UnknownHostException e1) {
			System.err.println("Don't know about host");
			System.exit(1);
		} catch (IOException e1) {
			System.err.println("Couldn't get I/O");
			System.exit(1);
		}

	}

	public static void main(String[] args){
		Client gui = new Client();
	}

	private void refresh(Message m) {
		mainFrame.setVisible(false);
		userListPanel.removeAll();
		userListLength = m.getUserLength();
		//		System.out.println("There are " + userListLength + " friends online");
		check = null;
		checkNum = null;
		check = new JCheckBox[userListLength-1];
		checkNum = new int[userListLength-1];
		int j = 0;
		for (int i=0;i<userListLength;i++){
			int n = m.getUserNum(i);
			String name = m.getUserName(i);
			if (n == myNumber) continue;
			//		System.out.println("The client " + name + " in number " + n);
			check[j] = new JCheckBox(name);
			userListPanel.add(check[j]);
			checkNum[j] = n;
			j++;
		}



		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private void sethistory(Message m){
		int a = m.getFrom();
		System.out.println("This message is from client: " + a);
		System.out.println("User list length: " + userListLength);

		if (userListLength == 1)
			System.out.println("There is only one client connected!");
		else
		{
			int i = 0;
			for (i = 0; i<userListLength-1;i++){
				if (a == checkNum[i]) break;
			}

			String name = check[i].getText();
			String content = m.getMessage();
			history.insert(name+": "+content+"\n", 0);
		}
	}

	private void quit() throws IOException, InterruptedException{
		Message m = new Message();

		out.writeObject(m);

//		flag = false;

		l.join();

		in.close();
		out.close();
		chatSocket.close();

		System.exit(0);
	}

	private class Listening extends Thread {
		public void run(){
			//			Timer time = new Timer(500,new TimeListener());
			//			time.start();

			while(flag) {
				Message mr = null;
				try {
					mr = (Message) in.readObject();
					mr.showMessage();
				}
				catch (SocketTimeoutException e) {continue;}
				catch(Exception ex) {ex.printStackTrace();break;}
				int type = mr.showType();
				if(type == 3) {
					refresh(mr);
				}
				if(type == 0) {
					int a = mr.getFrom();
					if (a != myNumber)
						sethistory(mr);
				}
				if (type == 2)
				{
					System.out.println("Quit!");
					return;
				}
				//				mr.showMessage();
			}


		}
	}

	private class SendToAllButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			Message m = new Message(myNumber,checkNum,userInput.getText());
			//		m.showMessage();

			try {
				out.writeObject(m);
				out.flush();
			}
			catch(IOException e1){
				e1.printStackTrace();
				return;
			}

			userInput.setText("");
		}
	}


	private class SendToSellectedButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {

			int [] sendList;
			int count = 0;
			for (int i =0; i<userListLength-1; i++){
				if (check[i].isSelected()) count++;
			}

			if (count == 0) return;

			sendList = new int [count];

			int j = 0;
			for (int i = 0 ; i<count && j<userListLength-1;j++) {
				if (check[j].isSelected()) {
					sendList[i] = checkNum[j];
					check[j].setSelected(false);
					i++;
				}
			}


			Message m = new Message(myNumber,sendList,userInput.getText());
			//		m.showMessage();

			try {
				out.writeObject(m);
				out.flush();
			}
			catch(IOException e1){
				e1.printStackTrace();
				return;
			}

			userInput.setText("");
		}
	}

	private class LoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Message m = new Message(loginName.getText());
			Message m2 = null;
			m.showMessage();



			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}



			try {
				m2 = (Message) in.readObject();
				m2.showMessage();
				if(m2.showType() != 3) {
					JOptionPane.showMessageDialog(null,"Your name already exists! Using a new name and try again.");
					return;
				}
			}
			catch (Exception ex){
				System.out.println(ex.getMessage());
				return;
			}

			//		m2.showMessage();

			myName = loginName.getText();
			System.out.println("My username is: " + myName);
			myNumber = m2.findNumber(myName);
			System.out.println("My number is: " + myNumber);
			userListLength = m2.getUserLength();

			loginFrame.setVisible(false);
			mainFrame.setTitle("Chatroom - "+myName);

			refresh(m2);

			try {
				chatSocket.setSoTimeout(800);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}

			l = new Listening();
			l.start();


		}
	}

	/*
	private class TimeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {



			Message m2 = null;
			try {
				m2 = (Message) in.readObject();
				if(m2.showType() != 3) {
					JOptionPane.showMessageDialog(null,"Your name already exists! Using a new name and try again.");
					return;
				}
			}
			catch (Exception ex){
				System.out.println(ex.getMessage());
				return;
			}

			m2.showMessage();
		}
	}*/


}




/*public class Client {
    public static void main(String[] args) throws IOException {

    	Client test = new Client();

    	JFrame frame = new JFrame("Client");
    	JPanel panel = new JPanel();
    	JPanel panel1 = new JPanel();
    	JPanel panel2 = new JPanel();
    	panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    	JTextField text = new JTextField ("",45);
    	JTextArea display = new JTextArea("Type Message (\"Bye.\" to quit)",10,50);
    	JButton OK = new JButton("OK");
    	display.setEditable(false);
    	frame.add(BorderLayout.CENTER,panel);
    	panel.add(panel1);
    	panel.add(panel2);
    	panel1.add(display);
    	panel2.add(text);
    	panel2.add(OK);

    	frame.pack();
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	OK.addActionListener(test.new ClickActionPerformed());

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket("127.0.0.1", 10008);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: taranis.");
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

        System.out.println ("Type Message (\"Bye.\" to quit)");
	while ((userInput = stdIn.readLine()) != null) 
           {
	    out.println(userInput);

            // end loop
            if (userInput.equals("Bye."))
                break;

	    System.out.println("echo: " + in.readLine());
	   }

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }


    private class ClickActionPerformed implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		;
    	}
    }


}
 */