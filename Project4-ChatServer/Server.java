import java.net.*;
import java.util.ArrayList;
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Server extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	// GUI items
	JButton ssButton;
	JLabel machineInfo;
	JLabel portInfo;
	JTextArea history;

	// a unique ID for each connection
	public static int uniqueId = 1;
	// stop the server??
	private boolean running;

	// Network Items
	boolean serverContinue;
	boolean clientSocketContinue;
	ServerSocket serverSocket;

	// list of clients
	public ArrayList<CommunicationThread> al;
	//DataOutputStream outToClient;
	//DataInputStream inFromClient;
	//int[] nums = null;
	//String[] names = null;

	// set up GUI
	public Server()
	{
		super( "Server" );
		//uniqueId = 0;
		al = new ArrayList<CommunicationThread>();

		// get content pane and set its layout
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );

		// create buttons
		running = false;
		ssButton = new JButton( "Start Listening" );
		ssButton.addActionListener( this );
		container.add( ssButton );

		String machineAddress = null;
		try
		{  
			InetAddress addr = InetAddress.getLocalHost();
			machineAddress = addr.getHostAddress();
		}
		catch (UnknownHostException e)
		{
			machineAddress = "127.0.0.1";
		}
		machineInfo = new JLabel (machineAddress);
		container.add( machineInfo );
		portInfo = new JLabel (" Not Listening ");
		container.add( portInfo );

		history = new JTextArea ( 10, 40 );
		history.setEditable(false);
		container.add( new JScrollPane(history) );

		setSize( 500, 250 );
		setVisible( true );


	} // end constructor

	public static void main( String args[] )
	{ 
		Server application = new Server();
		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	// handle button event
	public void actionPerformed( ActionEvent event )
	{
		if (running == false)
		{
			new ConnectionThread (this);
			running = true;
			ssButton.setText ("Stop Listening");
		}
		else
		{
			serverContinue = false;
			running = false;
			ssButton.setText ("Start Listening");
			portInfo.setText (" Not Listening ");
		}
	}



	// broadcast a message to all clients
	public synchronized void broadcast(Message message)
	{
		System.out.println("Message type broadcasted is: " + message.showType());

		if (message.showType() == 3){
			for (int i = al.size()-1; i >= 0; i--)
			{
				CommunicationThread ct = al.get(i);

				if (!ct.writeMsg(message))
				{
					al.remove(i);
				}
			}
		}
		else if(message.showType() == 0) {
			for (int i = al.size()-1;i>=0;i--){
				for (int j=0;j<message.getTo().length;j++) {
					if (al.get(i).id == message.getTo()[j]) {
						CommunicationThread ct = al.get(i);
						ct.writeMsg(message);
					}
				}
			}
		}
	}

	public synchronized void remove(int id)
	{
		for (int i = 0; i < al.size(); i++)
		{
			CommunicationThread ct = al.get(i);

			if (ct.id == id)
			{
				al.remove(i);
				System.out.println("Removing:"+ct.username);
				return;
			}
		}
	}

} // end class Server


class ConnectionThread extends Thread
{
	Server server;
	int port = 10007;

	public ConnectionThread (Server server)
	{
		this.server = server;
		start();
	}

	public void run()
	{
		server.serverContinue = true;

		try 
		{ 
			server.serverSocket = new ServerSocket(port); 
			server.portInfo.setText("Listening on Port: " + server.serverSocket.getLocalPort());
			System.out.println ("Connection Socket Created");

			while (server.serverContinue)
			{
				try { 
					System.out.println ("Waiting for clients on port " + port);
					server.ssButton.setText("Stop Listening");
					Socket clientSocket = server.serverSocket.accept();

					CommunicationThread t = new CommunicationThread (clientSocket, server); 
					System.out.println("Just connected to "
							+ clientSocket.getRemoteSocketAddress());
					//server.al.add(t);
				}
				catch (IOException e) 
				{ 
					System.err.println("Accept failed."); 
					System.exit(1); 
				} 
			}
		} 
		catch (IOException e) 
		{ 
			System.err.println("Could not listen on port: " + server.serverSocket.getLocalPort()); 
			System.exit(1); 
		} 
		finally
		{
			try 
			{
				server.serverSocket.close(); 
			}
			catch (IOException e)
			{ 
				System.err.println("Could not close port: 10007."); 
				System.exit(1); 
			} 
		}
	}
}

