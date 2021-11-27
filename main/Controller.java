package main;

public class Controller {

	
	private Shutdown_Hook shutdown_Hook = new Shutdown_Hook(this);
	
	
	public static void main(String[] args) {
		// Auto-generated method stub
		new Controller();
		
	}

	public Controller() {
		
		Runtime.getRuntime().addShutdownHook(shutdown_Hook);
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public void stop(){
		Runtime.getRuntime().removeShutdownHook(shutdown_Hook);
		
		
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
