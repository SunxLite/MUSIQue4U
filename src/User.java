/*
Class Name: User.java
Author: Sunny Li, Leo Liu
Date: Dec 22, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: Template for the User object.
			-The user object contains an unique ID and username
			-Personal information including name and age
			-Also a password to keep profile secure from vandalism
 */

class User {
	// keep these parameters secretive...
	private int id;
	private String username;
	private String password;
	private String name;
	private int age;

	public User(int id, String username, String password, String name, int age) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.age = age;
	}

	// GETTERS BELOW
	public String toString() { // same as getName()
		return name;
	}

	String info() {
		return "ID: " + id + "  |  Username: " + username + "  |  Name: " + name + "  |  Age: " + age;
	}

	int getID() {
		return id;
	}

	String getUsername() {
		return username;
	}

	String getPassword() {
		return password;
	}

	String getName() {
		return name;
	}

	int getAge() {
		return age;
	}

}