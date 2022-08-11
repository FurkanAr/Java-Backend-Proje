// Server side 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Server class
public class Server {
	
	public static void main(String [] args)  {
		
		try {
			// Server is listening on port 3000
			ServerSocket listener = new ServerSocket(3000);
			
			// infinite loop for getting client request
			while(true) {
				System.out.println("Server is running.. "
						+ "This is a number prediction game");
				
				//socket object to receive incoming client requests
				Socket socket = listener.accept();
				
				// create new threads for clients 
				ClientHandler clientSocket = new ClientHandler(socket);
				
				// Invoke the start method
				new Thread(clientSocket).start();
		
			}
			// catch the exceptions 
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	
	}


