/*
Class Name: Media.java
Author: Sunny Li, Leo Liu
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer, TDSB
IDE used: Eclipse, JGrasp
Purpose: Template for the Media object, which is abstract
		 and is inherited by either Music or Video object.
 */

abstract class Media {
	// Hold common data
	private int id;
	protected String title;
	protected String genre;
	protected static int total = 0;

	public Media(int id, String title, String genre) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		total++;
	}

	// GETTERS
	int getTotal() {
		return total;
	}

	String getGenre() {
		return genre;
	}

	String getTitle() {
		return title;
	}

	abstract boolean equals(Media other);

	public String toString() { // This is for subclasses... if ever needed
		return "ID: " + id + " | Title: " + title + " | Genre: " + genre;
	}
}