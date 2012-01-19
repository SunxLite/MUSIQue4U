/*
Class Name: Media
Author: Sunny Li, Leo Liu
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer, TDSB
IDE used: Eclipse, JGrasp
Purpose: Template for the Media object, which is abstract
		 and is inherited by either Music or Video object.
 */

abstract class Media {
	protected int id;
	protected String title;
	protected String genre;
	protected static int maxID = 0;

	public Media(int id, String title, String genre) {
		this.id = id;
		this.title = title;
		this.genre = genre;
if (id > maxID){
maxID = id;
}
	}

	abstract boolean equals(Media other);

	public String toString() { // This is for subclasses... if ever needed
		return "ID: " + id + " | Title: " + title + " | Genre: " + genre;
	}

	//Compare Methods, inherited by subclasses
	int compareTitle(Media other){
		return this.title.compareToIgnoreCase(other.title);
	}
	
	int compareGenre(Media other){
		return this.genre.compareToIgnoreCase(other.genre);
	}
	
	// GETTERS, inherited
	int getMaxID() {
		return maxID;
	}
	
	int getID(){
		return id;
	}

	String getTitle() {
		return title;
	}
	
	String getGenre() {
		return genre;
	}
}