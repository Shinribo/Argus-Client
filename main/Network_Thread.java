package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Network_Thread extends Thread{
	public Network_Thread(Controller pController) {
		this.start();
	}
	public void run() {
		
		while(!stop) {
			
			
			
			
			
		}
		
	}
	
	
	public void close() {
		
		stop = true;
		
		
		
	}
}
