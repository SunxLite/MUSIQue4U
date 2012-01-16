/*
Class Name: Musique4U.java
Author: Sunny Li, Leo Liu
Date: Dec 20, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: This is the main program.
		-It initializes the program by first prompting the user to log-in or register, and
		-then it presents a menu for the user to do stuff with their playlists such as add, delete, or modify their media objects
		-TODO: Enhancement cmd type prompt?
 */

import java.util.Scanner;

public class Musique4U {
	// Variables uses obviously naming
	// stores the current signed-in user
	private User user;
	// stores the current user's playlist data
	private Playlist[] playlists;
	// Check if the program should quit
	private boolean quit = false;

	// constructor - used as a program initializer
	public Musique4U() {

		// Program info
		System.out.println("Musique4U ALPHA v0.9\n");

		while (!quit) {
			// GET DATA REQUIRED FOR THIS PROGRAM TO FUNCTION
			// prompt for user to sign-in, get user account before proceeding.
			user = UserManager.getUser();
			// When user want to quit the program for some reason
			if (user == null) {
				quit = true;
			} else {
				// get user's playlists
				playlists = PlaylistManager.initialize(user);

				// presents program's main menu
				menu();
			}
		}

		// Terminating message
		System.out.println("Good-bye, Have a nice day!");
	}

	// This is where most of the program will run from
	void menu() {

		// for user input
		Scanner in = new Scanner(System.in);

		// variable to check whether to continue executing the program
		boolean loop = true;
		// string to store temporary input
		String temp = null;
		// stores the option the user have selected
		int choice;

		// A friendly greeting message
		System.out.println("Hello, " + user + ", what do you want to do today?");

		while (loop) {
			try {
				// Give user options to manipulate their media objects
				System.out.println("Options:\n1. Manage Playlist\n2. Search\n3. Sorting\n4. logout\n5. Quit\n");

				// Command-Prompt looking prompt
				System.out.print(user + ": ");
				// get user input
				temp = in.next();
				System.out.println(); // formating
				// convert the inputed String into int type
				// maybe change it to text based when there is time
				choice = Integer.parseInt(temp);

				if (choice == 1) {
					System.out.println("You have choose to manage your playlist");
					boolean stop = false;
					while (!stop){
					System.out.println("");
					}
				} else if (choice == 2) {
					System.out.println("Search");
				} else if (choice == 3) {
					System.out.println("Sort");
				} else if (choice == 4) {
					System.out.println("You are now logged out.");
					user = null;
					loop = false;
				} else if (choice == 5) {
					System.out.println("This application will now quit!");
					user = null;
					loop = false;
					quit = true;
				} else {
					System.out.println("The choice that you have specified is invalid!");
				}

			} catch (NumberFormatException e) { // When what is being entered is not an integer.
				System.out.println("The value \"" + temp
						+ "\" you have entered is not a valid integer.\nPlease input an integer!");
			}

			System.out.println(); // formating

		}
	}

}