/*
Class Name: PlaylistManger.java
Author: Sunny Li, Leo Liu
Date: Dec 27, 2011
School: A.Y. Jackson SS
Computer used: Sunny's Computer
IDE used: Notepad++
Purpose: A class library to manage media in playlists
*/

class PlaylistManager{
	
	//initialization method
	static Playlist[] initialize(User user){
		return FileManager.loadPlaylist(user);
	}
	
	static void display(Playlist[] playlist){
		System.out.println("You have the following playlist:");
		for (int i = 0; i < playlist.length; i++){
			System.out.println(" + "+playlist[i]);
		}
	}
	
	static void display(Playlist playlist){
		System.out.println("All your media in the playlist "+playlist);
		Media media = playlist.getMedia();
		for (int i = 0; i < playlist.getMedia().length; i++){
		//for (int i = 0; i < media.length; i++){
			System.out.println(media[i]);
		}
	}
	
	static void display(Media[] media){
		System.out.println("Detailed Information:");
		for (int i = 0; i < media.length; i++){
			System.out.println(media[i]);
		}
	}
	
	
	//=============== *TO DO! ===============
	static Media[] search(Playlist list, String[] search){
		return null;
	}
	
	static Media[] sort(Playlist list, String[] style){
		return null;
	}
}