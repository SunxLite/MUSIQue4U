/*
Class Name: FileManager
Author: Leo Liu, Sunny Li
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: TDSB, Sunny's Computer, Leo's Computer
IDE used: JGrasp, Eclipse
Purpose: The FileManager class provides a library of methods to handle file input/output.
 */

import java.io.File; //For creating file
import java.io.FileNotFoundException; //File does not exist
import java.io.IOException; //Input/Output error
import java.util.ArrayList; //Used to temporally hold an unknown array of data

//Java bundled classes for XML processing
//JAXP: Java API for XML Processing
import javax.xml.parsers.*; //for reading
import javax.xml.transform.*; //for outputting
import javax.xml.transform.dom.DOMSource; //document source
import javax.xml.transform.stream.StreamResult; //output stream

import org.xml.sax.SAXException; //SAX is partially used by JAXP to read data

//W3C's DOM model - W3C manages the XML specification
import org.w3c.dom.*; //for data manipulation

public class FileManager {

	// JAXP XML Parsers - Needed for almost every method
	private static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder docBuilder = null; // Has to initialize inside method to catch ParserConfigurationException
	// Source File
	private static String root = "../"; // empty string for Eclipse IDE, "../" for JGrasp
	private static File usrFile = new File(root + "data/User.xml"); //location of the user data file
	private static Document doc = null; // requires catching IOException

