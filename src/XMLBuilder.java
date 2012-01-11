/*
Class Name: WriteXMLFile.java
Author: Sunny Li, Leo Liu
Date: Jan 9, 2011
School: A.Y. Jackson SS
Computer used: TDSB
IDE used: Eclipse
Purpose: Document Builder Guideline
 */

import java.io.File; //For creating file

//Java provided classes allowing the processing of XML documents: JAXP
import javax.xml.parsers.*; //for reading
import javax.xml.transform.*; //for outputting
import javax.xml.transform.dom.DOMSource; //document source
import javax.xml.transform.stream.StreamResult; //output stream

//Uses W3C's DOM model - W3C organization manages the XML specification
import org.w3c.dom.*; //for data manipulation

public class XMLBuilder {
	public static void main(String args[]) {
		try {
			// The DocumentBuilder is a parser that parses a specified source into a document object
			// The DocumentBuilder is instantiated from the DocumentBuilderFactory because there are numerous parsers from
			// different packages and the DocumentBuilderFactory scans java settings to choose a specified parser to use.
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
			// Initializing a DOM structured document in memory
			Document doc = docBuilder.newDocument();

			// Creating a root element for the XML file
			Element rootElement = doc.createElement("data");
			// Adding the root element to the document
			doc.appendChild(rootElement);

			// Creating inner element
			Element item1 = doc.createElement("1");
			Element item2 = doc.createElement("2");
			// These elements belong in the root element
			rootElement.appendChild(item1);
			rootElement.appendChild(item2);

			// Creating attribute
			// Test on item2
			Attr attribute = doc.createAttribute("itemz");
			attribute.setValue("2");
			item2.setAttributeNode(attribute);

			// Alternative attribute
			item2.setAttribute("wateva", "nothing interesting here...");

			// Element inside element
			Element inItem1 = doc.createElement("Embeded");
			inItem1.appendChild(doc.createTextNode("Some random text"));
			Element inBoth = doc.createElement("text");
			inBoth.setAttribute("cake", "lies");
			// add it
			item1.appendChild(inBoth);
			item1.appendChild(inItem1);
			item1.appendChild(inBoth);
			item2.appendChild(inBoth);

			// Output content to XML file
			// Transformer takes the in memory document and output its data onto a stream
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			// Input source
			DOMSource src = new DOMSource(doc);
			// The output stream
			StreamResult out = new StreamResult(new File("testing223.xml"));
			// StreamResult out = new StreamResult(System.out);

			// Takes in the source and convert it to a format that works with the stream,
			// and the stream can write the data into a file or print it on screen.
			tf.transform(src, out);

			System.out.println("File Created!");

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