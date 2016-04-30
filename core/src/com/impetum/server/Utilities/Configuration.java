package com.impetum.server.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.StringTokenizer;

public class Configuration {
	private Properties prop;
	private InputStream input;
	private BufferedReader reader ;
	public String data;
	public String value;
	private File file;
	
	public Configuration(File file){
		this.file = file;
		
	}
	
	public String getProperty(String property){		
		return prop.getProperty(property);		
	}

	public void cleanUp() {
		
		
	}
	public void setData(String value,String operator){
		data = null;
		String result;
		this.setValue(value);
		try {
			input = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(input));
			 String data;
				while((data = reader.readLine( )) != null){
					String line = " ";
					StringTokenizer stringTokenizer = new StringTokenizer(data,operator);
			        while (stringTokenizer.hasMoreElements()) {
			            if(stringTokenizer.nextToken().equals(value)){
			            	line = stringTokenizer.nextElement().toString();
			            	if(line!=null){
			            	this.data = line;
			            	System.out.println(data);
			            	}
			            }
				}
			       
				}
				
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				if (reader != null)
					reader.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
}
	public void setValue(String value) {
	this.value = value;
		
	}

	public String getData(){
		if(this.isDataAvailable()){
		return this.data;
		}else{
			return "There is no " + this.value + " in file";
		}
	}
	public boolean isDataAvailable(){
		if(this.data!=null){
			return true;
		}
		return false;
	}
	
}