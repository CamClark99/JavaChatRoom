package chatroom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client 
{
	//instance variables
	//should this be private???
	public String userName;
	private String ipAddress;
	private int port;
	
	private Socket socket;	
	private BufferedReader clientInput; //client input to be taken from keyboard
	private DataOutputStream out;		//data to be streamed out via socket
	
	
	//constructor method
	public Client(String userName, String ipAddress, int port) throws IOException
	{
		//without doing this you can't access these values outside of class, refer to client demo
		this.userName = userName;
		this.ipAddress = ipAddress;
		this.port = port;
		
		
		//client socket is created
		socket = new Socket(ipAddress, port);
		System.out.println("Connected to Server");
			
		
		//input reader is created
		clientInput = new BufferedReader(new InputStreamReader(System.in));	
		
		//output stream is created
		out = new DataOutputStream(socket.getOutputStream());
 	
	
		String message = "";
		while (!message.equals("Stop")) 
		{
			System.out.print("> ");
			
			//input is taken from keyboard
			message = clientInput.readLine();
			
			//message is sent to output stream
			out.writeUTF(message);
		}
		
		//streams and sockets are closed, once loop is terminated
		clientInput.close();
		out.close();
		socket.close();
	
		
	}//end of constructor method 

	
	public static void main(String[] args) throws IOException
	{
		
		//Server server = new Server(9090);
		Client client = new Client("Caman177" , "127.0.0.1" , 9090);

	}

	
}//end of class
