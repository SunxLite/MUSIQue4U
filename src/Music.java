/*
Class Name: Music.java
Author: Sunny Li, Leo Liu
Date: Jan 11, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
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

		if (!(title == null || other.title == null)) {
			if (!this.title.equalsIgnoreCase(other.title)) {
				return false;
			}
		}
		if (!(genre == null || other.genre == null)) {
			if (!this.genre.equalsIgnoreCase(other.genre)) {
				return false;
			}
		}
		if (!(artist == null || ((Music) other).artist == null)) {
			if (!this.artist.equalsIgnoreCase(((Music) other).artist)) {
				return false;
			}
		}
		if (!(album == null || ((Music) other).album == null)) {
			if (!this.album.equalsIgnoreCase(((Music) other).album)) {
				return false;
			}
		}

		return true;
	}

}
