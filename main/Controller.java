package main;

public class Controller {


	final static boolean debugMode = true;
	
	
	private DUio duio;
	private Network_Thread network_Thread;

	
	private Shutdown_Hook shutdown_Hook = new Shutdown_Hook(this);
	
	
	public static void main(String[] args) {
		// Auto-generated method stub
		new Controller();
		
	}

	public Controller() {
		
		Runtime.getRuntime().addShutdownHook(shutdown_Hook);
		
		
		
		
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
