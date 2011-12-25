/*
Class Name: Musique4U.java
Author: Sunny Li, Leo Liu
Date: Dec 19, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Notepad++
Purpose: This is the main program.
*/

import java.util.Scanner;

public class Musique4U{
	
	//store the current instance of user
	User user;
	//store the current user's playlist
	Playlist[] list;
	
	//class constructor
	public Musique4U(){
		
		//GET DATA REQUIRED FOR THIS PROGRAM TO FUNCTION
		//prompt for user
		user = UserManger.getUser();
		//get user's playlist
		list = PlaylistManger.initialize(user);
		
		//Program info
		System.out.println("Musique4U ALPHA v0.1\nby Sunny Li and Leo Liu");
		
		//prompt
		menu();
		
		//Final message
		System.out.println("Good-bye, Have a nice day!");
	}
	
	public void menu(){
		
		//for user input
		Scanner in = new Scanner(System.in);
		
		//variable to check whether to continue executing the program
		boolean quit = false;
		//string to store tempory input
		String temp;
		//stores the option the user have selected
		int choice;
		
		//Greeting message
		System.out.println("Hello, "+user+", what do you want to do today?");
		
		while (!quit){
			try{
				//Give user options
				System.out.println("Options:\n1. Manage Playlist\n2. Search\n3. Sharing\n4. Quit");
				
				//Prompt looking prompt
				System.out.print(User+": ");
				//get user input
				temp = in.next();
				//convert the inputted String into int type
				choice = Integer.parseInt(temp);
				
				if (choice == 1){
					System.out.println("TODO: Manage Playlist");
				} else if (choice == 2){
					System.out.println("TODO: Media Search");
				}else if (choice == 3){
					System.out.println("TODO: Media Share");
				}else if (choice == 4){
					System.out.println("The application will now quit!");
					quit = true;
				} else {
					System.out.println("The choice that you have specified is invalid!");
				}
				
			} catch (InputMismatchException mismatch){
				System.out.println("The value \"+"temp"+\" you have entered is not a valid integer.\nPlease input an integer!");
			}
			
		}
	}
	
}