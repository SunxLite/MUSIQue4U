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

import java.util.Scanner;

public class Musique4U {
	// Stores the current signed-in user
	private User user;
	// Stores the current user's playlist data
	private Playlist[] playlists;
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
	void menu() {

		// A Scanner to get user input
		Scanner in = new Scanner(System.in);

		// Variable to check whether to continue looping the prompts.
		boolean loop = true;
		// Store any temporary String inputs
		String temp = null;
		// Stores number choices the user have selected
		int choice;

		// A friendly greeting message
		System.out.println("Hello, " + user + ", what do you want to do today?");

		while (loop) {
			try {
				// Prompt, helpful hints.
				System.out.println("Options:  1. Manage Playlist  2. Search  3. Sorting  4. logout  5. Quit\n");

				// Command-Prompt looking prompt
				System.out.print(user + ": ");

				// Get selected Option
				choice = Integer.parseInt(in.next());
				
				System.out.println(); // Skip line for formating

				if (choice == 1) { // Manage Playlist, create playlist, add or remove media from playlist.
					System.out.println("You have choose to manage your playlist");

					System.out.println("1. Create New Playlist\n2. Edit Existing Playlist");
					choice = Integer.parseInt(in.next());

					if (choice == 1) { // Creating new playlist
						System.out.println("Creating new playlist");
						System.out.println("Name of new playlist: ");
						String newName = in.nextLine().trim();
						// Create a blank playlist, check if the playlist name is alredy taken.
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
								playlists = arr2; // TODO: naming..!
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

									Media[] newMusic = new Media[1];
									newMusic[0] = new Music(Media.total + 1, info[0], info[1], info[2], info[3]);
									selected.addMedia(newMusic[0]); // add locally
									FileManager.add(newMusic, selected, user); // add newMusic into selected playlist which
																				// belongs to user...
									System.out.println("added to playlist!");
								} else if (choice == 2) {
									System.out.print("Duration(# of minutes): ");
									info[2] = in.next(); // Check if its double below..
									System.out.print("Rating: ");
									info[3] = in.next();

									try {
										Media[] newVideo = new Media[1];
										newVideo[0] = new Video(Media.total + 1, info[0], info[1], Double.parseDouble(info[2]),
												info[3]);
										selected.addMedia(newVideo[0]);
										FileManager.add(newVideo, selected, user);
										System.out.println("Added to playlist");
									} catch (NumberFormatException e) {
										System.out.println("The information you have provide is invalid.");
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

									selected.removeMedia(new Music(-1, info[0], info[1], info[2], info[3]), user);
									System.out.println("Media removed from playlist");
								} else if (choice == 2) {
									System.out.print("Duration(# of minutes): ");
									info[2] = in.next();
									System.out.print("Rating: ");
									info[3] = in.next();

									System.out.print("Deleting from playlist");
									try {
										selected.removeMedia(new Video(-1, info[0], info[1], Double.parseDouble(info[2]),
												info[3]), user);
										System.out.println("Media removed from playlist");
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
	
	void managePlaylistInterface(){
		
	}
}