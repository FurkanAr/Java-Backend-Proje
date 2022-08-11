// Client part
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// Client Class
public class Client {
	
	public static void main(String[] args) throws IOException {
		//localhost ip
		String host = "127.0.0.1";
		// server port 
		int port = 3000;
		
		// establish the connection with localhost and server port
		try (Socket socket = new Socket(host, port)){
			Scanner scan = new Scanner (System.in);
			
			//InputStream is used to read data from server.
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			//OutputStream is used for writing data to server.
			PrintWriter printWriter = new PrintWriter(
					socket.getOutputStream(),true);
			
			// Request a username and number from client
			System.out.println("Enter name..");
			String username = scan.nextLine();
			printWriter.println(username);
			System.out.println("Enter a number between 0-100..");
			
			// communication between ClientHandler and Client
			// it loop every time when predictions are not correct
			while (true) {
				
				// sends message to ClientHandler 
				String msgToSend = scan.nextLine();
				printWriter.println(msgToSend);
				
				// reads messeage from ClientHandler
				String msgFromServer = bufferedReader.readLine();
				
				// Shows message from ClientHandler in console
				System.out.println(msgFromServer);
				
				// If prediction is correct client is shutdown
				if (msgFromServer.contains("Congratulations!")) {
					printWriter.close();
					bufferedReader.close();
					socket.close();
					break;
				}
			}

		//catch the exceptions
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}