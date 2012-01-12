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

}
