package chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 
 * @author cameronclark
 * 
 */
public class Server 
{
	//instance variables
	private ServerSocket listener;	//Object is used to wait for connection
	

	//constructor method
	public Server(ServerSocket listener) 
	{
		//assign listener object as instance variable
		this.listener = listener;
	} 
	
	
	public void startServer() 
	{
		System.out.println("Server Running..");
		try 
		
		{	
			//while listener socket is open
			while(!listener.isClosed()) 
			{
				//outputs a client socket once a connection is accepted
				Socket socket = listener.accept();	//blocking method
				
				System.out.println("A new client has connected..");
				
				//passes through socket object
				ClientHandler clientHandler = new ClientHandler(socket);
				
				//creates thread of clienthandler
				Thread thread = new Thread(clientHandler);
				
				//runs thread
				thread.start();
			}
		}
		
		catch (IOException e) {System.out.println(e);}
	}
	
	
	public void closeServerSocket()
	{
		try{if (listener != null) {listener.close();}}
		catch (IOException e ) {e.printStackTrace();}
	}
	
	
	
	
	public static void main(String[] args) throws IOException 
	{
		//
		Server server = new Server(new ServerSocket(9090));
		
		//
		server.startServer();
	}
	
} //end of class
