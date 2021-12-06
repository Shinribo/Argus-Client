package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Network_Thread extends Thread{

	private Controller controller;
	private DUio duio;
	private Socket socket;
	private BufferedReader read;
	private PrintWriter write;
	private String input;
	
	private boolean stop = false;
	private String address = "localhost";
	private int port = 33669;
	
	
	public Network_Thread(Controller pController) {
		
		controller = pController;
		
		try {
			
			socket = new Socket(address, port);
		
			read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			write = new PrintWriter(socket.getOutputStream(),true);
			
			
		} catch (ConnectException e) {
			// Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Server nicht erreichbar");
			stop = true;
		
		} catch (IOException e) {
			// Auto-generated catch block
		
			if(Controller.debugMode) {
			
				e.printStackTrace();
			
			}
		
		}
		
		
	}
	
	
	
	public void run() {
		
		while(!stop) {
			
			try {
				
				if((input = read.readLine()) !=null) {
					
					duio.uploadToDU(input);
					
				}else {
					
					wait(50);
					
				}
				
			} catch (IOException e) {
				// Auto-generated catch block
				if(Controller.debugMode) {
				
					e.printStackTrace();
				
				}
				
			} catch (InterruptedException e) {
				
				//nothing
				if(Controller.debugMode) {
					
					e.printStackTrace();
					
					
				}
				
			}
			
		}
		
	}
	
	
	public void close() {
		
		stop = true;
		
		try {
			
			socket.close();
		
		} catch (IOException e) {
			// Auto-generated catch block
			
			if(Controller.debugMode) {
			
				e.printStackTrace();
			
			}
		}
		
	}
	
	
	public void sendData(String pData) {
		
		try {
			
			write.println(pData);
			
			
		}catch (NullPointerException e) {
			
			if(Controller.debugMode) {
			
				System.out.println("Server nicht erreichbar");
				System.out.println(pData);
			
			}
			
		}	
				
	}
	
	
	public void setDUio(DUio pDUio) {
		
		duio = pDUio;
		
	}
	
	
}
