package chatroom;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
	//instance variables
	//RESEARCH THESE OBJECTS!!!
	private ServerSocket listener;
	private Socket client;
	
	private DataInputStream in; //Input stream from client
	
	private ArrayList<ClientHandler> clients;
	
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException
	{
		//socket connection passed from server
		this.client = clientSocket;
		
		//arrayList of client threads
		this.clients = clients;
		//takes input from client socket stream
		in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
	}
	
	
	//code to be executed across multiple threads
	@Override
	public void run() 
	{
		try
		{
			//difference from being here or constructor???
			//in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			
			//declares empty string allowing while loop to run
			String message = "";
			
			//while client message unequal to "stop"
			while(!message.equals("stop")) 
			{
				//read input sent from client, stores in variable
				message = in.readUTF();
				
				//print input from client to server
				System.out.println(message);
			}
		}
		
		catch(IOException e) {System.out.println(e);}
		
		finally 
		{
			try 
			{
				//stream and socket is closed once loop is terminated
				System.out.println("Closing connection...");
				client.close();
				in.close();
			} 
			
			catch (IOException e) {e.printStackTrace();}
			
		}
	}//end of constructor 
	
	
	
	
}//end of class
