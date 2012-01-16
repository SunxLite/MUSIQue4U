/*
Class Name: FileManager.java
Author: Leo Liu, Sunny Li
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: TDSB, Sunny's Computer, Leo's Computer
IDE used: JGrasp, Eclipse
Purpose: The FileManager class provides a library of methods to handle file data.
			-Library contains: userExist, checkPass, loadUser...
			-Code way too repetitive... consider using global variables
 */

import java.io.File; //For creating file
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList; //Used to temporally hold an unknown amount of data

//Java provided classes allowing the processing of XML documents: JAXP
import javax.xml.parsers.*; //for reading
import javax.xml.transform.*; //for outputting
import javax.xml.transform.dom.DOMSource; //document source
import javax.xml.transform.stream.StreamResult; //output stream

//Uses W3C's DOM model - W3C organization manages the XML specification
import org.w3c.dom.*; //for data manipulation
import org.xml.sax.SAXException; //SAX is also used to parse Document...

public class FileManager {

	// JAXP XML Parsers - Needed for almost every method
	private static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder docBuilder = null; // Has to initialize inside method to catch ParserConfigurationException
	// Source File
	private static String root = ""; // empty string for Eclipse IDE, "../" for JGrasp
	private static File usrFile = new File(root + "data/User.xml");
	private static Document doc = null; // catch IOException in method

