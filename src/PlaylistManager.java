/*
 Class Name: PlaylistManger
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

	private static Media[] sanitize(Media[] items, String[] spec) {
		// Get Parameters
		String subClass = spec[0]; // Assuming subclass is always specified first, which it should.

		// Filter: Check whether the client wants to sort with secondary spec so to cast it down, null when its primary spec
		// It uses a array instead of a single String because it allows additional class to be added with the same parameter
		if (subClass != null)
		if (subClass.equalsIgnoreCase("Music") || subClass.equalsIgnoreCase("Video")) {

			// for casting down into its respected objects, and put it into playlist since it has pre-written methods
			Playlist valid = new Playlist(); //enpty constructor instead of null value

			if (subClass.equalsIgnoreCase("Music")) {
				for (int i = 0; i < items.length; i++) {
					if (items[i] instanceof Music) { // Use of Abstraction
						valid.addMedia((Music) items[i]);
					}
				}
			} else if (subClass.equalsIgnoreCase("Video")) {
				for (int i = 0; i < items.length; i++) {
					if (items[i] instanceof Video) {
						valid.addMedia((Video) items[i]);
					}
				}
			}

			// Retrieve the secondary objects, there should be a shorter way but I forgot..
			Playlist[] toArr = { valid };
			items = getMedia(toArr);
		}

		return items;
	}

	// ============================== SEARCH ==============================
	static Media[] search(Playlist[] playlists, String[] req) {
		// The require String array req[] follows the order [sub-class, Media id, title, genre, and secondary fields depending on subclass]
		Media[] given = getMedia(playlists);
		given = sanitize(given, req);

		// The three general type
		int id = Integer.parseInt(req[1]);
		String title = req[2];
		String genre = req[3];

		// The item we are looking for and the ones we find
		Media[] find = new Media[2];
		Playlist found = new Playlist();

		//creat media object to search with
		if (req[0].equalsIgnoreCase("Music")) {
			find[0] = new Music(id, title, genre, req[4], req[5]);
		} else if (req[0].equalsIgnoreCase("Video")) {
			find[0] = new Video(id, title, genre, Double.parseDouble(req[4]), req[5]);
		} else {
			find[0] = new Music(id, title, genre, null, null);
			find[1] = new Video(id, title, genre, null, null);
		}

		for (int i = 0; i < given.length; i++) {
			for (int a = 0; a < find.length; a++) {
				if (given[i].equals(find[a])) { // When it matches what the user specified
					found.addMedia(given[i]);
				}
			}
		}

		return found.getList(); //return an array of media that matches search parameters
	}

	// ============================== SORT ==============================
	static Media[] sort(Playlist[] playlists, String[] req) {
		// Media style sort, Options: Title, Genre, Secondary spec including artist/album or duration/rating
		// The require String array req[] follows the order [sub-class, field]
		// Note: When sorting with secondary specs, only those that are an instance of the supported class will be returned

		// get all the Media object that will be sorted
		Media[] items = getMedia(playlists);
		// Make items compliant to its subclass if specified
		items = sanitize(items, req);

		// Now all media sort are alike..
		quickSort(items, 0, items.length, req);
		return items;
	}

	private static void quickSort(Media[] data, int left, int right, String[] style) { // Makes use of recursion
		// An array of styles allow variety
		if (left < left) {
			int x = left;
			int y = right;
			Media pivot = data[right];

			// Check sort style, since there is no eval functions in Java...
			if (style[0] == null && style[1].equalsIgnoreCase("Title")) {
				while (x != y) {
					if (data[x].compareTitle(pivot) < 0) {
						x++;
					} else {
						swap(data, x, y);
						y--;
					}
				}
			} else if (style[0] == null && style[1].equalsIgnoreCase("Genre")) {
				while (x != y) {
					if (data[x].compareGenre(pivot) < 0) {
						x++;
					} else {
						swap(data, x, y);
						y--;
					}
				}
			} else if (style[0].equalsIgnoreCase("Music") && style[1].equalsIgnoreCase("Artist")) {
				// When passing in these secondary values, we are certain that it is an instance of the supported classes
				while (x != y) {
					if (((Music) data[x]).compareArtist((Music) pivot) < 0) {
						x++;
					} else {
						swap(data, x, y);
						y--;
					}
				}
			} else if (style[0].equalsIgnoreCase("Music") && style[1].equalsIgnoreCase("Album")) {
				while (x != y) {
					if (((Music) data[x]).compareAlbum((Music) pivot) < 0) {
						x++;
					} else {
						swap(data, x, y);
						y--;
					}
				}
			} else if (style[0].equalsIgnoreCase("Video") && style[1].equalsIgnoreCase("Duration")) {
				while (x != y) {
					if (((Video) data[x]).compareDuration((Video) pivot) < 0) {
						x++;
					} else {
						swap(data, x, y);
						y--;
					}
				}
			} else if (style[0].equalsIgnoreCase("Video") && style[1].equalsIgnoreCase("Rating")) {
				while (x != y) {
					if (((Video) data[x]).compareRating((Video) pivot) < 0) {
						x++;
					} else {
						swap(data, x, y);
						y--;
					}
				}
			}
			data[x] = pivot; // restore the pivot into its correct position

			// Recursive
			quickSort(data, left, x - 1, style);
			quickSort(data, y + 1, right, style);
		}
	}

	// Swap methods for quick sort, this only works with quick sort since item[b] is stored into the pivot variable
	private static void swap(Media[] item, int a, int b) {
		item[b] = item[a];
		item[a] = item[b - 1];
	}
}