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
	//why static?
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList();
	
	private Socket socket;
	private BufferedReader in;		//receives data
	private BufferedWriter out;		//outputs data
	
	private String clientUsername;
	
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
		
			//no fucking idea
			clientHandlers.add(this);
			
			broadcast("Server: " + clientUsername + " has entered the chat");
		
		}
		
		catch(IOException e) {closeAll(socket, in, out);}
	}
	
	//where client input comes in
	@Override
	public void run() 
	{
		String message;
		
		while(socket.isConnected()) 
		{
			try 
			{
				message = in.readLine();
				broadcast(message);
				
				if(message.startsWith("/")) 
				{
					if(message.contains("help")) 
					{
						for(ClientHandler clients : clientHandlers) 
						{
							if(clients.clientUsername.equals(clientUsername)) 
							{
								clients.out.write("here is a list of all commants:");
								clients.out.newLine();
								clients.out.flush();
							}
						}
					}
				}
			
			} 
			
			catch(IOException e) 
			{
				closeAll(socket, in, out);
				break;
			} 
		}
	}
	
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
	
	
	public void getCoordinator() 
	{
		if (clientHandlers.isEmpty())
		{
			
		}
	}
	
	
	public void removeClient()
	{
		clientHandlers.remove(this);// how the fuck does this work
		broadcast("Server: " + clientUsername + " has left the chat");
	}


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
}

