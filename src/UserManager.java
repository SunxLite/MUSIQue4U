/*
Class Name: UserManager.java
Author: Sunny Li, Leo Liu
Date: Dec 20, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: A library class that manages the user object.
		It contains methods that specifically:
		- presents options for the user to log-in
		- method for logging-in part
		- method for the register part
 */

import java.util.Scanner;

class UserManager {
	// global Scanner since this is going to be used throughout
	private static Scanner in = new Scanner(System.in);
	// Temporary user storage - since it's used in multiple methods
	private static User user;

	// Give sign-in options to user
	static User getUser() {

		System.out.println("Please login:\n1.Existing User\n2.Register\n3.Quit\n");

		// Check if user selected
		boolean selected = false;

		// user inputs
		String temp = null;
		int choice;

		while (!selected) {
			try {
				System.out.print("Option: ");
				temp = in.next();
				choice = Integer.parseInt(temp);

				if (choice == 1) {
					user = login();

					if (user != null) // when the user quits from the interface without signing-in
						selected = true;

				} else if (choice == 2) {
					user = register();

					if (user != null)
						selected = true;
					
				} else if (choice == 3) {
					
					// clear the user
					user = null;
					selected = true;
					
				} else {
					System.out.println("The choice that you have specified is invalid. Please try again!");
				}

			} catch (NumberFormatException mismatch) {
				System.out.println("The value \"" + temp + "\" you specified is not an integer.\nPlease input an integer!");
			}
		}
		return user;
	}

	// prompt the user to login
	static User login() {
		boolean hasUser = false;
		String username, pass;

		System.out.println("\nLogin Form (input \"back\" to cancel operation)");

		while (!hasUser) {
			System.out.print("Username: ");
			username = in.next();
			System.out.print("Password: ");
			pass = in.next();
			System.out.println(); // formating

			if (username.equalsIgnoreCase("back") || pass.equalsIgnoreCase("back")) {
				System.out.println("Cancelling operation, reverting back to user menu.\n");
				return null;
			}

			if (FileManager.userExist(username)) {
				if (FileManager.checkPass(username, pass)) {
					user = FileManager.loadUser(username);
					hasUser = true;
				} else {
					System.out.println("Incorrect Password!");
				}
			} else {
				System.out.println("The username you have specified does not exist!");
			}
			System.out.println(); //formating
		}
		return user;
	}

	// prompt the user to register
	static User register() {
		boolean regComplete = false;
		String username, password, name;
		int age;

		System.out.println("Please fill in the information below to register a new user.");

		while (!regComplete) {

			System.out.print("Desired Username: ");
			username = in.next();

			if (!FileManager.userExist(username)) {

				System.out.print("Password: ");
				password = in.next();
				System.out.print("Name: ");
				name = in.next();
				System.out.print("Age: ");
				try {
					age = Integer.parseInt(in.next());

					// Create the new user
					user = new User(-1, username, password, name, age); // -1 is a temporary ID which will be overwritten
					FileManager.addUser(user);
					if (FileManager.userExist(username)) {
						regComplete = true;
						// Reload the user with correct corresponding ID
						user = FileManager.loadUser(username);
					}

				} catch (NumberFormatException mismatch) {
					System.out.println("You did not input an integer..\nPlease re-enter your information");
				}

			} else {
				System.out.println("The username that you want already exist!\nPlease try another one.");
			}
		}
		return user;
	}
}