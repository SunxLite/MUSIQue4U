/*
Class Name: PlaylistManger.java
Author: Sunny Li, Leo Liu
Date: Dec 27, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: A class library to manage media in playlists
			-it has methods that grabs the user's personal playlist
			-it includes multiple display methods that displays the specific data provided
			-it also contains playlist manipulation methods such as search and sort
 */

class PlaylistManager {

	// get user's playlist
	static Playlist[] initialize(User user) {
		return FileManager.loadPlaylist(user);
	}

	// display the name of everything playlist the user has
	static void display(Playlist[] playlist) {
		System.out.println("You have the following playlist:");
		for (int i = 0; i < playlist.length; i++) {
			System.out.println(" + " + playlist[i]);
		}
	}

	// display what a specified playlist contains
	static void display(Playlist playlist) {
		System.out.println("All your media in the playlist " + playlist);
		Media[] media = playlist.getList();
		display(media); // makes use of the method below
	}

	// display the details of a specified array of media objects
	static void display(Media[] media) {
		System.out.println("Detailed Information:");
		for (int i = 0; i < media.length; i++) {
			System.out.println(media[i]); // what does it print?
		}
	}

	// =============== *TODO! ===============
	static Media[] search(Playlist list, String[] search) {
		System.out.println("Currently unavailable");
		return null;
	}

	static Media[] sort(Playlist list, String[] style) {
		System.out.println("Constructing");
		return null;
	}
}