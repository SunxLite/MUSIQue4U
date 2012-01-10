/*
Class Name: Media.java
Author: Sunny Li, Leo Liu
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Notepad++
Purpose: Template for the Media object, which is abstract
		 and inherits either Music and Video object.
*/

class Media{
	private int id;
	private String title;
	private String genre;
	private static int total; //this can keep track of the total number of Media object initialized
	
	public Media(int id, String title, String genere){
		//...
	}
	
	public boolean equals (Media another){
		//..
	}
	
	public String toString(){
		return "ID: "+id" | Title: "+title+" | Genre: "+genre;
	}
}