class CommunicationThread extends Thread
{ 
	private Socket clientSocket;
	private Server server;
	ObjectOutputStream out;
	ObjectInputStream in;
	int id;
	String username;

	public CommunicationThread (Socket client, Server server)
	{
		clientSocket = client;
		this.server = server;
		try {
			out = new ObjectOutputStream(
					clientSocket.getOutputStream());
			in = new ObjectInputStream( 
					clientSocket.getInputStream()); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception creating new Input/output Streams: " + e);
			return;
		}
		System.out.println ("New Communication Thread Started");
		server.clientSocketContinue = true;
		start();
	}

	public void run()
	{
		while (server.clientSocketContinue)
		{
			Message m;
			try 
			{
				m = (Message) in.readObject();
			} 
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}

			Message m2 = null;
			System.out.println("Message type from client is: " + m.showType());//m.showMessage();//System.out.println()

			if (m.showType() == 1) // log in
			{
				// if first person join the list
				if (server.al.size() == 0)
				{
					id = Server.uniqueId++;
					username = m.getMessage();
					System.out.println("userName is: " + username);
					server.al.add(this);

					m2 = new Message(getIds(server.al), getNames(server.al));	
				}
				else // if not first person joined
				{
					int i;

					// check if userName exist or not
					for (i = 0; i < server.al.size(); i++ )
					{
						if (m.getMessage().equals(server.al.get(i).username))
						{
							m2 = new Message();
							System.out.println("userName exist!");
							close();
							try {
								this.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
					}
					// if this is a new user with no userName conflict => add to list
					if (i == server.al.size())
					{
						id = Server.uniqueId++;
						username = m.getMessage();
						server.al.add(this);
						m2 = new Message(getIds(server.al), getNames(server.al));
					}
					else
						m2 = new Message();
				}

				m2.showMessage();
				server.broadcast(m2);
				server.history.insert(id + ": " + username + "\n", 0);

			}
			else if (m.showType() == 2) // quit message
			{
				server.clientSocketContinue = false;
				server.remove(this.id);

				m2 = new Message(getIds(server.al), getNames(server.al));
				m2.showMessage();
				server.broadcast(m2);
				this.writeMsg(new Message());
				System.out.println("Client " + username + " exited!");
				close();
				try {
					this.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (m.showType() == 3) // for returning list
			{
				m2 = new Message(getIds(server.al), getNames(server.al));
				server.broadcast(m2);
			}
			else if (m.showType() == 0)		// normal message
			{
				System.out.println("Message from client " + username + ": " + m.getMessage());
				server.broadcast(m);
			}

		}

		try
		{
			Thread.sleep(4000);
		}
		catch(InterruptedException e)
		{
			return;  /*This will terminate the thread*/
		}

	}

	public int[] getIds(ArrayList<CommunicationThread> al)
	{
		int[] ids = new int[al.size()];
		for (int i =0; i < al.size(); i++)
		{
			ids[i] = al.get(i).id;
		}
		return ids;
	}
	public String[] getNames(ArrayList<CommunicationThread> al)
	{
		String[] names = new String[al.size()];
		for (int i =0; i < al.size(); i++)
		{
			names[i] = al.get(i).username;
		}
		return names;
	}
	// try to close everything
	public void close() {
		// try to close the connection
		try {
			if(out != null) out.close();
		}
		catch(Exception e) {}
		try {
			if(in != null) in.close();
		}
		catch(Exception e) {};
		try {
			if(clientSocket != null) clientSocket.close();
		}
		catch (Exception e) {}
	}

	public boolean writeMsg(Message message)
	{
		if (!clientSocket.isConnected())
		{
			close();
			return false;
		}
		try
		{
			out.writeObject(message);
			out.flush();
		}
		catch (IOException e)
		{

		}
		return true;
	}

} 







