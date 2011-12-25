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
	static User user; //?!?
	
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
				
				return user;
				
			} catch (InputMismatchException mismatch){
				System.out.println("The value \""+temp+"\" you specified is not an integer.\nPlease try again!");
			}
		}
		
	}
	static User login(){
		//send info to filereader... when confirmed that the data matches, call the file reader again and read in user's data.
	}
	static User register(){
		//make the user object in here and return it.
	}
}