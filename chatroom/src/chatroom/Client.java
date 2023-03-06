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
	private BufferedReader clientInput;
	private DataOutputStream out;
	
	
	//constructor method
	public Client(String userName, String ipAddress, int port) throws IOException
	{
		//with doing this you can't access these values outside of class, refer to client demo
		this.userName = userName;
		this.ipAddress = ipAddress;
		this.port = port;
		
		
		//client socket is created
		socket = new Socket(ipAddress, port);
		System.out.println("Connected to Server");
			
		
		//blocking call????
		clientInput = new BufferedReader(new InputStreamReader(System.in));	
		out = new DataOutputStream(socket.getOutputStream());
 	
	
		String message = "";
		while (!message.equals("Stop")) 
		{
			System.out.print("> ");
			message = clientInput.readLine();
			out.writeUTF(message);
		}
		
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
