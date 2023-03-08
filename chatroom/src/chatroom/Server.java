 package chatroom;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server 
{
	//RESEARCH THESE OBJECTS!!!
	
	//a server socket waits for incoming client requests
	private ServerSocket listener;	//used to listen for port
	private Socket client;			//actual port
	//private DataInputStream in; 	//input stream from client
	
	private static ArrayList<ClientHandler> clients = new ArrayList<>();	//array list to store clientHandler objects
	
	private static ExecutorService pool = Executors.newFixedThreadPool(4);
	
	
	//constructor method
	public Server(int port) throws IOException
	{
			//ServerSocket object is under the same listener, constructor argument is used as port
			listener = new ServerSocket(port);
			System.out.println("server intialised under port: " + port);
		
			
			//this code is now in a loop, as we want to accept more than once client
			while (true) 
			{
			
				System.out.println("---------------------------------");
				System.out.println("Waiting for client connection");
		
				//when a connection is accepted, a socket object is created on server side which = client
				client = listener.accept();		//blocking operation
				
				
				System.out.println("Client accepted, handshake formed");

				
				//client handler object is created, client socket is passed in
				ClientHandler clientThread = new ClientHandler(client, clients);
				
				//stores client thread in arraylist
				clients.add(clientThread);
				
				//??
				pool.execute(clientThread);
			}
	}//end of constructor
	
	
	public static void main(String[] args) throws IOException
	{
		Server server = new Server(9090);
		//Client client = new Client("Caman177" , "127.0.0.1" , 9090);

	}

} // End of Class

//incoming.accept = blocking operation/call - read up on this
//server blocking operation will end when a client socket is accepted/constructed