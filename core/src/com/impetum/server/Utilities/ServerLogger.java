package com.impetum.server.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.*;
import java.lang.Object;
import java.io.*;

public class ServerLogger {
	private File file;
	public Logger logger = Logger.getLogger(ServerLogger.class.getName());
	private FileHandler filehandler;

	//Basic constructor
		public ServerLogger(){
			try{
				filehandler = new FileHandler("./serverlog.log");  
				}catch (IOException e){
					e.printStackTrace();
				}
		}
		//Constructor with file variable
		public ServerLogger(File file) {
			this.file = file;
			try{
			filehandler = new FileHandler(file.toString());
			}catch (IOException e){
				e.printStackTrace();
			}
		}

		public void writeInfoLog(String msg) {
			try {
				logger.addHandler(filehandler);
				logger.info(msg);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}

		public void writeWarningLog(String msg) {
			try {
			
				logger.addHandler(filehandler);
				logger.warning(msg);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		public void writeFineLog(String msg) {
			try {
				logger.addHandler(filehandler);
				logger.fine(msg);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		public void writeErrorLog(String msg) {
			try {
				logger.addHandler(filehandler);

				logger.severe(msg);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		public void writeBooleanLog(Boolean bool,String error_msg,String info_msg){
			if(!bool){
				this.writeErrorLog(error_msg);
			}else{
				this.writeInfoLog(info_msg);
			}
		}
		public void writeIntLog(int value,String error_msg,String info_msg){
			if(value==0){
				this.writeErrorLog(info_msg);
			}else{
				this.writeInfoLog(error_msg);
			}
		}
		//to check int functions which return 0
		public void writeBasicIntLog(int value){
			if(value==0){
				this.writeErrorLog("Value is 0");
			}else{
				this.writeInfoLog("Value is not 0");
			}
		}
		//to check Boolean functions which is True
		public void writeBasicBooleanLog(Boolean bool){
			if(!bool){
				this.writeErrorLog("Value is false");
			}else{
				this.writeInfoLog("Value is true");
			}
		}
		public String getFilePath(){
			return this.file.toString();
		}
		public File setFile(File file){
			return this.file=file;
		}
		public void setFileDirectory(String filepath) {
			try{
			this.filehandler = new FileHandler(filepath);
			}catch(IOException e){
				e.printStackTrace();
			}
		}

}
