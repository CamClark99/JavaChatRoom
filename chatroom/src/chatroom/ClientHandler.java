package chatroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable 
{
	//why static? | allows sharing accross all instances
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList();
	
	private Socket socket;			//accepted connection socket
	private BufferedReader in;		//receives data
	private BufferedWriter out;		//outputs data
	
	private String clientUsername;
	private Boolean isCoordinator;
	
	
	//constructor method
	public ClientHandler(Socket socket) 
	{
		try 
		{
			this.socket = socket;

			//output stream = stream characters out
			this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			//reads data sent from socket
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//username data is taken from stream
			this.clientUsername = in.readLine();
		
			//revise
			clientHandlers.add(this);
			
			
			broadcast("Server: " + clientUsername + " has entered the chat");
		
		}
		
		catch(IOException e) {closeAll(socket, in, out);}
	} //end of constructor
	
	
	
	//where client input comes in
	@Override
	public void run() 
	{
		
		String message;
		
		sendToSelf("Type /help for a list of commands, or type anything to broadcast a message to all conneted clients.");
		
		while(socket.isConnected()) 
		{
			try 
			{
				//reads data sent from data stream REVISE
				//takes input or reads from console?
				message = in.readLine();
			
				
				
				//if message sent from client contains a command (starts with a /)
				if(containsCommand(message))
				{
					//separates command from message and stores in string
					String command = message.split(" ")[1];
					
					switch(command) 
					{
						case "/help":
							sendToSelf("Here is a list of commands!");	
							break;
						
						case "/kick":
							//implement kick feature, only coordinator can do this
							break;
						
						case "/tell":
							//UNFINISHED
							String username = message.split(" ")[2];
							message = message.split(" ")[3];
							privateMessage(message, username);
							break;
					
					}
				}
				
				else{broadcast(message);}
			} 
			
			catch(IOException e) 
			{
				closeAll(socket, in, out);
				break;
			} 
		}
	} //end of run
	
	
	
	//tests to see if command character has been entered by client
	// class methods
	
	//tests for command from client
	public Boolean containsCommand(String message) 
	{
		if(message.split(" ")[1].startsWith("/")) 
		{
			return true;
		}
		else {return false;}
	}
	
	
	
	//broadcasts message to all clients except sender
	//broadcasts message to all except sender
	public void broadcast(String message) 
	{
		for(ClientHandler clients : clientHandlers) 
		{
			try 
			{
				//prevents message being sent to self
				if (!clients.clientUsername.equals(clientUsername))
				{
					clients.out.write(message);
					clients.out.newLine();
					clients.out.flush();
				}
			}
			catch (IOException e) {closeAll(socket, in, out);}
		}
	}
	
	
	
	//sends message to specified user
	public void privateMessage(String message, String userName) 
	{
		for(ClientHandler clients : clientHandlers) 
		{
			try 
			{
				if(clients.clientUsername.equals(userName))
				{
					clients.out.write(clientUsername + message);
					clients.out.newLine();
					clients.out.flush();
				}
			}
			catch (IOException e) {closeAll(socket, in, out);}
		}
	}
	
	
	//broadcasts message to all clients including sender
	//broadcasts to every user, including client sending the message
	public void sendToAll(String message) 
	{
		for(ClientHandler clients : clientHandlers) 
		{
			try 
			{
					clients.out.write(message);
					clients.out.newLine();
					clients.out.flush();
			}
			catch (IOException e) {closeAll(socket, in, out);}
		}
	}
	
	
	
	//add description
	//sends message to self
	public void sendToSelf(String message) 
	{
		for(ClientHandler clients : clientHandlers) 
		{
			try 
			{
				//sends message to self
				if (clients.clientUsername.equals(clientUsername))
				{
					clients.out.write(message);
					clients.out.newLine();
					clients.out.flush();
				}
			}
			catch (IOException e) {closeAll(socket, in, out);}
		}
	}
	
	
	
	//add description
	//removes specified client
	public void removeClient()
	{
		clientHandlers.remove(this);// Revise
		broadcast("Server: " + clientUsername + " has left the chat");
	}


	
	//closes socket and streams
	public void closeAll(Socket socket, BufferedReader in, BufferedWriter out) 
	{
		removeClient();
		try 
		{
			if(in != null){in.close();}
			if(out != null){out.close();}
			if(socket != null){socket.close();}
		}
		catch (IOException e){e.printStackTrace();}
	}

} //end of class

