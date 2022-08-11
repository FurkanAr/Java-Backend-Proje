
// ClientHandler provides connection between server and multiple clients 
// with the helps of Runnable interface
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

// ClientHandler Class
class ClientHandler implements Runnable{
// Socket for communication 
	private final Socket socket;
	// Constructor 
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	
	// When an object implementing interface Runnable is used to 
	// create a thread, starting the thread causes the object's run method to
	// be called in that separately executingthread.
	@Override
	public void run() {
		// Input and output streams for communication 
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;

		try {
			bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(),true);	
			
			// Receive the username from client
			String name = bufferedReader.readLine();
			System.out.println(name  + " connected..");
			
			// Random method gives a random number for prediciton game 
			Random r=new Random();
			
			// random number between 0-100
			int number =r.nextInt(101);
			
			// counter counts the steps
			int counter=1;
			
			while (true) {
				// reads response from client
				String msgFromClient = bufferedReader.readLine();
				
				// response message is in String format, parseInt method
				// convert string to int
				int prediction = Integer.parseInt(msgFromClient);
				System.out.println(name + ": " + msgFromClient);
				
				// if prediction is equal to random number it sends 
				// congrats message and close the socket for connection
				if (number == prediction) {
					
					System.out.println(name + " find the " + number + 
							" in step " +counter+ "." );
					String message = "Server: "
							+ "Congratulations! You find the "+ number +
							" in step " + counter+"." ;
					printWriter.println(message);
					
					printWriter.close();
					bufferedReader.close();
					socket.close();
					System.out.println(name + " has left..");
					break;
				}
				// if prediction not equal to random number it sends 
				// message to client
				else {	
					if (number < prediction) {
						printWriter.println("Server: Your guess is greater than the number!");
						counter++;
					}
					else {
						printWriter.println("Server: Your guess is less than the number!");
						counter++;
					}
				}
			}
			// catch the exceptions
		}catch (IOException e) {
			e.printStackTrace();
			
			// at the end of try block closes the using socket,
			// input and output streams
		}finally {
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
				bufferedReader.close();
				socket.close();
			}
				
			// catch the exceptions
			}catch (IOException e) {
				e.printStackTrace();
				}
			
		}
	}		
}
	
	

	