import java.io.*;
import java.net.*;
import java.util.Scanner;
class Client
{
    public static void main(String args[])
        throws IOException
    {
        Socket s = new Socket("localHost",8001); //IP of server,tcp port
        DataInputStream Reciever=new DataInputStream(s.getInputStream()); //data stream?
        DataInputStream sender =new DataInputStream(System.in);
        PrintStream dos = new PrintStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            String str= Reciever.readLine();
            System.out.println( str + "---recieved from server, enter the key for decryption : ");
            int shift = sc.nextInt();
            String PlainText = "";
            char alphabet;
            for(int i=0; i < str.length();i++) 
            {
                // Shift one character at a time
                alphabet = str.charAt(i);
                
                // if alphabet lies between a and z 
                if(alphabet >= 'a' && alphabet <= 'z') 
                {
                // shift alphabet
                alphabet = (char) (alphabet - shift);
                // if shift alphabet greater than 'z'
                if(alphabet > 'z') {
                    // reshift to starting position 
                    alphabet = (char) (alphabet+'a'-'z'-1);
                }
                PlainText = PlainText + alphabet;
                }
                
                // if alphabet lies between 'A'and 'Z'
                else if(alphabet >= 'A' && alphabet <= 'Z') {
                // shift alphabet
                alphabet = (char) (alphabet - shift);    
                    
                // if shift alphabet greater than 'Z'
                if(alphabet > 'Z') {
                    //reshift to starting position 
                    alphabet = (char) (alphabet+'A'-'Z'-1);
                }
                PlainText = PlainText + alphabet;
                }
                else {
                PlainText = PlainText + alphabet;   
                }
            
            }
            System.out.println("MESSAGE RECIEVED FROM SERVER:"+ PlainText);
            System.out.println("ENTER MESSAGE TO SEND TO SERVER :");
            sc.close();
            String str2 = sender.readLine();
            dos.println(str2);
            if(PlainText.equals("end"))
            {
            s.close();
            break;
            }
            if(str2.equals("end"))
            {
            s.close();
            break;
            }
        }
    }
}