	// Check if the user data file contains a specified user
	static boolean userExist(String username) {

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

		// doc.getDocumentElement().normalize(); // remove empty text fields
		NodeList nList = doc.getElementsByTagName("user");

		for (int index = 0; index < nList.getLength(); index++) {
			Node currentNode = nList.item(index);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currentNode;
				if (getTagValue("username", eElement).equalsIgnoreCase(username)) {
					return true; // Username found
				}
			}

		}
		return false; // Username Not found
	}

	// Check if the log-in information provided is valid
	static boolean checkPass(String username, String password) {

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

		// doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("user");

		for (int index = 0; index < nList.getLength(); index++) {
			Node currentNode = nList.item(index);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currentNode;

				if (getTagValue("username", eElement).equalsIgnoreCase(username)) {
					if (getTagValue("pass", eElement).equals(password)) {
						return true; // data matches, valid.
					}
				}

			}
		}
		return false; // User not found or data does not match, invalid.
	}

	// load the specified user information, create the User object, and return it.
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

		// doc.getDocumentElement().normalize();
		// creates a nodelist containing all of the childnodes under the tag name of "user"
		NodeList nList = doc.getElementsByTagName("user");

		Element eElement = null;
		boolean found = false;

		// goes through all of the nodes in the nodelist for a matching "username"
		for (int i = 0; i < nList.getLength() && !found; i++) {
			Node currentNode = nList.item(i);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				eElement = (Element) currentNode;

				if (getTagValue("username", eElement).equalsIgnoreCase(username)) {// comparing username
					found = true;
				}

			}
		}

		if (found) {
			return new User(Integer.parseInt(getTagValue("id", eElement).trim()), getTagValue("username", eElement),
					getTagValue("pass", eElement), getTagValue("name", eElement), Integer.parseInt(getTagValue("age", eElement)
							.trim()));

		} else { // User not found.
			return null;
		}
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

			// Additionally create a default user media storage.
			// Location: data/user/(#uid)/0.xml
			// new user has been created, so get lastID provides the new user ID.
			initializePlaylist(getLastID());

			return true; // TODO this is not safe...

		}
	}

	static Playlist[] addPlaylist(User selected, String playlistName) {
		int playlistChecker = 0;
		boolean end = false;
		File listLoc = null;

		while (!end) {
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.newDocument();

				// requires user directory to be present, build one just in case
				new File(root + "data/user/" + selected.getID()).mkdir();

				listLoc = new File(root + "data/user/" + selected.getID() + "/" + playlistChecker + ".xml");

				Document testDoc = docBuilder.parse(listLoc);
				//checks for the existence of the file, if the file does not exist, this line of code will be caught by FileNotFoundException
				NodeList testExistence = testDoc.getElementsByTagName("Playlist");

				playlistChecker++;
			} catch (FileNotFoundException e) {
				end = true; // if the file does not exist anymore, it stops the while loop
				Element playList = doc.createElement("Playlist"); // adds the basic layout of a playlist data file containing nothing but the name of the playlist
				playList.setAttribute("name", playlistName);
				doc.appendChild(playList);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			} catch (SAXException e) {
				e.printStackTrace();

			}

		}
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult out = new StreamResult(listLoc);
			// make the output look neat, or make it super hard to see by not setting indent
			transformer.setOutputProperty("indent", "yes");
			transformer.transform(source, out);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();

		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();

		} catch (TransformerException e) {
System.out.println("You may not have enough disk space to store your data.");
			e.printStackTrace();

		}
		return loadPlaylist(selected);
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
						if (eElement.getAttribute("type").equalsIgnoreCase("music")) { // obtains information from data file if it's a
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
							double duration = Double.parseDouble(getTagValue("duration", eElement));
							String rating = getTagValue("rating", eElement);
							newMedia = new Video(id, title, genre, duration, rating);

						}
						selection[temp] = newMedia;

					}

				}
				Element playlistName = (Element) doc.getDocumentElement();
				currentPlaylist = new Playlist(playlistName.getAttribute("name"), selection);
				tracking.add(currentPlaylist);
				playlistChecker++;

			} catch (FileNotFoundException e) {
				end = true; // if the file does not exist anymore, it stops the while loop

			} catch (NullPointerException e) {
				e.printStackTrace();
				System.out.println("Null Pointer @ initializer");

			} catch (ParserConfigurationException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			} catch (SAXException e) {
				e.printStackTrace();

			}
		}

		Playlist[] chosen = tracking.toArray(new Playlist[tracking.size()]); // Convert list back to array
		return chosen;

	}

	// add a media data to playlist
	static void add(Media adding, Playlist selected, User user) {
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
					if (adding instanceof Music) { // checks if the object is a Music or a Video object
						Element media = doc.createElement("Media"); // if Music, adds the following information into the
																	// data file
						media.setAttribute("type", "Music");
						data.appendChild(media);

						Element id = doc.createElement("id");
						id.appendChild(doc.createTextNode(getLastMediaID(adding) + ""));
						media.appendChild(id);

						Element title = doc.createElement("title");
						title.appendChild(doc.createTextNode(adding.getTitle()));
						media.appendChild(title);

						Element genre = doc.createElement("genre");
						genre.appendChild(doc.createTextNode(adding.getGenre()));
						media.appendChild(genre);

						Element artist = doc.createElement("artist");
						artist.appendChild(doc.createTextNode(((Music) adding).getArtist()));
						media.appendChild(artist);

						Element album = doc.createElement("album");
						album.appendChild(doc.createTextNode(((Music) adding).getAlbum() + ""));
						media.appendChild(album);

					} else { // if Video, adds the following information to the data file.
						Element media = doc.createElement("Media");
						media.setAttribute("type", "Video");
						data.appendChild(media);

						Element id = doc.createElement("id");
						id.appendChild(doc.createTextNode(getLastMediaID(adding) + ""));
						media.appendChild(id);

						Element title = doc.createElement("title");
						title.appendChild(doc.createTextNode(adding.getTitle()));
						media.appendChild(title);

						Element genre = doc.createElement("genre");
						genre.appendChild(doc.createTextNode(adding.getGenre()));
						media.appendChild(genre);

						Element duration = doc.createElement("duration");
						duration.appendChild(doc.createTextNode(((Video) adding).getDuration() + ""));
						media.appendChild(duration);

						Element rating = doc.createElement("rating");
						rating.appendChild(doc.createTextNode(((Video) adding).getRating()));
						media.appendChild(rating);

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

	static void del(Media deling, Playlist selected, User user) {
		Playlist[] matchingPlaylists = loadPlaylist(user); // loads the matching playlists of the user in the perimeter
		boolean found = false;

		// compares and finds the right playlist under all of the user's playlists
		for (int i = 0; i < matchingPlaylists.length && !found; i++) {
			if (matchingPlaylists[i].equals(selected)) {
				found = true;
				try {
					docBuilder = docBuilderFactory.newDocumentBuilder();

					// access the corresponding user data file
					File listLoc = new File(root + "data/user/" + user.getID() + "/" + i + ".xml");
					doc = docBuilder.parse(listLoc);

					Node rootNode = doc.getFirstChild();
					// creates a node list that contains all childnodes under the tag name of "Media"
					NodeList mediaList = doc.getElementsByTagName("Media");
					for (int y = 0; y < mediaList.getLength(); y++) {
						// creates an element of each of the nodes
						Element currentElement = (Element) mediaList.item(y);

						// compares the ID of Media element with the Media object in the perimeter
						if (getTagValue("id", currentElement).equals(deling.getID() + "")) {
							// removes all following childnodes under that Media element
							rootNode.removeChild(mediaList.item(y));
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

	public static void edit(Media original, Media edited, Playlist selected, User user) {
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
			// This assumes that the last user in the data file should have the last id... it should.
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

		// Since its from the database file, we assume that the value is correct.
		return Integer.parseInt(returningID.trim());

	}

	// A custom class to quickly get the last Media Id number
	private static int getLastMediaID(Media reference) {
		return reference.getMaxID();
	}

	// A quick method to create a single playlist...
	private static void initializePlaylist(int userID) {
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			// Initializing a DOM structured document in memory
			Document doc = docBuilder.newDocument();

			// Creating a root element for the XML file
			Element rootElement = doc.createElement("Playlist");
			// Give it an attribute
			rootElement.setAttribute("name", "default");
			// Adding the root element to the document
			doc.appendChild(rootElement);

			// Output content to XML file
			// Transformer takes the in memory document and output its data onto a stream
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			// Input source
			DOMSource src = new DOMSource(doc);
			// The output stream
			StreamResult out = new StreamResult(new File(root + "data/user/" + userID + "/0.xml"));

			// Create the user directory to store playlists
			new File(root + "data/user/" + userID).mkdirs();

			// Takes in the source and convert it to a format that works with the stream,
			// and the stream can write the data into a file or print it on screen.
			tf.transform(src, out);

		} catch (ParserConfigurationException e) {
			System.out.println("Misconfigured Parser!");
			// The machine may not have the required java classes but this is unlikely as
			// Java comes bundled with some XML handling classes by default.
		} catch (TransformerException e) {
			e.printStackTrace();
			System.out.println("Unable to print data to stream!");
			// In this case, probably the directory does not exist.
		}
	}
}