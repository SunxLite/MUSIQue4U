/*
Class Name: Music.java
Author: Sunny Li, Leo Liu
Date: Jan 11, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Eclipse
Purpose: Template for the Video object, which extends Media
 */

public class Video extends Media {
	private double duration;
	private String rating;

	public Video(int id, String title, String genre, double duration, String rating) {
		super(id, title, genre);
		this.duration = duration;
		this.rating = rating;
	}

}
