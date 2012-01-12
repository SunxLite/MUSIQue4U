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

abstract class Media { //====================ABSTRACT CLASS!!!====================
	private int id;
	private String title;
	private String genre;

	public Media(int id, String title, String genre) {
		this.id = id;
		this.title = title;
		this.genre = genre;
	}

	public boolean equals(Media other) {
		if (title.equals(other.title) && genre.equals(other.genre))
			return true;
		return false;
	}

	public String toString() {
		return "ID: " + id + " | Title: " + title + " | Genre: " + genre;
	}
}