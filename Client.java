import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.Scanner;
class Client
{
    public static String Hashing(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) 
                  hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
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
            System.out.println( "\n\n"+ str + "\nThis data is recieved from server! \n\nEnter the key for decryption : ");
            int shift = sc.nextInt();
            String cipherString = str.substring(str.indexOf("-") + 1, str.indexOf("*"));
            String hash_recieved =str.substring(str.indexOf("*") +1 , str.length());
            System.out.println("\n Hash Recieved : "+hash_recieved);
            String hash_new = Hashing(cipherString);
            System.out.println("\nNew hash calculated : "+hash_new);

            if (hash_new.equals(hash_recieved))
            {
                System.out.println("\n\nHASH COMPARISION SUCCESSFUL!! \n");
            }
            else
            {
                System.out.println("\n\nHASH COMPARISION UN-SUCCESSFUL, DATA TEMPERED!! \n");
            }

            String PlainText = "";
            char alphabet;
            for(int i=0; i < cipherString.length();i++) 
            {
                // Shift one character at a time
                alphabet = cipherString.charAt(i);
                
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
            String str2 = sender.readLine();
            dos.println(str2);
            if(PlainText.equals("end"))
            {
                sc.close();
                s.close();
                break;
            }
            if(str2.equals("end"))
            {
                sc.close();
                s.close();
                break;
            }
        }
    }
}
