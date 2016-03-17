/* Authors: Mike McClory and Tung Hoang
 * Class: CS342 Software Design
 * Instructor: P.Troy
 * Project 3: RSA Encrypt/Decrypt
 * 
 * Contain: Main Souce Code 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class rsa_gui extends JFrame //implements ActionListener
{ 
	// all the items that make up the game
	static rsa_gui app;
	static JFrame frame = new JFrame("RSA Encrypter/Decrypter");
	private JButton buttons[]; // 2d-array of buttons
	private Container container; // the container the grid of buttons will lie in
	private GridLayout grid1;
	JMenuBar menu = new JMenuBar();
	static RSA rsa = new RSA();
	KeyPairGenerator key;
	int p = 0, q = 0;

	// set up GUI
	public rsa_gui(JFrame frame)
	{
		super( "RSA Encrypter/Decrypter" );

		// add menu bar and its items to gui
		frame.setJMenuBar(menu);

		// get content pane and set its layout
		container = getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridheight = 5;
		container.setSize(500, 200);

		JLabel label = new JLabel();
		label.setText("RSA Encrypter/Decrypter");
		label.setFont(new Font("Arial",Font.BOLD , 18));
		label.setPreferredSize(new Dimension(300,50));
		label.setHorizontalAlignment(JLabel.CENTER);
		frame.add(label);

		// create and add buttons
		buttons = new JButton[5];

		String[] names = {"Create Keys","Block","Encrypt","Decrypt","Unblock"};
		int n = 0;

		for ( int i = 0; i < 5; i++ ) 
		{

			c.gridx = 0;

			buttons[i] = new JButton(names[n]);
			buttons[i].setPreferredSize(new Dimension(300, 50));
			buttons[i].addMouseListener(new MouseHandler());
			container.add( buttons[i], c);
			n++;

		}

		// add the button grid to the gui
		frame.add(container);

	} // end constructor GridLayoutDemo



	public static void main( String args[] )
	{

		frame.setSize(320,360);
		frame.setLayout(new FlowLayout());


		//create board and start the gui
		app = new rsa_gui(frame);
		app.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		frame.setVisible(true);

	} // end of main



	// 
	// checks if a number is prime 
	//
	public boolean isPrime(int x)
	{
		int num = 0;
		//System.out.println("in isPrime...");
		//System.out.println(x);
		int count=0;    
		for(num = x; num >= 1; num--)
		{
			if(x % num == 0)
			{
				count++;
			}
		}  

		if (count == 2)
		{
			return true;
		}

		return false;
	}


	//
	// handles mouse click events
	//
	class MouseHandler extends MouseAdapter 
	{

		public void mouseClicked (MouseEvent e)
		{

			JButton temp = (JButton) e.getSource();

			if( (SwingUtilities.isLeftMouseButton(e)) && (temp.getText().equals("Encrypt")) )
			{

				try
				{
					rsa.blockedFileName = JOptionPane.showInputDialog("Enter the filename of the message to be encrpyted: ");
					rsa.publicKeyFileName = JOptionPane.showInputDialog("Enter the filename of the public key: ");
					rsa.EncryptMessage(rsa.blockedFileName,rsa.publicKeyFileName);  

				}
				catch(NumberFormatException n)
				{
					JOptionPane.showMessageDialog(null,"no number found.");
				}
				catch(IOException f)
				{
					JOptionPane.showMessageDialog(null,"file not found.");
				}
				catch(NullPointerException p)
				{
					JOptionPane.showMessageDialog(null,"variable is null.");
				}

			}
			else if( (SwingUtilities.isLeftMouseButton(e)) && (temp.getText().equals("Decrypt")) )
			{

				try
				{
					rsa.encryptedFileName = JOptionPane.showInputDialog("Enter the filename of the message to be decrpyted: ");
					rsa.privateKeyFileName = JOptionPane.showInputDialog("Enter the filename of the private key: ");
					rsa.DecryptMessage(rsa.encryptedFileName,rsa.privateKeyFileName);  

				}
				catch(NumberFormatException n)
				{
					JOptionPane.showMessageDialog(null,"no number found.");
				}
				catch(NullPointerException p)
				{
					JOptionPane.showMessageDialog(null,"variable is null.");
				}
				catch(IOException f)
				{
					JOptionPane.showMessageDialog(null,"file not found.");
				}


			}
			else if( (SwingUtilities.isLeftMouseButton(e)) && (temp.getText().equals("Block")) )
			{ 

				try
				{
					rsa.messageFile = JOptionPane.showInputDialog("Enter the filename of the message to be blocked: ");
					int size = Integer.parseInt( JOptionPane.showInputDialog("Enter a block size (1-10): ") );
					rsa.blockMessage(rsa.messageFile,size);  
				}
				catch(NumberFormatException n)
				{
					JOptionPane.showMessageDialog(null,"no number found.");
				}
				catch(IOException f)
				{
					JOptionPane.showMessageDialog(null,"file not found.");
				}
				catch(NullPointerException p)
				{
					JOptionPane.showMessageDialog(null,"variable is null.");
				}

			}
			else if( (SwingUtilities.isLeftMouseButton(e)) && (temp.getText().equals("Unblock")) )
			{

				try
				{
					rsa.decryptedFileName = JOptionPane.showInputDialog("Enter the filename of the message to be unblocked: ");
					rsa.unblockMessage(rsa.decryptedFileName);

				}
				catch(IOException f)
				{
					JOptionPane.showMessageDialog(null,"file not found.");
				}
				catch(NullPointerException p)
				{
					JOptionPane.showMessageDialog(null,"variable is null.");
				}

			}
			else if( (SwingUtilities.isLeftMouseButton(e)) && (temp.getText().equals("Create Keys")) )
			{
				/*
      String[] buttons = { "Pick Myself", "Generate Random Primes"};

      int rc = JOptionPane.showOptionDialog(null, "Would you like to supply your own prime numbers or have them generated?", "Confirmation",
                     JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]); */
				int rc = 0;

				if(rc == 0)
				{
					String prime = JOptionPane.showInputDialog("Please enter a prime number: ");
					String prime2 = JOptionPane.showInputDialog("Please enter a second prime number: ");

					// convert the prime numbers to ints
					//p = prime; 
					//q = prime2;

					boolean is_P_prime = isPrime(p);
					boolean is_Q_prime = isPrime(q);

					// check if chosen numbers are actually prime numbers
					if(is_P_prime == false)
					{
						String s = String.format("%d is not a prime number.",p);
						JOptionPane.showMessageDialog(null,s);
						return;
					}
					else if(is_Q_prime == false)
					{
						String s2 = String.format("%d is not a prime number.",q);
						JOptionPane.showMessageDialog(null,s2);
						return;

					}

					String pubName = JOptionPane.showInputDialog("Please enter the filename for the public key: ");
					String privName = JOptionPane.showInputDialog("Please enter the filename for the private key: ");

					try
					{
						key = new KeyPairGenerator(prime, prime2,pubName,privName);
					}
					catch(Exception c)
					{
						JOptionPane.showMessageDialog(null,"exception caught");
					}
				}
				else
				{
					//key = new KeyPairGenerator();


				}

			}



		} // end mouseClicked


	}// end mouseHandler class





} // end class minesweeper


