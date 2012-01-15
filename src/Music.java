/*
Class Name: Music.java
Author: Sunny Li, Leo Liu
Date: Jan 11, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer, TDSB
IDE used: Eclipse, JGrasp
Purpose: Template for the Music object, which extends Media
 */

class Music extends Media {
	private String artist;
	private String album;

	public Music(int id, String title, String genre, String artist, String album) {
		super(id, title, genre);
		this.artist = artist;
		this.album = album;
	}

	boolean equals(Media other) {
		// Note: Media object may not contain all parameters such as when a Media is created for use with search.
		// When searching, the other Media object should always be the Media that is being searched for.
		if (other.id > 0){
			if (!(this.id == other.id)){
				return false;
			}
		}
		if (!(other.title == null)) { //if one exists
			if (!this.title.equalsIgnoreCase(other.title)) { //does not equals
				return false;
			}
		}
		if (!(other.genre == null)) {
			if (!this.genre.equalsIgnoreCase(other.genre)) {
				return false;
			}
		}
		if (!(((Music) other).artist == null)) {
			if (!this.artist.equalsIgnoreCase(((Music) other).artist)) {
				return false;
			}
		}
		if (!(((Music) other).album == null)) {
			if (!this.album.equalsIgnoreCase(((Music) other).album)) {
				return false;
			}
		}

		return true;
	}
	
	//Compare Methods
	int compareArtist(Music other){
		return this.artist.compareToIgnoreCase(other.artist);
	}
	
	int compareAlbum(Music other){
		return this.artist.compareToIgnoreCase(other.artist);
	}

	// GETTERS
	String getArtist() {
		return artist;
	}

	String getAlbum() {
		return album;
	}
}
