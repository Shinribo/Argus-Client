package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import com.sun.jna.platform.win32.Advapi32Util;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;


public class DUio extends Thread{

	private Controller controller;
	private Network_Thread network_Thread;
	private File currentLogFile;
	private BufferedReader readLogFile;
	private BufferedWriter writeLuaFile;;
	private long sysTime;
	private long checkLogFilesDelay;
	private String input;
	private String data;
	
	private boolean stop = false;
	private String logFileDirectory = System.getProperty("user.home") + "\\AppData\\Local\\NQ\\DualUniverse\\log\\";
	private String luaFileDirectory = Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE,
                    "Software\\Novaquark\\DualUniverse\\Settings","InstallFolder") 
					+ "\\Game\\data\\lua\\argus";
	private String dataUpLinkFileName = "Argus_UpLink_";
	
	public DUio(Controller pController) {
		
		controller = pController;
		
		if(Controller.debugMode) {
			
			System.out.println("logFileDirectory:" + logFileDirectory);
			System.out.println("luaFileDirectory:" + luaFileDirectory);
			
		}
		
		checkLogFiles();
		
	}
	
	
	public void run() {
		
		while(!stop) {
			
			
			sysTime = System.nanoTime()/ 1000000000L;
			
			
			if(sysTime > checkLogFilesDelay) {
				
				checkLogFiles();
				checkLogFilesDelay = sysTime + 10;
				
			}
			
			
			if(currentLogFile != null) {
				
				try {
					
					if((input = readLogFile.readLine()) != null) {
						
						if(input.startsWith("<message>ARGUS")) {
						
							input = input.substring(14, input.length() - 10 );
							network_Thread.sendData(input);
						
						}
					}
					
				}catch (IOException e) {
					
					if(Controller.debugMode) {
					
						e.printStackTrace();
					
					}
					
				}
					
			}
			
		}
		
	}
	
	public void close() {
		

	    
	    stop = true;
	    
	    try {
	    
	      readLogFile.close();
	      
	    } catch (IOException e) {
	      // Auto-generated catch block
	      
	    	if(Controller.debugMode) {
	    	
	    		e.printStackTrace();
	    	
	    	}
	    	
	    }
		
		
	}
	
	private void checkLogFiles() {
	    
	    File folder = new File(logFileDirectory); // current directory
	    File[] files = folder.listFiles();
	    
	    //sorting
	    Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
	    
	    for(int i = 0 ; i< files.length ; i++) {
	      
	     
	      if(files[i].isFile()) { 
	        
	        //System.out.println(files[i].getName());
	        if(files[i] != currentLogFile) {
	        
	          setupLogFileReader(files[i]);
	        
	        }
	        
	        break;
	        
	      }
	      
	    }
	    
	  }
	  
	  
	  private void setupLogFileReader(File logFile) {
	    
	    
	    try {
	      
	      readLogFile = new BufferedReader(new FileReader(logFile),300000); //sollte für 3k Radar-Entrys reichen
	      currentLogFile = logFile;
	      
	    } catch (FileNotFoundException e) {
	      // Auto-generated catch block
	      
	    	if(Controller.debugMode) {
	    	
	    		e.printStackTrace();
	    	
	    	}
	    }
	    
	    
	    //dumping old entrys
	    try {
	      
	      while(readLogFile.ready()) {
	        
	        //nothing
	        readLogFile.readLine();
	        
	      }
	      
	    } catch (IOException e) {
	      // Auto-generated catch block
	      
	    	if(Controller.debugMode) {
	    	
	    		e.printStackTrace();
	    	
	    	}
	    	
	    }
	    
	    
	  }
	
	
	  public void setNetwork_Thread(Network_Thread pNetwork_Thread) {
		  
		  network_Thread = pNetwork_Thread;
		  
	  }
	  
	  
	  public void uploadToDU(String pData) {
		  
		  File luaFile = new File(luaFileDirectory + dataUpLinkFileName + "0" + ".lua");
		  
		  if(luaFile.exists()) {
			  
			  luaFile.delete();
			  
		  }
		  
		  
		  try {
			  
			data = "function getData() return \"" + pData + "\" end ";
			  
			writeLuaFile = new BufferedWriter(new FileWriter(luaFile));
			writeLuaFile.write(data);
			writeLuaFile.close();
			
		  } catch (IOException e) {
			// Auto-generated catch block
			
			  if(Controller.debugMode) {
			  
				  e.printStackTrace();
			  
			  }
			  
		  }
		  
		  
	  }
	  
}
