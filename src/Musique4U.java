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
		System.out.println("Musique4U V1.0\n");

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

				if (choice == 1) { // Manage Playlist, create playlist, add or remove media from playlist.
					System.out.println("You have choose to manage your playlist");

					System.out.println("1. Create New Playlist\n2. Edit Existing Playlist");
					choice = Integer.parseInt(in.next());

					if (choice == 1) { // Creating new playlist
						System.out.println("Creating new playlist");
						System.out.println("Name of new playlist: ");
						String newName = in.nextLine().trim();
						// Create a blank playlist, check if the same name exist.
						boolean available = true;
						for (int i = 0; i < playlists.length; i++) {
							if (newName.equalsIgnoreCase(playlists[i].getName())) {
								available = false;
							}
						}

						if (available) {
							Playlist newList = new Playlist(newName, null);
							System.out.println("You are creating a new playlist with the name \"" + newName
									+ "\"\nAre you sure you want to add it to your collection? (y/n)");
							String confirmation = in.next();
							if (confirmation.charAt(0) == 'y') {
								// copy old array to another array and append an extra field
								Playlist[] arr2 = new Playlist[playlists.length + 1];
								for (int i = 0; i < playlists.length; i++) {
									arr2[i] = playlists[i];
								}
								// append the new playlist to playlist array.
								arr2[playlists.length] = newList;
								System.out.println("Playlist added to your collection");
							} else {
								System.out.println("You have cancelled the operation");
							}
						} else {
							System.out.println("Sorry, you already have a playlist under the name \"" + newName + "\"");
						}

					} else if (choice == 2) { // Modifying existing playlist
						System.out.println("Please select your playlist:");
						Playlist selected = null;

						// show all the playlist the current user have
						PlaylistManager.display(playlists);

						System.out.print("\nSelect the playlist where you want to add or delete media (Name): ");
						String name = in.next();

						for (int i = 0; i < playlists.length; i++) {
							if (name.equalsIgnoreCase(playlists[i].getName())) {
								selected = playlists[i];
							}
						}

						System.out.println("The selected playlist contains the following");
						PlaylistManager.display(selected);

						System.out.println("What do you want to do with this playlist?\n1.Add Media\n2.Remove Media"); // and
																														// modify..
						choice = Integer.parseInt(in.next());

						if (choice == 1) { // adding media
							System.out.println("Adding Media. Please provide the following information");
							System.out.print("Music or Video?\n1. Music   2. Video");
							String[] info = new String[4]; // quickly store the required parameters
							choice = Integer.parseInt(in.next());
							if (choice == 1 || choice == 2) {
								System.out.print("Title: ");
								info[0] = in.next();
								System.out.print("Genre: ");
								info[1] = in.next();
								if (choice == 1) {
									System.out.print("Artist: ");
									info[2] = in.next();
									System.out.print("Album: ");
									info[3] = in.next();

									System.out.print("Adding to playlist");
									selected.addMedia(new Music(Media.total + 1, info[0], info[1], info[2], info[3]));
								} else if (choice == 2) {
									System.out.print("Duration(# of seconds): ");
									info[2] = in.next(); // Catch this error..
									System.out.print("Rating: ");
									info[3] = in.next();

									System.out.print("Adding to playlist");
									// used protected data..
									try {
										selected.addMedia(new Video(Media.total + 1, info[0], info[1], Double
												.parseDouble(info[2]), info[3]));
									} catch (NumberFormatException e) {
										System.out.println("The information that you have provided is invalid. Quitting.");
									}
								}

							} else {
								System.out.println("Invalid choice!");
							}

						} else if (choice == 2) { // removing media
							System.out.println("Removing Media. Please provide the following information");
							System.out.print("Music or Video?\n1. Music   2. Video");
							String[] info = new String[4]; // quickly store the required parameters
							choice = Integer.parseInt(in.next());
							if (choice == 1 || choice == 2) {
								System.out.print("Title: ");
								info[0] = in.next();
								System.out.print("Genre: ");
								info[1] = in.next();
								if (choice == 1) {
									System.out.print("Artist: ");
									info[2] = in.next();
									System.out.print("Album: ");
									info[3] = in.next();

									System.out.print("Adding to playlist");
									selected.removeMedia(new Music(-1, info[0], info[1], info[2], info[3])); // ID doesn't
																												// matter
								} else if (choice == 2) {
									System.out.print("Duration(# of seconds): ");
									info[2] = in.next(); // Catch this error..
									System.out.print("Rating: ");
									info[3] = in.next();

									System.out.print("Adding to playlist");
									// used protected data..
									try {
										selected.removeMedia(new Video(-1, info[0], info[1], Double.parseDouble(info[2]),
												info[3]));
									} catch (NumberFormatException e) {
										System.out.println("The information that you have provided is invalid.\nQuitting.");
									}
								}
							}
						}
					}

				} else if (choice == 2) {
					System.out.println("Search all Media");
					System.out.println("Please enter the fields you want to search for (\".\" to ignore the field)");

					// Search prompt
					// General data
					System.out.print("ID: ");
					String id = in.next(); // take in as String but parse later.
					if (id.equalsIgnoreCase(".")) {
						id = null;
					}
					System.out.print("Title: ");
					String title = in.next();
					if (title.equalsIgnoreCase(".")) {
						title = null;
					}
					System.out.print("Genre: ");
					String genre = in.next();
					if (genre.equalsIgnoreCase(".")) {
						genre = null;
					}

					// Secondary data
					System.out.print("Media type (Music/Video/Null) : ");
					String type = in.next();
					if (!type.equalsIgnoreCase("Music") && !type.equalsIgnoreCase("Video")) {
						type = null;
					}

					String spec1 = null;
					String spec2 = null;

					if (type != null) {
						if (type.equalsIgnoreCase("Music")) {

							System.out.print("Artist: ");
							spec1 = in.next();
							if (spec1.equalsIgnoreCase(".")) {
								spec1 = null;
							}
							System.out.print("Album: ");
							spec2 = in.next();
							if (spec2.equalsIgnoreCase(".")) {
								spec2 = null;
							}

						} else if (type.equalsIgnoreCase("Video")) {

							System.out.print("Duration: ");
							spec1 = in.next();
							if (spec1.equalsIgnoreCase(".")) {
								spec1 = null;
							}
							System.out.print("Rating: ");
							spec2 = in.next();
							if (spec2.equalsIgnoreCase(".")) {
								spec2 = null;
							}
						}
					}

					String[] req = { type, id, title, genre, spec1, spec2 };

					System.out.println("The results are: ");
					Playlist result = new Playlist("search result", PlaylistManager.search(playlists, req));
					PlaylistManager.display(result);

				} else if (choice == 3) {

					System.out.println("Sort all Media");
					System.out.println("How do you want your playlist to sort by?");
					System.out.println("1. Title     2. Genre     3. Artist     4. Album     5.Duration     6.Rating");
					choice = Integer.parseInt(in.next());
					String req[] = new String[2];
					if (choice == 1) {
						req[0] = ""; // Empty String means General fields
						req[1] = "title";
					} else if (choice == 2) {
						req[0] = "";
						req[1] = "genre";
					} else if (choice == 3) {
						req[0] = "music";
						req[1] = "artist";
					} else if (choice == 4) {
						req[0] = "music";
						req[1] = "album";
					} else if (choice == 5) {
						req[0] = "video";
						req[1] = "duration";
					} else if (choice == 6) {
						req[0] = "video";
						req[1] = "rating";
					}

					System.out.println("Sorted: ");
					PlaylistManager.display(PlaylistManager.sort(playlists, req));

				} else if (choice == 4) {
					System.out.println("You have logged out.");
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