/*
Class Name: Music.java
Author: Sunny Li, Leo Liu
Date: Jan 11, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer, TDSB
IDE used: Eclipse, JGrasp
Purpose: Template for the Video object, which extends Media
 */

class Video extends Media {
	// Double is an object that wraps primitive type double, therefore allowing null values
	private Double duration;
	private String rating;

	public Video(int id, String title, String genre, Double duration, String rating) {
		super(id, title, genre);
		this.duration = duration;
		this.rating = rating;
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
		if (!(duration == null || ((Video) other).duration == null)) {
			if (!(this.duration == (((Video) other).duration))) {
				return false;
			}
		}
		if (!(rating == null || ((Video) other).rating == null)) {
			if (!this.rating.equalsIgnoreCase(((Video) other).rating)) {
				return false;
			}
		}

		return true;
	}

	// Compare Methods
	int compareDuration(Video other) {
		double result = this.duration - other.duration;
		if (result > 0) {
			return 1;
		} else if (result < 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
	int compareRating(Video other){ //Just Alphabetically compare it
		return this.rating.compareToIgnoreCase(other.rating);
	}

	// GETTERS
	double getDuration() {
		return duration;
	}

	String getRating() {
		return rating;
	}
}
