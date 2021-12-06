package main;

public class Controller {

	private DUio duio;
	private Network_Thread network_Thread;
	
	private Shutdown_Hook shutdown_Hook = new Shutdown_Hook(this);
	
	
	public static void main(String[] args) {
		// Auto-generated method stub
		new Controller();
		
	}

	public Controller() {
		
		Runtime.getRuntime().addShutdownHook(shutdown_Hook);
		
		duio = new DUio(this);
		network_Thread = new Network_Thread(this);
		
		duio.setNetwork_Thread(network_Thread);
		network_Thread.setDUio(duio);
		
		duio.start();
		network_Thread.start();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public void stop(){
		
		try {
		
	
	
	
	
			Runtime.getRuntime().removeShutdownHook(shutdown_Hook);
			duio.close();
			network_Thread.close();
			
		} catch(Exception e) {
			
			//
			
		}
		
	}	
	
}









final class Shutdown_Hook extends Thread{
	
	Controller controller;
	
	public Shutdown_Hook(Controller pController) {
		
		controller = pController;
		
	}
	
	
	public void run() {
		
		controller.stop();
		
	}
	
}
