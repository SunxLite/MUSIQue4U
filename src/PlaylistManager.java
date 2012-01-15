import java.util.Arrays;

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
		// goes to file
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
			System.out.println(media[i]);
		}
	}

	// get media object from numerous playlist
	// used for local search and sort methods
	private static Media[] getMedia(Playlist[] playlists) {
		Media[] items = null;

		// Add array contained Media into a single Media array.
		for (int i = 0; i < playlists.length; i++) {

			// Get additional data that will be appended to the Media array
			Media[] additional = playlists[i].getList();

			// Make a new list and append its length
			int initialLength = items.length;
			int additionalLength = additional.length;
			Media temp[] = new Media[initialLength + additionalLength];

			// copy the old arrays back onto the new one
			for (int a = 0; a < initialLength; a++) {
				temp[a] = items[a];
			}

			// Append data from additional array
			for (int a = 0; a < additionalLength; a++) {
				temp[a + initialLength] = additional[a];
			}

			// Copy the array back to the original
			items = temp;
		}

		// Pass it back
		return items;
	}

	// =============== *TODO! ===============
	static Media[] search(Playlist[] list, String[] req) {
		System.out.println("Currently unavailable");
		return null;
	}

	static Media[] sort(Playlist[] playlists, String[] req) { // Able to pass in an array of playlist object, if it's only one
																// then pass a single playlist through an array
		// quicksort method
		// Get all Media together from playlists

		// Check whether the user want to sort the playlist or its media by checking the first requirement in req[0]
		// Playlist style sort, Options: Name, Size
		if (req[0].equalsIgnoreCase("Playlist")) {
			// Playlist[] playlists; //this is passed in from parameter so it exist already

			// Check second parameter and sort to see how the playlist should be sorted by
			if (req[1].equalsIgnoreCase("Name")) {

			} else if (req[1].equalsIgnoreCase("Size")) {

			}

			// Meida style sort, Options: Title, Genre, Secondary spec including artist/album or duration/rating
		} else if (req[0].equalsIgnoreCase("Media")) {
			
			// get all the Media object that will be sorted
			Media[] items = getMedia(playlists);

			// Check whether the client want to sort with secondary spec so to cast it down, null when its primary
			if (req[1].equalsIgnoreCase("Music") || req[2].equalsIgnoreCase("Video")) {
				
				// for casting down into its respected objects, and put it into playlist since it has pre-written methods
				// Playlist secondary = null;
				Playlist secondary = new Playlist();
				
				if (req[1].equalsIgnoreCase("Music")) {
					
					for (int i = 0; i < items.length; i++) {
						if (items[i] instanceof Music) {
							secondary.addMedia((Music) items[i]);
						}
					}
					
				} else if (req[1].equalsIgnoreCase("Video")) {
					
					for (int i = 0; i < items.length; i++) {
						if (items[i] instanceof Video) {
							secondary.addMedia((Video) items[i]);
						}
					}
					
				}
				
				// Retrieve the secondary objects
				Playlist[] sec = { secondary };
				items = getMedia(sec);
			}

			// Now all media sort alike..

		}
		return null;
	}

	// Swap methods for quick sort, this only works with quick sort since list[b] is stored into the pivot variable
	private void swap(Playlist[] list, int a, int b) {
		list[b] = list[a];
		list[a] = list[b - 1];
	}

	private void swap(Media[] item, int a, int b) {
		item[b] = item[a];
		item[a] = item[b - 1];
	}
}