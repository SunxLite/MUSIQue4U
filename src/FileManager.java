/*
Class Name: FileManager.java
Author: Leo Liu, Sunny Li
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: TDSB
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
	// private static File usrFile = new File("data/User.xml"); // Eclipse
	private static File usrFile = new File("../data/User.xml"); // JGrasp
	private static Document doc = null; // catch IOException in method

	// Testing utility - Skip this method
	public static void main(String[] args) {
		/*
		 * if (checkPass("leo123", "PASSWORD")) { System.out.println("User Exist"); } User test = loadUser("admin"); // Note:
		 * Username only! System.out.println("User: " + test);
		 * 
		 * if (addUser(new User(1, "a", "a", "a", 2))) { System.out.println("User added"); } Playlist[] test1 =
		 * loadPlaylist(test); System.out.println(test1[0]);
		 */
		User test = loadUser("admin");
		Playlist[] testing = loadPlaylist(test);
		for (int i = 0; i < testing.length; i++) {
			for (int x = 0; x < (testing[i].getList()).length; x++) {
				System.out.println(testing[i].getList()[x]);
			}
		}
	}

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

		// doc.getDocumentElement().normalize(); // restore escaped text back to original
		// get the list of user node... all the data inside <uesr> data here </user>
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
		NodeList nList = doc.getElementsByTagName("user");

		Element eElement = null;
		boolean found = false;

		for (int i = 0; i < nList.getLength() && !found; i++) {
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

	static boolean addUser(User newUser) {
		if (userExist(newUser.getUsername())) {
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

			Element user = doc.createElement("user");
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

			return true; // TODO this is not safe...
		}
	}

	static Playlist[] loadPlaylist(User selected) {
		// Temporary use List to hold append the data
		ArrayList<Playlist> tracking = new ArrayList<Playlist>();
		boolean end = false;
		int playlistChecker = 0;
		Playlist currentPlaylist = null;

		while (!end) {
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				File listLoc = new File("../data/user/" + selected.getID() + "/" + playlistChecker + ".xml");
				doc = docBuilder.parse(listLoc);

				doc.getDocumentElement().normalize();
				NodeList mediaList = doc.getElementsByTagName("Media");
				// ==================== CONFUSION ZONE! ====================
				Element eElement;
				Media[] selection = new Media[mediaList.getLength()];
				for (int temp = 0; temp < mediaList.getLength(); temp++) {

					Node currentNode = mediaList.item(temp);
					if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
						Media newMedia;
						eElement = (Element) currentNode;
						if (eElement.getAttribute("type").equals("music")) {
							int id = Integer.parseInt(getTagValue("id", eElement).trim());
							String title = getTagValue("title", eElement);
							String genre = getTagValue("genre", eElement);
							String artist = getTagValue("artist", eElement);
							String album = getTagValue("album", eElement);
							newMedia = new Music(id, title, genre, artist, album);
						} else {
							int id = Integer.parseInt(getTagValue("id", eElement).trim());
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
				end = true; // TEMPORARY... better get a variable that tells how much playlist there is
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

	static void add(Media[] adding, Playlist selected, User user) {
		Playlist[] matchingPlaylists = loadPlaylist(user);
		boolean found = false;
		for (int i = 0; i < matchingPlaylists.length && !found; i++) {
			if (matchingPlaylists[i].equals(selected)) {
				found = true;
				try {
					docBuilder = docBuilderFactory.newDocumentBuilder();
					File listLoc = new File("../data/user/" + user.getID() + "/" + i + ".xml");
					doc = docBuilder.parse(listLoc);

					Node currentNode = doc.getFirstChild(); // get root node
					Element data = (Element) currentNode; // transform into a element
					for (int x = 0; x < adding.length; x++) {
						if (adding[x] instanceof Music) {
							Element media = doc.createElement("Media");
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
						} else {
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

	static void del(Media[] adding, Playlist selected, User user) {

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
			DocumentBuilder documentBuilder = docBuilderFactory.newDocumentBuilder();
			Document docu = docBuilder.parse(usrFile);
			NodeList nList = docu.getElementsByTagName("user");
			Node lastNode = (Node) nList.item((nList.getLength() - 1));
			Element lastElement = (Element) lastNode;
			returningID = getTagValue("id", lastElement);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(returningID.trim());
	}

	// A custom class to quickly get the last Media Id number
	private static int getLastMediaID(Media reference) {
		return reference.getTotal() + 1;
	}
}