/*
Class Name: Media.java
Author: Sunny Li, Leo Liu
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: Template for the Media object, which is abstract
		 and is inherited by either Music or Video object.
 */

abstract class Media {
	// Hold common data
	private int id;
	protected String title;
	protected String genre;

	public Media(int id, String title, String genre) {
		this.id = id;
		this.title = title;
		this.genre = genre;
	}

	abstract boolean equals(Media other);

	public String toString() { // This is for subclasses...
		return "ID: " + id + " | Title: " + title + " | Genre: " + genre;
	}
}