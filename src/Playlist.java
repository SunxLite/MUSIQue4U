/*
Class Name: Playlist.java
Author: Sunny Li, Leo Liu
Date: Dec 31, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer, TDSB
IDE used: Eclipse, JGrasp
Purpose: The playlist object.
		-Each playlist requires an unique name
		-The playlist contains strands of media objects which can be added, remove or modify.
 */

class Playlist {

	// Parameters
	// No Unique ID for playlist, select it by name
	private String name;
	private Media[] media;

	// Initializer
	public Playlist() {
		//for temporary Media arrays
	}
	
	public Playlist(String name, Media[] media) {
		this.name = name;
		this.media = media;
	}

	// Methods
	void addMedia(Media item) {
		//TODO: Check if the object is null
		// This method will append a data to the list by creating a new list and overwriting it
		Media[] temp = new Media[media.length + 1];

		// copy data to new list
		for (int i = 0; i < media.length; i++) {
			temp[i] = media[i];
		}
		temp[media.length] = item; // append the provided media into the playlist

		// overwrite
		media = temp;
	}

	void removeMedia(Media item) {
		// the Media item here is a temporary object that contains the same parameter of items that needs to be removed.
		// can be used to mass remove Media with the same title, album, artist, etc..

		// remove matching item(s)...
		int removed = 0;
		for (int i = 0; i < media.length; i++) {
			if (media[i].equals(item)) {
				media[i] = null;
				removed++;
			}
		}

		// copy data to new array
		Media[] temp = new Media[media.length - removed];
		int index = 0;
		for (int i = 0; i < media.length; i++) {
			if (media[i] != null) {
				temp[index] = media[i];
				index++;
			}
		}

		// overwrite
		media = temp;
	}

	void editMedia(Media initial, Media replacement) { // This one makes use of Media's unique id
		// replace one for another..
		boolean found = false;
		for (int i = 0; i < media.length && !found; i++) {
			if (media[i].equals(initial)) { // Let the loop finish since this program allows duplicated media
				media[i] = replacement;
			}
		}
	}

	// Getter
	Media[] getList() {
		return media; // The Media's within the playlist
	}
	
	String getName(){
		return name;
	}

	// Object Name
	public String toString() { //same as getName but doesn't work when comparing
		return name; // Playlist name
	}

	// Check if the playlist matches by checking its unique name
	public boolean equals(Playlist other) {
		if (name.equals(other.name)) {
			return true;
		} else {
			return false;
		}
	}

}