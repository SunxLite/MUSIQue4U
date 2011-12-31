/*
Class Name: User.java
Author: Sunny Li, Leo Liu
Date: Dec 22, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Notepad++
Purpose: Template for the User object.
*/

class User{
	
	private int id;
	private String username;
	private String password;
	private String name;
	private int age;
	//private char gender; // this is not really necessary...
	
	public User(String[] data){ //User data passed in from String array
		id = Integer.parseInt(data[0]);
		username = data[1];
		password = data[2];
		name = data[3];
		age = Integer.parseInt(data[4]);
		//gender = data[4].charAt(0);
	}
	
	public String toString(){
		return "ID: "+id+"  |  Username: "+username+"  |  Name: "+name+"  |  Age: "+age;
	}
	
	public int getID(){
		return id;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getName(){
		return name;
	}
	
	public int getAge(){
		return age;
	}
	
}