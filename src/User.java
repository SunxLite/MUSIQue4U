/*
Class Name: User.java
Author: Sunny Li, Leo Liu
Date: Dec 22, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: Template for the User object.
 */

class User {

	private int id;
	private String username;
	private String password;
	private String name;
	private int age;

	// private char gender; //unnecessary field.

	public User(int id, String username, String password, String name, int age) { // User data passed in from String array
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.age = age;
	}

	public String toString() {
		return "ID: " + id + "  |  Username: " + username + "  |  Name: " + name + "  |  Age: " + age;
	}

	public int getID() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

}