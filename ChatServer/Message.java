//package hw4;

import java.io.*;
import java.util.Arrays;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8440551716461118987L;
	private int from;
	private int[] to=null;
	private String msg=null;
	private String[] list=null;
	
	public Message(String name){							//Message of log in
		from = -1;
		msg = name;
	}
	
	public Message(){										//Message of quit
		from = -2;
	}
	
	public Message(int[] numbers,String[] names){			//Message of user list
		from = -3;
		to = numbers;	//Array of client numbers;
		list = names;	//Array of client names;
	}
	
	
	public Message(int myNumber, int[] destination, String content){	//Message
		from = myNumber;
		to = destination;
		msg = content;
	}
	
	public int showType(){		//return the type of message. 
		if (getFrom() >0){
			if (list == null) return 0;					//0 for normal message
		}
		else if (getFrom() == -1){
			if (to == null  && list == null) return 1;	//1 for log in message
		}
		else if (getFrom() == -2){							//2 for quit message
			if (to == null && msg == null && list == null) return 2;
		}
		else if (getFrom() == -3){						// list?
			if (msg == null && to.length == list.length) return 3;					//3 for user list
		}
		return -1;										//-1 for error
	}
	
	public void showMessage(){	//Output information to the console
		if (getFrom() >0){
			System.out.println("A message from " + getFrom() + ":");
			System.out.println("To :" + Arrays.toString(to));
			System.out.println("Content: " + msg);
			if (list == null) return;
		}
		else if (getFrom() == -1){
			System.out.println("The firste message from " + msg + " to the Server.");
			if (to == null  && list == null) return;
		}
		else if (getFrom() == -2){
			System.out.println("The quiting message");
			if (to == null && msg == null && list == null) return;
		}
		else if (getFrom() == -3){
			System.out.println("A user list message");
			System.out.println(Arrays.toString(to));
			System.out.println(Arrays.toString(list));
			if (msg == null) return;
		}
		
		System.out.println("Error!");
	}
	
	public static void main(String[] args){
		
		Message m1 = new Message("name");
		Message m2 = new Message();
		int[] test = new int[10];
		String[] test2 = new String[10];
		test2[0] = "A!";
		Message m3 = new Message(test,test2);
		m1.showMessage();
		m2.showMessage();
		m3.showMessage();
		
		try {
			FileOutputStream fs = new FileOutputStream("test.txt");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(m1);
			os.writeObject(m2);
			os.writeObject(m3);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		test2[1] = "B!";
		int[] test3 = {2,4,5};
		Message m4 = new Message(1,test3,"test");
		m3.showMessage();
		m4.showMessage();
		try {
			FileOutputStream fs = new FileOutputStream("test2.txt");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(m3);
			os.writeObject(m4);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream fs = new FileInputStream("test.txt");
			ObjectInputStream os = new ObjectInputStream(fs);
			Message m5 =(Message) os.readObject();
			Message m6 =(Message) os.readObject();
			Message m7 =(Message) os.readObject();
			os.close();
			m5.showMessage();
			m6.showMessage();
			m7.showMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream fis = new FileInputStream("test2.txt");
			ObjectInputStream os = new ObjectInputStream(fis);
			Message m8 =(Message) os.readObject();
			Message m9 =(Message) os.readObject();
			os.close();
			m8.showMessage();
			m9.showMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int findNumber(String name){
		if (showType() != 3) {
			System.err.println("Try to findNumber not in a user list message!");
			return -1;
		}
		int l = to.length;
		for (int i = 0; i<l; i++){
			if(name.compareTo(list[i]) == 0) return to[i];
		}
		
		System.err.println("Fail to find this name is the user list!");
		System.exit(1);
		return -1;
	}
	
	public int getUserLength(){
		if (showType() != 3) {
			System.err.println("Try to findNumber not in a user list message!");
			return -1;
		}
		return to.length;
	}
	
	public int getUserNum( int i){
		if (showType() != 3) {
			System.err.println("Try to findNumber not in a user list message!");
			return -1;
		}
		return to[i];
	}
	public String getUserName( int i){
		if (showType() != 3) {
			System.err.println("Try to findNumber not in a user list message!");
			return null;
		}
		return list[i];
	}

	public int getFrom() {
		return from;
	}

	public String getMessage() {
		return msg;
	}
	
	public int[] getTo() {
		return to;
	}
}
