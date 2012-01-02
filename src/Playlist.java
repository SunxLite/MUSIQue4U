/*
Class Name: Playlist.java
Author: Sunny Li, Leo Liu
Date: Dec 31, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Notepad++
Purpose: The playlist object.
*/

class Playlist{

	//Parameters
	private String name;
	private Media[] media;
	
	//Initializer
	public Playlist(String name, Media[] media){
		this.name = name;
		this.media = media;
	}
	
	//Methods
	void addMedia(Media item){
		//This method will append a data to the list by creating a new list and overwritting it
		private Media[] temp = new Media[media.length + 1];
		
		//copy data to new list
		for (int i = 0; i < media.length; i++){
			temp[i] = media[i];
		}
		temp[media.length] = item;
		
		//overwrite
		media = temp;
	{
	
	void removeMedia(Media item){
		//this method will remove matching item from the playlist
		//can be used to mass remove album, artist, etc..
		
		//remove matching item(s)...
		private int removed = 0;
		for (int i = 0; i < media.length; i++){
			if (media[i].equals(item)){
				media[i] = null;
				removed++;
			}
		}
		
		//copy data to new array
		private Media[] temp = new Media[media.length - removed];
		private int index = 0;
		for (int i = 0; i < media.length; i++){
			if (media[i] != null){
				temp[index] = media[i];
				index++;
			}
		}
		
		//overwrite
		media = temp;
	}
	
	
	void editMedia(Media initial, Media replacement){
		//replace one for another..
		private boolean found = false;
		for (int i = 0; i < media.length && !found; i++){
			if (media[i].equals(initial)){
				media[i] = replacement;
			}
		}
	}
	
	//Getter
	Media[] getList(){
		return media;
	}
	
	//Object Name
	String toString(){
		return name;
	}
}