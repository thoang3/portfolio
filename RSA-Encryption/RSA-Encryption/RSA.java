/* Authors: Mike McClory and Tung Hoang
 * Class: CS342 Software Design
 * Instructor: P.Troy
 * Project 3: RSA Encrypt/Decrypt
 * 
 * Conatins: RSA class, functions for:
 *     - encryption
 *     - decrpytion
 *     - blocking
 *     - unblocking
 * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


class RSA
{
  static String publicKeyFileName;   // name of file containing the public key
  static String privateKeyFileName;  // name of file containg the private key
  static String blockedFileName;     // name of file containing blocked message
  static String encryptedFileName;   // name of file containing encrypted message
  static String decryptedFileName;   // name of file containing decrypted message
  static String messageFile;         // name of file containing original message
  
  
  
  // 
  // blocks the message given as the first argument with block size of the second argument
  //
  public void blockMessage(String inputFile, int blockSize) throws FileNotFoundException, NullPointerException
  { 
    StringBuilder[] build = new StringBuilder[30];
    for(int n=0;n<30;n++) //initialize each string builder in the array
      build[n] = new StringBuilder();
    
    String[] message = new String[10]; 
    String[] blocked = new String[25];
    String pad = "0"; //padding string for end of blocked file(if necessary)
    
    
    int linesNeeded = 0; // lines needed in blocked file
    int j = 0, pos = 0, x = 1, slot = 0;                        // loop variables and variables to keep count
    int len = 0, count = 0, numLines = 0, totalLength = 0;     // of the lengths of the individual lines of the message
                                                               // as well as the total length of the message.
    
    message = readFromFile(inputFile); // read lines from file into string array
    
    
    for(int n=0;n<10;n++)
      if(message[n] != null )
    {
          numLines++;
          totalLength = totalLength + message[n].length();
    }
   
    
    for(int i=0;i<numLines;i++)
    {
      len = message[i].length(); // get the length of the string without the end terminator
      
      while(j < len) 
      {
        //System.out.println(String.format("in while...%d",len));
        if((int) message[i].charAt(j) > 15)
        {
          //System.out.println(String.format("char = %c",message[i].charAt(j)));
          
          if( (j > 0) && ((int)message[i].charAt(j-1) == ' '))
          {
            int ch = ((int) message[i].charAt(j) - 27) *  10;
            build[pos].insert(0,ch);
            j++;
          }
          else
          {
            build[pos].insert(0,((int) message[i].charAt(j) - 27));
            j++;
          }
          
        }
        else if((int) message[i].charAt(j) == 0)
        {
          //System.out.println(String.format("char = %c",message[i].charAt(j)));
          build[pos].insert(0,0).insert(0,0);
          j++;
        } 
        else if((int) message[i].charAt(j) > 8)
        {
          //System.out.println(String.format("char = %c",message[i].charAt(j)));
          build[pos].insert(0,(int) message[i].charAt(j) - 8);
          j++;
        }
        count++;
       
        // if characters read equals block size, add current string to output array and increment index for temp string array
        if(count == (x*blockSize)) 
        {                                                      
         blocked[slot] = build[pos].toString();
         //System.out.println(String.format("build %d = %s", slot, build[pos].toString()));
         slot++;
         pos++;
         x++;
        }
         
      }
        int remainder = 0, lines = 0;
        
        remainder = totalLength % blockSize;
        
        //find if any padding is needed at end of the file(i.e. '00')
        if(totalLength % blockSize == 0)
        {
          lines = totalLength / blockSize;
        }
        else
        {
          lines = (totalLength / blockSize) + 1;
        }
        
        
        // add padding ('00') to last string if there would be space left over (i.e. a remainder)
        if( (message[i+1] == null) && ( remainder != 0) )
        {
          for(int k=0;k<remainder;k++)
          {  
            build[pos].insert(0,0).insert(0,0);
            blocked[slot] = build[pos].toString();
          }
          //System.out.println(String.format("build %d = %s", slot, build[pos].toString()));
        }
      
    }
     
    String filename = JOptionPane.showInputDialog("Enter the filename for the blocked message to be placed: ");
    writeToFile(filename,blocked); //write the new "blocked" message to a new file
    
  }// end of blockMessage
  
  
  
  // 
  // unnblocks the given message 
  //
  public void unblockMessage(String inputFile) throws FileNotFoundException, NullPointerException
  { 
    StringBuilder[] build = new StringBuilder[30];
    
    for(int n=0;n<30;n++) //initialize each string builder in the array
      build[n] = new StringBuilder();
    
    String temp;
    String temp2;
    String[] message = new String[10]; 
    String[] unblocked = new String[25];
    
    int j = 0, pos = 0, slot = 0, x = 0;                      // loop variables and variables to keep count
    int len = 0, count = 0, numLines = 0, totalLength = 0;    // of the lengths of the individual lines of the message
    char c;                                                   // as well as the total length of the message.
    
    message = readFromFile(inputFile); // read lines from file into string array
    
    for(int n=0;n<10;n++)
      if(message[n] != null )
    {
          numLines++;
    }
    
    for(int i=0;i<numLines;i++)
    {
      len = message[i].length() - 1; // get the length of the string without the end terminator
      j = len;
      temp = "";
      
      while(j >= 0) 
      {
        temp = "";
        //System.out.println(String.format("in while.... %d",len));
        temp = temp + message[i].charAt(j-1) + message[i].charAt(j);
        x = Integer.parseInt(temp);
       
        if( (x > 0) && (x < 5) )
        {
          x = x + 8;
          c = (char) x;
          //System.out.println(String.format("in if 1....%d",numLines));
          //System.out.println(String.format("char = %c",c));
          build[pos].append(c);
          j = j - 2;
        }
        else if( x >= 5)
        {
          x = x + 27;
          c = (char) x;
          //System.out.println(String.format("in if 2....%d",numLines));
          //System.out.println(String.format("char = %c",c));
          build[pos].append(c);
          j = j - 2;
        }
        else if(x == 0)
        {
          //System.out.println(String.format("in if 2...."));
          j = j - 2;
        }
        
      if((j == 0) || (j == -1) ) // put current temp string into output array and increment temp array index
      {
         unblocked[slot] = build[pos].toString();
         //System.out.println(String.format("build %d = %s", slot, build[pos].toString()));
         slot++;
         pos++;
      } 
      //System.out.println(String.format("j = %d", j));
     } 
      
     
        
    }
    
    String filename = JOptionPane.showInputDialog("Enter the filename for the unblocked message to be placed: ");
    writeToFile2(filename,unblocked); //write the new "unblocked" message to a new file
    
  }// end of unblockMessage
  
  
  
  // 
  // encrypts a blocked message 
  //
  public void EncryptMessage(String inputFile, String keyFile ) throws FileNotFoundException, NullPointerException
  { 
    String[] message = new String[10]; // will contain the lines of the blocked message
    String[] encrypted = new String[25]; // will contain the encrypted lines
    Key key = new Key(new BigInt(4),new BigInt(4)); // the public or private key holder
    List<BigInt> array = new ArrayList<BigInt>(); // array the keys will be read into
    BigInt cm;      // input number from encrpyting or decrypting
    BigInt result = new BigInt(); // output number from encrpyting or decrypting
    List<BigInt> eNums = new ArrayList<BigInt>();
    StringBuilder[] build = new StringBuilder[30];
    
    for(int n=0;n<30;n++) //initialize each string builder in the array
      build[n] = new StringBuilder();
    
    // read message and private key from their files
    key.readPublicKeyFromFile(keyFile);
    message = readFromFile(inputFile);
    
    
    // get the keys
    array = key.getKey();
    
    int i = 0, j = 0;
    
    while(message[i] != null)
    {
      System.out.println(message[i]);
      cm = new BigInt(message[i]);
      
      //encrypt the number
      result = cm.encrypt(cm, key);
      
      for(int k = 0; k < result.array.size(); k++)
      {
        System.out.println("in encrypt for.....");
        build[j].insert(0,(result.array.get(k)));
      }
      
      encrypted[i] = build[j].toString();
      
      j++;
      i++;
      System.out.print("Encrypt value: ");
      result.printB(result);
    }
    
    
    String filename = JOptionPane.showInputDialog("Enter the filename for the encrypted message to be placed: ");
    writeToFile(filename, encrypted); //write the new "encrypted" message to a new file
    
    
  }// end of EncryptMessage
  
   
  
  // 
  // decrypts an encrypted message 
  //
  public void DecryptMessage(String inputFile, String keyFile ) throws FileNotFoundException, NullPointerException
  { 
    String[] message = new String[10]; // will contain the lines of the blocked message
    String[] decrypted = new String[25]; // will contain the encrypted lines
    Key key = new Key(new BigInt(4),new BigInt(4)); // the public or private key holder
    List<BigInt> array = new ArrayList<BigInt>(); // array the keys will be read into
    BigInt cm;      // input number from encrpyting or decrypting
    BigInt result = new BigInt(); // output number from encrpyting or decrypting
    StringBuilder[] build = new StringBuilder[30];
    
    for(int n=0;n<30;n++) //initialize each string builder in the array
      build[n] = new StringBuilder();
    
    // read message and private key from their files
    key.readPrivateKeyFromFile(keyFile);
    message = readFromFile(inputFile);
 
    // get the keys
    array = key.getKey();
    
    int i = 0, j = 0;
    
    while(message[i] != null)
    {
      System.out.print(String.format("%d = ",i));
      System.out.println(message[i]);
      cm = new BigInt(message[i]);
      
      //decrypt number
      result = cm.decrypt(cm, key);
      
      for(int k = 0; k < result.array.size(); k++)
      {
        build[j].insert(0,(result.array.get(k)));
      }
      
      decrypted[i] = build[i].toString();
      
      i++;
      j++;
      System.out.print("Decrypt value: ");
      result.printB(result);
    }
    
    
    
    
    String filename = JOptionPane.showInputDialog("Enter the filename for the decrypted message to be placed: ");
    writeToFile(filename, decrypted); //write the new "encrypted" message to a new file
    
    
  }// end of DecryptMessage
  
  
  
  //
  // reads a message from a file (not files containing the a key)
  //
  public String[] readFromFile(String filename) throws FileNotFoundException
  {
    //System.out.println(String.format("name = %s", rsa.messageFile));
    String line;
    File file = new File(filename);
    Scanner sc;
    
    // check whether file exists or not
    if(file.exists())
    {
     sc = new Scanner(file);
   
     String[] words = new String[10];
     int j = 0;
     
     // read file line-by-line, parse into words and put into array of strings
     while((line = sc.nextLine()) != null) 
     {
       words[j] = line;   
       j++;
  
       if(!sc.hasNextLine())
       { 
        //System.out.println(words[1]);                                             
        return words;
       }   
     }
    }
    else
    {
      JOptionPane.showMessageDialog(null,"File not found.");
    }
    return null;
    
  } // end of readFromFile
  
  
  
  //
  // wirte blocked message to a file
  //
  public void writeToFile(String filename, String[] lines) throws FileNotFoundException
  {
    File f = new File(filename);
    FileWriter fWriter;
    PrintWriter pWriter;
    
    try
    {
      fWriter = new FileWriter (f);
      pWriter = new PrintWriter (fWriter);
      
     int i = 0;
     while(lines[i] != null)
     {
       //System.out.println(String.format("char = %s",lines[i]));
       pWriter.println(lines[i]);
       i++;
     }
    
     pWriter.close();
     JOptionPane.showMessageDialog(null,"Write to file successful.");
    }
    catch(IOException e)
    {
      JOptionPane.showMessageDialog(null,"File not found.");
    }
    //PrintWriter pWriter = new PrintWriter (fWriter);
    
  } // end of writeToFile

  
  
  //
  // wirte an unblocked message to a file
  //
  public void writeToFile2(String filename, String[] lines) throws FileNotFoundException
  {
    File f = new File(filename);
    FileWriter fWriter;
    PrintWriter pWriter;
    
    try
    {
      fWriter = new FileWriter (f);
      pWriter = new PrintWriter (fWriter);
      
     int i = 0;
     while(lines[i] != null)
     {
       //System.out.println(String.format("char = %s",lines[i]));
       pWriter.print(lines[i]);
       i++;
     }
    
     pWriter.close();
     JOptionPane.showMessageDialog(null,"Write to file successful.");
    }
    catch(IOException e)
    {
      JOptionPane.showMessageDialog(null,"File not found.");
    }
    //PrintWriter pWriter = new PrintWriter (fWriter);
    
  } // end of writeToFile

} // end of RSA class















