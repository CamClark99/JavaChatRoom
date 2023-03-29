package client;

/*
 * This class represents a simple chat client which handles the functionality of setting a
 * specified IP address, port number, and user-name via command line arguments. //CHANGE
 */

public class ChatClient {
	//ALL OF THESE ARE PRIVATE SO THEY CANNOT BE SET OR ACCESSED OUTSIDE OF THIS CLASS, USING SETTERS
	// Set default values for connection parameters
    private String address = "127.0.0.1";
    private String username = "";
    private int port = 1500;
	
    
	//private and static bcos they are only used in this class
    //why 
	private static String setIP(String[] args) 
	{
		// Define a regular expression pattern to validate IP addresses
		String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		
		// Validate the first command line argument as an IP address or "localhost"
        if (args[0].matches(ipPattern) || args[0].equals("localhost"))
        {
        	String address = args[0];
        	return address;
        } 
        else 
        {
        // Throw an IllegalArgumentException if the IP address or hostname is invalid
        throw new IllegalArgumentException("Invalid IP address format or hostname.");
        }
	} 
	
	
	// Sets client port
	private static int setPort(String[] args) 
	{
		   try 
           {
               int portNumber = Integer.parseInt(args[1]);
               
               // Validate that the port number is within a valid range
               if (portNumber >= 1024 && portNumber <= 65535)
               {
                  int port = portNumber;
                  return port;
               } 
               else 
               {
                   // Throw an IllegalArgumentException if the port number is invalid
                   throw new IllegalArgumentException("Port number must be between 1024 and 65535.");
               }
           } 
           catch (NumberFormatException e) 
           {
               // Throw a NumberFormatException if the port number is not a valid integer
               throw new NumberFormatException("Invalid port number.");
           }
	}
	
	
	// Sets client username
	public static String setUsername(String[] args) 
	{
		String username = args[2];
		
		// Validate the username to ensure it meets certain requirements
		if (username.isEmpty())
        {
            // Throw an IllegalArgumentException if the username is empty
            throw new IllegalArgumentException("Username cannot be empty.");
        } 
        else if (username.length() > 20) 
        {
            // Throw an IllegalArgumentException if the username is exceeds 20 characters
            throw new IllegalArgumentException("Username cannot be longer than 20 characters.");
        }
        else if (username.length() <= 2) 
        {
        	// Throw an IllegalArgumentException if the username is less than or equal to 2 characters
            throw new IllegalArgumentException("Username must be at least 3 characters long.");
        }
        else if (username.matches("(?i).*\\b(admin)\\b.*")) 
        {
            // Throw a SecurityException if the username contains the word "admin"
            throw new SecurityException("Username cannot contain 'admin'.");
        }
        else {return username;}
	}
	
	
	// Driver class
    public static void main(String[] args)
    {
    	ChatClient chatClient = new ChatClient();
    	
        // Parse command line arguments to override default connection parameters
        if (args.length >= 1) 
        { 
        	chatClient.address=setIP(args); 	
        }
        // Parse the second command line argument as a port number (optional)
        if (args.length >= 2) 
        {
        	chatClient.port=setPort(args);
        }
        // Parse the third command line argument as the username (optional)
        if (args.length >= 3) 
        {
        	chatClient.username=setUsername(args);
        }
        
        //WE HERE
        // Create a new network client object, passing in command line parameters
        NetworkClient networkClient = new NetworkClient(chatClient.address, chatClient.port, chatClient.username);

           	// Start the client and check if it has successfully started
            if (networkClient.isConnected())
            {
            // Create a new UserInput object and pass in the client
            	//IS THIS BAD BCOS OBJECTS ARE GETTING MADE ALL THE TIME IDK, maybe move outside of loop
            UserInput userInput = new UserInput(networkClient);
            // Call the handleUserInput() method to handle user input
            userInput.displayCommands();
            userInput.handleInput();
            } 
           
    }
}