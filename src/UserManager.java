/*
Class Name: UserManager.java
Author: Sunny Li, Leo Liu
Date: Dec 20, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Notepad++
Purpose: A library class that manages the user object
*/

import java.util.Scanner;

class UserManager{
	//global Scanner
	static Scanner in = new Scanner(System.in);
	//tempory user storage
	static User user; // PUT THE USER INSIDE METHOD... JUST TESTING!!
	
	static User getUser(){
		//prompt the user to login
		System.out.println("Please login:\n1.Existing User\n2.Register");
		
		boolean selected = false;
		String temp;
		int choice;
		
		while(!selected){
			try{
				System.out.print("Option: ");
				temp = in.next();
				choice = Integer.parseInt(temp);
				
				if (choice == 1){
					user = login();
					selected = true;
				}else if(choice == 2){
					user = register();
					selected = true;
				}else{
					System.out.println("The choice that you have specified is invalid.");
				}
				
				//if (user != null) //this is needed for GUI... if there is one.
				return user;
				
			} catch (InputMismatchException mismatch){
				System.out.println("The value \""+temp+"\" you specified is not an integer.\nPlease input an integer!");
			}
		}
	}
	
	static User login(){
		boolean hasUser = false;
		String username, pass;
		
		System.out.println("Login Form");
		
		while (!hasUser){
			System.out.print("Username: ");
			username = in.next();
			System.out.print("Password: ");
			pass = in.next();
			
			if (FileManger.userExist(username)){
				if (FileManger.checkPass(username, pass)){
					user = FileManger.loadUser(username);
					hasUser = true;
				}else{
					System.out.println("Incorrect Password!");
				}
			}else{
				System.out.println("The username you have specified does not exist!");
			}
			
			/*if (username.equalsIgnoreCase("back") || pass.equealsIgnoreCase("back")){
				System.out.println("Back to user menu.");
				return null;
			}*/
		}
		return user;
	}
	
	static User register(){
		boolean regComplete = false;
		String username, password, name;
		int age;
		
		System.out.println("Please fill in the information below to register a new user.");
		
		while(!regComplete){
			
			System.out.print("Desired Username: ");
			username = in.next();
			
			if (!FileManager.userExist(username)){
				
				System.out.print("Password: ");
				password = in.next();
				System.out.print("Name: ");
				name = in.next();
				System.out.print("Age: ");
				try{
				age = Integer.parseInt(in.next());
				
				//Create the new user
				user = new User(-1, username, password, name, age);
				FileManager.addUser(user);
				regComplete = true;
				
				}catch(InputMismatchException mismatch){
					System.out.println("You did not input an integer..\nPlease re-register!");
				}
				
			}else{
				System.out.println("The username that you want already exist!\nPlease try another one.");
			}
		}
		return user;
	}
}