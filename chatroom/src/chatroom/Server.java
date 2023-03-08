package chatroom;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server 
{
	//RESEARCH THESE OBJECTS!!!
	private ServerSocket listener;
	private Socket client;
	private DataInputStream in; //<- should this be out or in??
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(4);
	
	
	//constructor method
	public Server(int port) throws IOException
	{
			//ServerSocket object is instantiated, SERVER IS CREATED
			listener = new ServerSocket(port);
			System.out.println("server intialised under port: " + port);
		
			
			while (true) 
			{
				//two ports connected, CLIENT IS CONNECTED
				//blocking operating
				System.out.println("Waiting for client connection");
				client = listener.accept();
				System.out.println("Client accepted, handshake formed");
				
				//client handler object is created
				ClientHandler clientThread = new ClientHandler(client);
				
				//adds client thread object to array list
				clients.add(clientThread);
				//fucking fix this
				if(clientThread.getCoordinator());{}
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