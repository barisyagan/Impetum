package com.impetum.server.Utilities;

import java.sql.*;
import java.util.LinkedList;

import com.impetum.server.HighScore;



public class DatabaseManager {
	String databaseName;
	Connection connection;
	Statement statement;
	 public DatabaseManager (String databaseName) {
		 
		this.databaseName = "jdbc:sqlite:" + databaseName;	
		
		try{
			
			//Create a database connection
			connection = DriverManager.getConnection(this.databaseName);
			statement = connection.createStatement();
			statement.setQueryTimeout(20);  
			
		
			//If tables do not exist in database this code creates highscores and userlogs tables.
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet highscoreTables = dbm.getTables(null, null, "Highscores", null);
			if (!highscoreTables.next()) {
				this.updateSQL("create table Highscores (Score int, Nickname string)");
			}
			ResultSet UserLogsTables = dbm.getTables(null, null, "UserLogs", null);
			if (!UserLogsTables.next()) {
				this.updateSQL("create table UserLogs (Userip string, Timestamp string, Status string)");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 
	public void deleteAllValuesOnHighScoreTable(){
		
		try {
			statement.executeUpdate("DELETE FROM Highscores");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void dropTable(String tableName) {
		 try
		    {
		      statement.executeUpdate("drop table if exists " + tableName);
		    }
		 catch (Exception e){
			 e.printStackTrace();
		 }
	}


	public void insertPlayerToHighscoreTable(String nickname, int score) {
		try{
	      statement.executeUpdate("INSERT INTO Highscores (Score, Nickname) VALUES ("+score+", '" +nickname+"')");
	    }
		catch (Exception e){
		 e.printStackTrace();
		}
	}


	public void printHighScoreTable() {
		try{
	      ResultSet rs = statement.executeQuery("select * from Highscores");
	      while(rs.next()){
	        System.out.println("Nickname: " + rs.getString("Nickname") + "Score: " + rs.getInt("Score"));  
	      }
	   }
		catch (Exception e){
		 e.printStackTrace();
		}
		
	}
	
	//it returns sorted list highscore table list
	
	public LinkedList<HighScore> getHighScores(){
		LinkedList<HighScore> list = new LinkedList<HighScore>();
		try{
			//Sorting by Score column
		      ResultSet rs = statement.executeQuery("select * from Highscores ORDER BY Score DESC");
		      while(rs.next()){
		        String username =  rs.getString("Nickname"); 
		        int userScore = rs.getInt("Score");  
		        HighScore userHighScore = new HighScore(username,userScore);
		        list.add(userHighScore);
		        
		      }
		   }
			catch (Exception e){
			 e.printStackTrace();
			}
		return list;
	}

	//This method allows us to reach database. (Select keyword does not work on this method.)
	public void updateSQL(String SQL) {
		try { 
	      statement.executeUpdate(SQL); 
	    }
		catch (Exception e){
		 e.printStackTrace();
		}
		
	}


	public void printUserLogTable() {
		try {
			ResultSet rs = statement.executeQuery("select * from UserLogs");
			while(rs.next()){
				System.out.println("UserIp: " + rs.getString("Userip") + " TimeStamp: " + rs.getString("Timestamp") + " Status: " + rs.getString("Status"));
			}
    
	   }
		catch (Exception e){
		 e.printStackTrace();
		}
		
	}


	public void insertPlayerToUserLogs(String userip, String timestamp,
			String status) {
			try{
		      statement.executeUpdate("INSERT INTO UserLogs (Userip, Timestamp, Status) VALUES ('"+userip+"',  '" +timestamp+"'  , '" + status + "'         )");
		    }
			catch (Exception e){
			 e.printStackTrace();
			}
		
		
	}


	public void deleteAllTable(String tableName) {
			try{
		      statement.executeUpdate("Delete From " + tableName );
		    }
			catch (Exception e){
			 e.printStackTrace();
			}
		
	}
	 
}
