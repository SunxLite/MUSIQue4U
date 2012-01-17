/*
Class Name: Musique4U
Author: Sunny Li, Leo Liu
Date: Dec 20, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: This is the main class. It initializes the program by prompting the user to log-in or register, then it presents
			a menu to the user to edit their playlists such as to add, delete, or modify their media in their playlist.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Musique4U {
	// Stores the current signed-in user
	private User user;
	// Stores the current user's playlist data
	private Playlist[] playlists;

	// A global BufferedReader to get user input, more reliable than Scanner
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	// Check if user want to exit program
	private boolean quit = false;

	// Constructor - starts to program
	public Musique4U() {

		// Program info
		System.out.println("Musique4U V1.1\n");

		while (!quit) {
			// Before the program can function properly, we need to get the user info.
			// Runs method which prompt user to sign-in, get user account before proceeding.
			user = UserManager.getUser();

			// For cases when the user want to quit program prior to logging in.
			if (user == null) {
				quit = true;
			} else { // Receives user object, continue.

				// Get user's playlists
				playlists = PlaylistManager.initialize(user);
				// presents program's main menu for user to edit their playlists
				menu();
			}
		}

		// Final message before program halts.
		System.out.println("Good-bye, Have a nice day!");
	}

	// Main menu providing options to user for playlist editing.
	private void menu() {

		// Variable to check whether to continue looping the prompts.
		boolean loop = true;
		// Temporary variable to store Strings
		String temp = null;
		// Stores number choices the user have selected
		int choice;

		// A friendly greeting message
		System.out.println("Hello, " + user + ", what do you want to do today?");

		while (loop) {
			try {
				// Prompt, helpful hints.
				System.out.println("Options:   1. Manage Playlist    2. Search    3. Sorting    4. logout    5. Quit\n");

				// Command-Prompt looking prompt
				System.out.print(user + ": ");
				// Get selected Option
				temp = in.readLine();
				choice = Integer.parseInt(temp);

				System.out.println(); // Skip line for formating

				if (choice == 1) {
					// Manage Playlist, create playlist, add or remove media from playlist.
					managePlaylistInterface();

				} else if (choice == 2) {
					// Search media in all playlists, althought the method can actually handle single playlist.
					searchInterface();

				} else if (choice == 3) {
					// Sort all media, method can infact sort single playlist
					sortInterface();

				} else if (choice == 4) {
					// User logout option
					System.out.println("You have logged out.");
					user = null;
					loop = false;

				} else if (choice == 5) {
					// Quit program option
					System.out.println("This application will now quit!");
					user = null;
					loop = false;
					quit = true;

				} else {
					System.out.println("The choice that you have specified is invalid.");
				}

			} catch (IOException e) {
				System.out.println("IO error @ menu.");
			} catch (NumberFormatException e) {// When what is being entered is not an integer.
				System.out.println("The value \"" + temp + "\" you have entered is not a valid integer.");
			}

			System.out.println(); // formating
		}
	}

	private void managePlaylistInterface() {
		// Manage user's playlists
		// Create playlist, add/delete media objects

		// Prompt
		System.out.println("Manage Playlist:   1. Create New Playlist   2. Edit Existing Playlist");

		int choice = 0;
		try {
			choice = Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Not an integer.");
		} catch (IOException e) {
			System.out.println("IOException @ managePlaylistInterface");
		}

		if (choice == 1) { // Creating new playlist
			createPlaylist();

		} else if (choice == 2) { // Modifying existing playlist
			editPlaylist();

		} else {
			System.out.println("Invalid choice.");

		}
	}

	private void createPlaylist() {
		try {
			System.out.print("Name of new playlist: ");
			String newPlaylistName = in.readLine();

			// Check if the playlist name is already taken. If not, create the playlist.
			boolean nameAvailable = true;

			for (int i = 0; i < playlists.length; i++) {
				if (newPlaylistName.equalsIgnoreCase(playlists[i].getName())) {
					nameAvailable = false;
				}
			}

			if (nameAvailable) {
				Playlist additional = new Playlist(newPlaylistName, null);
				System.out.println("You are creating a new playlist with the name \"" + newPlaylistName
						+ "\"\nAre you sure you want to add it to your collection? (y/n)");

				String confirmation = in.readLine();
				if (confirmation.charAt(0) == 'y') {

					// copy original playlists array to another array and append an extra field
					Playlist[] newPlaylists = new Playlist[playlists.length + 1];

					for (int i = 0; i < playlists.length; i++) {
						newPlaylists[i] = playlists[i];
					}

					// append the new playlist to playlist array.
					newPlaylists[playlists.length] = additional;

					playlists = newPlaylists;
					FileManager.addPlaylist(user, newPlaylistName); // Write a method in playlist manager..
					System.out.println("Playlist \"" + newPlaylistName + "\" has been created");

				} else {
					System.out.println("You have cancelled the operation");
				}

			} else {
				System.out.println("Sorry, you already have a playlist named \"" + newPlaylistName + "\"");
			}

		} catch (IOException e) { // one big catch since this exception is rare..
			System.out.println("IOException @ createPlaylist");
		}
	}

	private void editPlaylist() {
		System.out.println("Please select a playlist to modify (listed below):");

		Playlist selected = null; // The playlist that will be modified

		// Show all the playlist the current user have
		PlaylistManager.display(playlists);

		try {
			System.out.print("Select: ");
			String selectedPlaylistName = in.readLine();

			// Get the selected playlist
			for (int i = 0; i < playlists.length; i++) {
				if (selectedPlaylistName.equalsIgnoreCase(playlists[i].getName())) {
					selected = playlists[i];
				}
			}

			if (selected != null) { // When found selected playlist

				System.out.println("The selected playlist contains the following");
				// Display what the playlist contains
				PlaylistManager.display(selected);

				System.out.println("What do you want to do with this playlist?   1.Add Media   2.Remove Media");
				try {
					int choice = Integer.parseInt(in.readLine());

					// Information needed to create a music/video object
					String title, genre;
					String[] secondarySpecs = new String[2]; // used to quickly store the secondary specs

					if (choice == 1) { // add media
						System.out.println("Adding Media. Please provide the following information");
						System.out.print("Music or Video?   1. Music  2. Video");
						choice = Integer.parseInt(in.readLine());

						if (choice == 1 || choice == 2) {

							// The general information which both music and video have
							System.out.print("Title: ");
							title = in.readLine();
							System.out.print("Genre: ");
							genre = in.readLine();

							// Checking secondary specs
							if (choice == 1) { // Music object
								System.out.print("Artist: ");
								secondarySpecs[0] = in.readLine();
								System.out.print("Album: ");
								secondarySpecs[1] = in.readLine();

								Media newMusic = new Music(Media.total + 1, title, genre, secondarySpecs[0], secondarySpecs[1]);

								selected.addMedia(newMusic);// add locally
								// add newMusic into selected playlist for the current user
								FileManager.add(newMusic, selected, user); // TODO: Merge these methods

								System.out.println("added to playlist!");

							} else if (choice == 2) { // Video object
								System.out.print("Duration(# of minutes): ");
								secondarySpecs[0] = in.readLine(); // Check if its double below..
								System.out.print("Rating: ");
								secondarySpecs[1] = in.readLine();

								try {
									Media newVideo = new Video(Media.total + 1, title, genre,
											Double.parseDouble(secondarySpecs[0]), secondarySpecs[1]);

									selected.addMedia(newVideo); // add locally
									// add newVideo into selected playlist for the current user
									FileManager.add(newVideo, selected, user);

									System.out.println("Added to playlist");

								} catch (NumberFormatException e) {
									System.out
											.println("The information you have provide is invalid.\nDuration field is numbers only.");
								}
							}

						} else {
							System.out.println("Invalid choice!");
						}

					} else if (choice == 2) { // removing media
						System.out.println("Removing Media. Please provide the following information");
						System.out.print("Music or Video?   1. Music  2. Video");
						choice = Integer.parseInt(in.readLine());

						if (choice == 1 || choice == 2) {

							// The general information which both music and video have
							System.out.print("Title: ");
							title = in.readLine();
							System.out.print("Genre: ");
							genre = in.readLine();

							// Checking secondary specs
							if (choice == 1) { // Music object
								System.out.print("Artist: ");
								secondarySpecs[0] = in.readLine();
								System.out.print("Album: ");
								secondarySpecs[1] = in.readLine();

								selected.removeMedia(new Music(-1, title, genre, secondarySpecs[0], secondarySpecs[1]), user);
								System.out.println("Media removed from playlist");

							} else if (choice == 2) {
								System.out.print("Duration(# of minutes): ");
								secondarySpecs[0] = in.readLine();
								System.out.print("Ratings: ");
								secondarySpecs[1] = in.readLine();

								System.out.print("Deleting from playlist");

								try {
									selected.removeMedia(new Video(-1, title, genre, Double.parseDouble(secondarySpecs[0]),
											secondarySpecs[1]), user);
									System.out.println("Media removed from playlist");

								} catch (NumberFormatException e) {
									System.out
											.println("The information you have provide is invalid.\nDuration field is numbers only.");
								}

							}
						}
					}

				} catch (NumberFormatException e) {
					System.out.println("Invalid Integer");
				}

			} else {
				System.out.println("The playlist you have specified do not exist.");
			}

		} catch (IOException e) {
			System.out.println("IOException @ editPlaylist");
		}
	}

	private void searchInterface() {
		try {
			System.out.println("Search all Media, input \".\" to ignore the field.");

			// Search prompt
			// General data
			System.out.print("ID: ");
			String id = in.readLine(); // take in as String but parse later.
			if (id.equalsIgnoreCase(".")) {
				id = null;
			}
			System.out.print("Title: ");
			String title = in.readLine();
			if (title.equalsIgnoreCase(".")) {
				title = null;
			}
			System.out.print("Genre: ");
			String genre = in.readLine();
			if (genre.equalsIgnoreCase(".")) {
				genre = null;
			}

			// Secondary data
			System.out.print("Media type (Music/Video/Null) : ");
			String type = in.readLine();
			if (!type.equalsIgnoreCase("Music") && !type.equalsIgnoreCase("Video")) {
				type = null;
			}

			String spec1 = null;
			String spec2 = null;

			if (type != null) {
				if (type.equalsIgnoreCase("Music")) {

					System.out.print("Artist: ");
					spec1 = in.readLine();
					if (spec1.equalsIgnoreCase(".")) {
						spec1 = null;
					}
					System.out.print("Album: ");
					spec2 = in.readLine();
					if (spec2.equalsIgnoreCase(".")) {
						spec2 = null;
					}

				} else if (type.equalsIgnoreCase("Video")) {

					System.out.print("Duration: ");
					spec1 = in.readLine();
					if (spec1.equalsIgnoreCase(".")) {
						spec1 = null;
					}
					System.out.print("Rating: ");
					spec2 = in.readLine();
					if (spec2.equalsIgnoreCase(".")) {
						spec2 = null;
					}
				}
			}

			String[] req = { type, id, title, genre, spec1, spec2 };

			System.out.println("The results are: ");
			Playlist result = new Playlist("search result", PlaylistManager.search(playlists, req));
			PlaylistManager.display(result);
		} catch (IOException e) {
			System.out.println("IOException @ searchInterface");
		}
	}

	private void sortInterface() {
		System.out.println("Sort all Media");
		System.out.println("How do you want your playlist to sort by?");
		System.out.println("1. Title     2. Genre     3. Artist     4. Album     5.Duration     6.Rating");
		int choice = 0;
		try {
			choice = Integer.parseInt(in.readLine());

			if (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5 || choice == 6) {

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

			} else {
				System.out.println("Invalid choice!");
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Not an integer!");
		} catch (IOException e) {
			System.out.println("IOException @ sortInterface");
		}
	}
	
}