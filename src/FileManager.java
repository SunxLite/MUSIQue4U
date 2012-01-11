/*
Class Name: FileManager.java
Author: Leo Liu, Sunny Li
Date: Jan 10, 2011
School: A.Y. Jackson SS
Computer used: TDSB
IDE used: JGrasp
Purpose: The file manger class provides a library of methods to handle file data.
*/

import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
 
    public class FileManager {
       public static void main(String []args){
         if (checkPass("leo123", "PASSWORD")){
            System.out.println("Exist");
         }
         User test = loadUser("Admin");
         System.out.println(loadUser("Admin"));
         if(addUser(test)){
            System.out.println("A");
         }
      }
   	
   	
       public static boolean userExist(String username) {
         boolean found = false;
         try {
            File fXmlFile = new File("../data/User.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
         
            for (int temp = 0; temp < nList.getLength(); temp++) {
            	//System.out.println( nList.getLength());
               Node currentNode = nList.item(temp);
               if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
               
                  Element eElement = (Element) currentNode;
               //System.out.println(getTagValue("username", eElement));
                  if (getTagValue("username", eElement).equalsIgnoreCase(username)){
                     found = true;
                  }
               }
            }
         } 
             catch (Exception e) {
               e.printStackTrace();
            }
         return found;
      }
      
       public static boolean checkPass(String username, String password){
         boolean exist = false;
         try {
            File fXmlFile = new File("../data/User.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
         
            for (int temp = 0; temp < nList.getLength(); temp++) {
               Node currentNode = nList.item(temp);
               if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
               
                  Element eElement = (Element) currentNode;
                  if (getTagValue("username", eElement).equalsIgnoreCase(username)){
                     if (getTagValue("pass", eElement).equals(password)){
                        exist = true;
                     }
                  }
               }
            }
         } 
             catch (Exception e) {
               //e.printStackTrace();
               System.out.println(e);
            }
         return exist;
      }
      
       public static User loadUser(String username){
         User chosen = new User();
         try {
            File fXmlFile = new File("../data/User.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            boolean found = false;
            Element eElement = (Element)nList.item(0);
            for (int temp = 0; temp < nList.getLength() && !found; temp++) {
               Node currentNode = nList.item(temp);
               if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                  eElement = (Element) currentNode;
                  if (getTagValue("username", eElement).equalsIgnoreCase(username)){
                     found = true;
                  }
               }
            }
            chosen = new User(Integer.parseInt(getTagValue("id", eElement).trim()), getTagValue("username", eElement), getTagValue("pass", eElement), getTagValue("name", eElement), Integer.parseInt(getTagValue("age", eElement).trim()));
         } 
             catch (Exception e) {
               System.out.println(e);
            }
         return chosen;
      }
   	
       public static boolean addUser(User newUser){
         if(userExist(newUser.getUsername())){
            return false;
         }
         else{
            try {
               File fXmlFile = new File("../data/Usr.xml");
               DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
               DocumentBuilder docBuild = dbf.newDocumentBuilder();
               Document doc = docBuild.parse(fXmlFile);
               doc.getDocumentElement().normalize();
            
               Node currentNode = doc.getFirstChild();
               Element data = (Element)currentNode;
            
               Element user = doc.createElement("user");
               data.appendChild(user);
            
               Element id = doc.createElement("id");
               id.appendChild(doc.createTextNode(newUser.getID()+""));
               user.appendChild(id);
            
               Element username = doc.createElement("username");
               username.appendChild(doc.createTextNode(newUser.getUsername()+""));
               user.appendChild(username);
            
               Element password = doc.createElement("pass");
               password.appendChild(doc.createTextNode(newUser.getPassword()+""));
               user.appendChild(password);
            
               Element name = doc.createElement("name");
               name.appendChild(doc.createTextNode(newUser.getName()+""));
               user.appendChild(name);
            /*
               Element gender = doc.createElement("gender");
               gender.appendChild(doc.createTextNode(newUser.getGender()+""));
               user.appendChild(gender);
            */
               Element age = doc.createElement("age");
               age.appendChild(doc.createTextNode(newUser.getAge()+""));
               user.appendChild(age);
            
               Transformer transformer = TransformerFactory.newInstance().newTransformer();
               DOMSource source = new DOMSource(doc);
               StreamResult output = new StreamResult(new File("../data/Usr.xml"));
               transformer.transform(source,output);
            }
                catch (Exception e) {
                  e.printStackTrace();
               }
            return true;
         }
      }
      
       public static Playlist[] loadPlaylist(User selected){
         Playlist[] chosen;
         try {
            int playlistChecker = 0;
            while(true){
               playlistChecker ++;
               File fXmlFile = new File("../data/" + selected.getID()+ "_" + playlistChecker + ".xml");
               DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
               DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
               Document doc = dBuilder.parse(fXmlFile);
               doc.getDocumentElement().normalize();
               NodeList musics = doc.getElementsByTagName("Music");
               Element eElement = (Element)musics.item(0);
               for (int temp = 0; temp < musics.getLength(); temp++) {
                  Node currentNode = musics.item(temp);
                  if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                     eElement = (Element) currentNode;
                     int id = Integer.parseInt(getTagValue("id", eElement).trim());
                     String title = getTagValue("title", eElement);
                     String genre = getTagValue("genre", eElement);
                     String artist = getTagValue("artist", eElement);
                     String album = getTagValue("album", eElement);
                     Media newMedia = new Media(id, title, genre, artist, album);
                  }
               }
               Nodelist videos = doc.getElementsByTageName("Video");
            }
         } 
             catch (Exception e) {
               System.out.println(e);
            }
         return chosen;
      
      }
   
   
   
   
   
   
   
       private static String getTagValue(String sTag, Element eElement) {
         NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
         Node nValue = (Node) nlList.item(0);
         return nValue.getNodeValue();
      }
   
   }