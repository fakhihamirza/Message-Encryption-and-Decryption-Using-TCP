import java.io.*;
import java.net.*;
import java.util.Scanner;
class Server
{
    public static void main(String a[])
        throws IOException
    {
        ServerSocket Server_socket=new ServerSocket(8001);  //creating a new server socket 
        Socket s =Server_socket.accept();  //accepts an incomming connection 
        DataInputStream in =new DataInputStream(System.in);  //sending message
        PrintStream dos = new PrintStream(s.getOutputStream()); 
        Scanner sc = new Scanner(System.in); 
        DataInputStream Recieve_data = new DataInputStream(s.getInputStream()); //recieving message
        while(true)
        {
            System.out.println("enter message to send to client:");
            String str = in.readLine();
            System.out.println(" Enter the key : ");
            int shift = sc.nextInt();
            String ciphertext = "";
            char alphabet;
            for(int i=0; i < str.length();i++) 
            {
                // Shift one character at a time
                alphabet = str.charAt(i);
                
                // if alphabet lies between a and z 
                if(alphabet >= 'a' && alphabet <= 'z') 
                {
                // shift alphabet
                alphabet = (char) (alphabet + shift);
                // if shift alphabet greater than 'z'
                if(alphabet > 'z') {
                    // reshift to starting position 
                    alphabet = (char) (alphabet+'a'-'z'-1);
                }
                ciphertext = ciphertext + alphabet;
                }
                
                // if alphabet lies between 'A'and 'Z'
                else if(alphabet >= 'A' && alphabet <= 'Z') {
                // shift alphabet
                alphabet = (char) (alphabet + shift);    
                    
                // if shift alphabet greater than 'Z'
                if(alphabet > 'Z') {
                    //reshift to starting position 
                    alphabet = (char) (alphabet+'A'-'Z'-1);
                }
                ciphertext = ciphertext + alphabet;
                }
                else {
                ciphertext = ciphertext + alphabet;   
                }
            
            }
            System.out.println(" ciphertext sent is : " + ciphertext);
            
            dos.println(ciphertext);
            sc.close();
            String str2 = Recieve_data.readLine();
            System.out.println("Message recieved from client" + str2);
            if(str.equals("end"))
            {
            Server_socket.close();  
            break;
            }
            if(str2.equals("end"))
            {
            Server_socket.close();
            break;
            }
        }
    }
}