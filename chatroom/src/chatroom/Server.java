package chatroom;
import java.io.*;
import java.net.*;

public class Server 
{
	//RESEARCH THESE OBJECTS!!!
	private ServerSocket listener;
	private Socket client;
	private DataInputStream in; //<- should this be out or in??
	
	//constructor method
	public Server(int port) throws IOException
	{
			//ServerSocket object is instantiated, SERVER IS CREATED
			listener = new ServerSocket(port);
			System.out.println("server intialised under port: " + port);
		
			
			
			//two ports connected, CLIENT IS CONNECTED
			//blocking operating
			System.out.println("Waiting for client connection");
			client = listener.accept();
			System.out.println("Client accepted, handshake formed");
			
			
			
			
			//takes input from client socket
			in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			
			//declares empty string allowing while loop to run
			String message = "";
			//while client message unequal to stop
			while(!message.equals("stop")) 
			{
				//read input from client, and give value to var message
				message = in.readUTF();
				System.out.println(message);
			}
			
			
			
			
			
			//when loop is broken/termination value is entered, close client connection and inputstream
			System.out.println("Closing connection...");
			client.close();
			in.close();
	}
	
	
	public static void main(String[] args) throws IOException
	{
		Server server = new Server(9090);
		//Client client = new Client("Caman177" , "127.0.0.1" , 9090);

	}

} // End of Class

//incoming.accept = blocking operation/call - read up on this
//server blocking operation will end when a client socket is accepted/constructed