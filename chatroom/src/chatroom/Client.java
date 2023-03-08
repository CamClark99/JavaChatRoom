package chatroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client 
{
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
	private String username;

	public Client(Socket socket, String username)
	{
		try 
		{
			this.socket = socket;
		
			this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.username = username;
		} 
		
		catch(IOException e) 
		{
			closeAll(socket, in, out);
		}
	}
	
	
	public void sendMessage() 
	{
		try 
		{
			out.write(username);
			out.newLine();
			out.flush();
			
			Scanner scanner = new Scanner(System.in);
			while(socket.isConnected()) 
			{
				String message = scanner.nextLine();
				out.write(username + ": " + message);
				out.newLine();
				out.flush();
			}
		}
		
		catch(IOException e) 
		{
			closeAll(socket, in, out);
		}
	}
	
	
	public void listenForMessage() 
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				String msgFromGroup;
				
				while(socket.isConnected()) 
				{
					try 
					{
	
						msgFromGroup = in.readLine();
						System.out.println(msgFromGroup);
					} catch (IOException e) {closeAll(socket, in, out);}
				}
			}
		}).start();
	}
	
	
	public void closeAll(Socket socket, BufferedReader in, BufferedWriter out) 
	{
		try 
		{
			if(in != null){in.close();}
			if(out != null){out.close();}
			if(socket != null){socket.close();}
		}
		catch (IOException e){e.printStackTrace();}
	}
	
	
	
	
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter your username for the group chat: ");
		String username = scanner.nextLine();
		
		Socket socket = new Socket("localhost", 9090);
		Client client = new Client(socket, username);
		
		client.listenForMessage();
		client.sendMessage();
		
		scanner.close();
	}
	
} //end of class