	// Check if the user data file contains a specified user
	static boolean userExist(String username) {
		boolean exist = false;

		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(usrFile);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (SAXException e) {
			e.printStackTrace();

		}

		doc.getDocumentElement().normalize(); // remove empty text fields
		NodeList nList = doc.getElementsByTagName("user");

		for (int index = 0; index < nList.getLength(); index++) {
			Node currentNode = nList.item(index);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currentNode;
				if (getTagValue("username", eElement).equalsIgnoreCase(username)) {
					exist = true;

				}
			}
		}
		return exist;

	}

	// Check if the log-in information provided is valid
	static boolean checkPass(String username, String password) {
		boolean valid = false;

		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(usrFile);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (SAXException e) {
			e.printStackTrace();

		}

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("user");

		for (int index = 0; index < nList.getLength(); index++) {
			Node currentNode = nList.item(index);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currentNode;
				if (getTagValue("username", eElement).equalsIgnoreCase(username)) {
					if (getTagValue("pass", eElement).equals(password)) {

						valid = true;
					}
				}
			}
		}

		return valid;
	}

	// load the specified user's detail and return it
	static User loadUser(String username) {

		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(usrFile);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (SAXException e) {
			e.printStackTrace();

		}

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("user"); // creates a nodelist containing all of the childnodes under the tag
															// name of "user"

		Element eElement = null;
		boolean found = false;

		for (int i = 0; i < nList.getLength() && !found; i++) { // goes through all of the nodes in the nodelist for a matching
																// "username"
			Node currentNode = nList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				eElement = (Element) currentNode;
				if (getTagValue("username", eElement).equalsIgnoreCase(username)) {
					found = true;

				}
			}
		}

		User chosen = new User(Integer.parseInt(getTagValue("id", eElement).trim()), getTagValue("username", eElement),
				getTagValue("pass", eElement), getTagValue("name", eElement), Integer.parseInt(getTagValue("age", eElement)
						.trim()));

		return chosen;

	}

	// Write a new user into the user data file by appending to it
	static boolean addUser(User newUser) {
		if (userExist(newUser.getUsername())) { // checks if the user already exists, prevents duplication
			return false;
		} else {
			
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(usrFile);

			} catch (ParserConfigurationException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			} catch (SAXException e) {
				e.printStackTrace();

			}

			doc.getDocumentElement().normalize();

			Node currentNode = doc.getFirstChild(); // get root node
			Element data = (Element) currentNode; // transform into a element

			Element user = doc.createElement("user"); // adds information into the xml data file
			data.appendChild(user);

			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode((getLastID() + 1) + ""));
			user.appendChild(id);

			Element username = doc.createElement("username");
			username.appendChild(doc.createTextNode(newUser.getUsername()));
			user.appendChild(username);

			Element password = doc.createElement("pass");
			password.appendChild(doc.createTextNode(newUser.getPassword()));
			user.appendChild(password);

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(newUser.getName()));
			user.appendChild(name);

			Element age = doc.createElement("age");
			age.appendChild(doc.createTextNode(newUser.getAge() + ""));
			user.appendChild(age);

			try {
				Transformer transformer = TransformerFactory.newInstance().newTransformer();

				DOMSource source = new DOMSource(doc);
				StreamResult output = new StreamResult(usrFile);

				// make the output look neat, or make it super hard to see by not setting indent
				transformer.setOutputProperty("indent", "yes");
				transformer.transform(source, output);

			} catch (TransformerConfigurationException e) {
				e.printStackTrace();

			} catch (TransformerFactoryConfigurationError e) {
				e.printStackTrace();

			} catch (TransformerException e) {
				e.printStackTrace();

			}

			//Additionally create a default user media storage.
			newPlaylistXML(new String((getLastID()+1)+"/"+0), "default");
			
			return true; // TODO this is not safe...

		}
	}

	// Get the playlist and pass it back
	static Playlist[] loadPlaylist(User selected) {
		// Temporary use List to hold append the data
		ArrayList<Playlist> tracking = new ArrayList<Playlist>();
		boolean end = false;
		int playlistChecker = 0; // since it is not provided the number of playlists the user has, this variable is used to keep
									// track while accessing the user's playlists
		Playlist currentPlaylist = null;

		while (!end) {

			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				File listLoc = new File(root + "data/user/" + selected.getID() + "/" + playlistChecker + ".xml");
				doc = docBuilder.parse(listLoc);

				doc.getDocumentElement().normalize();
				NodeList mediaList = doc.getElementsByTagName("Media"); // a nodelist that holds all childnodes under the tag
																		// name of Media
				Element eElement;
				Media[] selection = new Media[mediaList.getLength()];
				for (int temp = 0; temp < mediaList.getLength(); temp++) {

					Node currentNode = mediaList.item(temp);
					if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
						Media newMedia;
						eElement = (Element) currentNode;
						if (eElement.getAttribute("type").equals("music")) { // obtains information from data file if it's a
																				// Music type
							int id = Integer.parseInt(getTagValue("id", eElement).trim());
							String title = getTagValue("title", eElement);
							String genre = getTagValue("genre", eElement);
							String artist = getTagValue("artist", eElement);
							String album = getTagValue("album", eElement);
							newMedia = new Music(id, title, genre, artist, album); // creates an instance of Music using the
																					// above information

						} else {
							int id = Integer.parseInt(getTagValue("id", eElement).trim()); // for a Video type
							String title = getTagValue("title", eElement);
							String genre = getTagValue("genre", eElement);
							double duration = Double.parseDouble(getTagValue("duration", eElement).trim());
							String rating = getTagValue("rating", eElement);
							newMedia = new Video(id, title, genre, duration, rating);

						}
						selection[temp] = newMedia;

					}
					Element playlistName = (Element) doc.getDocumentElement();
					currentPlaylist = new Playlist(playlistName.getAttribute("name"), selection);

				}
				tracking.add(currentPlaylist);
				playlistChecker++;

			} catch (FileNotFoundException e) {
				end = true; // if the file does not exist anymore, it stops the while loop

			} catch (ParserConfigurationException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			} catch (SAXException e) {
				e.printStackTrace();

			}
		}

		Playlist[] chosen = tracking.toArray(new Playlist[tracking.size()]);
		return chosen;

	}

	// add a media data to playlist
	static void add(Media[] adding, Playlist selected, User user) {
		Playlist[] matchingPlaylists = loadPlaylist(user);
		boolean found = false;
		for (int i = 0; i < matchingPlaylists.length && !found; i++) {
			if (matchingPlaylists[i].equals(selected)) {
				found = true; // quit for loop

				try {
					docBuilder = docBuilderFactory.newDocumentBuilder();
					File listLoc = new File(root + "data/user/" + user.getID() + "/" + i + ".xml");
					doc = docBuilder.parse(listLoc);

					Node currentNode = doc.getFirstChild(); // get root node
					Element data = (Element) currentNode; // transform into a element
					for (int x = 0; x < adding.length; x++) {
						if (adding[x] instanceof Music) { // checks if the object is a Music or a Video object
							Element media = doc.createElement("Media"); // if Music, adds the following information into the
																		// data file
							media.setAttribute("type", "Music");
							data.appendChild(media);

							Element id = doc.createElement("id");
							id.appendChild(doc.createTextNode(getLastMediaID(adding[x]) + ""));
							media.appendChild(id);

							Element title = doc.createElement("title");
							title.appendChild(doc.createTextNode(adding[x].getTitle()));
							media.appendChild(title);

							Element genre = doc.createElement("genre");
							genre.appendChild(doc.createTextNode(adding[x].getGenre()));
							media.appendChild(genre);

							Element artist = doc.createElement("artist");
							artist.appendChild(doc.createTextNode(((Music) adding[x]).getArtist()));
							media.appendChild(artist);

							Element album = doc.createElement("album");
							album.appendChild(doc.createTextNode(((Music) adding[x]).getAlbum() + ""));
							media.appendChild(album);

						} else { // if Video, adds the following information to the data file.
							Element media = doc.createElement("Media");
							media.setAttribute("type", "Video");
							data.appendChild(media);

							Element id = doc.createElement("id");
							id.appendChild(doc.createTextNode(getLastMediaID(adding[x]) + ""));
							media.appendChild(id);

							Element title = doc.createElement("title");
							title.appendChild(doc.createTextNode(adding[x].getTitle()));
							media.appendChild(title);

							Element genre = doc.createElement("genre");
							genre.appendChild(doc.createTextNode(adding[x].getGenre()));
							media.appendChild(genre);

							Element duration = doc.createElement("duration");
							duration.appendChild(doc.createTextNode(((Video) adding[x]).getDuration() + ""));
							media.appendChild(duration);

							Element rating = doc.createElement("rating");
							rating.appendChild(doc.createTextNode(((Video) adding[x]).getRating()));
							media.appendChild(rating);

						}
						Transformer transformer = TransformerFactory.newInstance().newTransformer();

						DOMSource source = new DOMSource(doc);
						StreamResult output = new StreamResult(listLoc);

						// make the output look neat, or make it super hard to see by not setting indent
						transformer.setOutputProperty("indent", "yes");
						transformer.transform(source, output);

					}
				} catch (ParserConfigurationException e) {
					e.printStackTrace();

				} catch (IOException e) {
					e.printStackTrace();

				} catch (SAXException e) {
					e.printStackTrace();

				} catch (TransformerConfigurationException e) {
					e.printStackTrace();

				} catch (TransformerFactoryConfigurationError e) {
					e.printStackTrace();

				} catch (TransformerException e) {
					e.printStackTrace();

				}
			}
		}
	}

	static void del(Media[] deling, Playlist selected, User user) {
		Playlist[] matchingPlaylists = loadPlaylist(user); // loads the matching playlists of the user in the perimeter
		boolean found = false;
		for (int i = 0; i < matchingPlaylists.length && !found; i++) { // compares and finds the right playlist under all of the
																		// user's playlists
			if (matchingPlaylists[i].equals(selected)) {
				found = true;
				try {
					docBuilder = docBuilderFactory.newDocumentBuilder();
					File listLoc = new File(root + "data/user/" + user.getID() + "/" + i + ".xml"); // access the corresponding
																									// user
																									// data file
					doc = docBuilder.parse(listLoc);

					Node rootNode = doc.getFirstChild();
					NodeList mediaList = doc.getElementsByTagName("Media"); // creates a node list that contains all childnodes
																			// under the tag name of "Media"
					for (int x = 0; x < deling.length; x++) {
						for (int y = 0; y < mediaList.getLength(); y++) {
							Element currentElement = (Element) mediaList.item(y); // creates an element of each of the nodes
							if (getTagValue("id", currentElement).equals(deling[x].getID() + "")) { // compares the ID of Media
																									// element with the Media
																									// object in the perimeter
								rootNode.removeChild(mediaList.item(y)); // removes all following childnodes under that Media
																			// element
							}
						}
					}
					Transformer transformer = TransformerFactory.newInstance().newTransformer();

					DOMSource source = new DOMSource(doc);
					StreamResult output = new StreamResult(listLoc);

					// make the output look neat, or make it super hard to see by not setting indent
					transformer.setOutputProperty("indent", "yes");
					transformer.transform(source, output);

				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				} catch (TransformerFactoryConfigurationError e) {
					e.printStackTrace();
				} catch (TransformerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void edit(Media[] original, Media[] edited, Playlist selected, User user) {
		del(original, selected, user); // makes use of del class by deleting the original Media objects
		add(edited, selected, user); // makes use of add class by adding the edited MEdia objects
	}

	// A custom class to quickly get tag values
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();

	}

	// A custom class to quickly get the last user ID number
	private static int getLastID() {
		String returningID = "";

		try {
			Document docu = docBuilder.parse(usrFile);
			NodeList nList = docu.getElementsByTagName("user");
			// This assumes that the last user in the data file should have the last id... which it should
			Node lastNode = (Node) nList.item((nList.getLength() - 1));
			Element lastElement = (Element) lastNode;
			returningID = getTagValue("id", lastElement);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) { // when there is no user in database..
			return 0;
		}
		return Integer.parseInt(returningID.trim());

	}

	// A custom class to quickly get the last Media Id number
	private static int getLastMediaID(Media reference) {
		return reference.getTotal() + 1;

	}
	
	//A quick method to create a single playlist...
	private static void newPlaylistXML(String fileName, String name){
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			// Initializing a DOM structured document in memory
			Document doc = docBuilder.newDocument();

			// Creating a root element for the XML file
			Element rootElement = doc.createElement("Playlist");
			// Give it an attribute
			rootElement.setAttribute("name", name);
			// Adding the root element to the document
			doc.appendChild(rootElement);

			// Output content to XML file
			// Transformer takes the in memory document and output its data onto a stream
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			// Input source
			DOMSource src = new DOMSource(doc);
			// The output stream
			StreamResult out = new StreamResult(new File(root+"data/user/"+fileName+".xml"));
			// StreamResult out = new StreamResult(System.out);

			// Takes in the source and convert it to a format that works with the stream,
			// and the stream can write the data into a file or print it on screen.
			tf.transform(src, out);
			
		} catch (ParserConfigurationException e) {
			System.out.println("Misconfigured Parser!");
			// The machine may not have the required java classes but this is unlikely as
			// Java comes bundled with some XML handling classes by default.
			// So it is likely that the computer is misconfigured.
		} catch (TransformerException e) {
			System.out.println("Unable to print data");
		}
	}
}