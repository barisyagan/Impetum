package com.impetum.server.Utilities;

public class ShutdownHook extends Thread {
	private Configuration config;
	
	public ShutdownHook(Configuration config){
		this.config = config;
	}
	
	@Override
	public void run(){
		System.out.println("Shutdown Signal is received cleaning up!");
		if(config != null){
			config.cleanUp();
		}
	}

}
