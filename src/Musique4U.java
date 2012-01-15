/*
Class Name: Musique4U.java
Author: Sunny Li, Leo Liu
Date: Dec 20, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: This is the main program.
		-It initializes the program by first prompting the user to log-in or register,
		-then it presents a menu for the user to do stuff with their playlists such as (add/del/mod music/video)
 */

import java.util.Scanner;

public class Musique4U {

	// stores the current signed-in user
	User user;
	// stores the current user's playlist data
	Playlist[] list;

	// class constructor
	public Musique4U() {

		// GET DATA REQUIRED FOR THIS PROGRAM TO FUNCTION
		// prompt for user to sign-in
		user = UserManager.getUser();
		// get user's playlist data
		list = PlaylistManager.initialize(user);

		// Program info
		System.out.println("Musique4U ALPHA v0.8");

		// presents program's main menu
		menu();

		// Terminate message
		System.out.println("Good-bye, Have a nice day!");
	}

	// This is where most of the program will run in
	void menu() {

		// for user input
		Scanner in = new Scanner(System.in);

		// variable to check whether to continue executing the program
		boolean quit = false;
		// string to store temporary input
		String temp = null;
		// stores the option the user have selected
		int choice;

		// A friendly greeting message
		System.out.println("Hello, " + user + ", what do you want to do today?");

		while (!quit) {
			try {
				// Give user options to manipulate their media objects
				System.out.println("Options:\n1. Manage Playlist\n2. Search\n3. Sharing\n4. Quit");

				// Command-Prompt looking prompt
				System.out.print(user + ": ");
				// get user input
				temp = in.next();
				// convert the inputed String into int type
				// maybe change it to text based when there is time
				choice = Integer.parseInt(temp);

				if (choice == 1) {
					System.out.println("TODO: Manage Playlist");
				} else if (choice == 2) {
					System.out.println("TODO: Search");
				} else if (choice == 3) {
					System.out.println("TODO: Share");
				} else if (choice == 4) {
					System.out.println("The application will now quit!");
					quit = true;
				} else {
					System.out.println("The choice that you have specified is invalid!");
				}

			} catch (NumberFormatException e) {
				System.out.println("The value \"" + temp
						+ "\" you have entered is not a valid integer.\nPlease input an integer!");
			}

		}
	}

}