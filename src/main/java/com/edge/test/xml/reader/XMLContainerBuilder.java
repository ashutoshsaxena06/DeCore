package com.edge.test.xml.reader;

import java.net.URL;
/***
 * 
 * @author Mohamed Abdul Kader
 *
 */

public class XMLContainerBuilder {

	public IntDataContainer buildContainer( String args )
	{
		XMLDataParser parser = new XMLDataParser();
		URL url = getClass().getResource(args);
		IntDataContainer container = parser.parseXML(url.toString());		
		return container;
	}
	

}
