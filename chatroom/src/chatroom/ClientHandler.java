package chatroom;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	//instance variables
	//RESEARCH THESE OBJECTS!!!
	private ServerSocket listener;
	private Socket client;
	private DataInputStream in; //<- should this be out or in??
	
		
	public ClientHandler(Socket clientSocket) throws IOException
	{
		this.client = clientSocket;
		in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
	}
	
	
	@Override
	public void run() 
	{
		try
		{
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
		}
		
		catch(IOException e) 
		{
			System.out.println(e);
		}
		
		finally 
		{
			try 
			{
				System.out.println("Closing connection...");
				client.close();
				in.close();
			} 
			
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}//end of constructor 
	
	
	public Boolean getCoordinator() 
	{
		return true;
	}
}//end of class
