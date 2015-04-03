package API;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import Controleur.Coordonnees;

public class HostIPDataLoader {

	/**
	 * {@inheritDoc}
	 */
	public boolean getPointData(Coordonnees point) {
		URL urlHostIp;
		try {
			urlHostIp = new URL("http://api.hostip.info/?ip=" + point.getIp());
			URLConnection connection = urlHostIp.openConnection();

			// Create a "parser factory" for creating SAX parsers
			SAXParserFactory spfac = SAXParserFactory.newInstance();

			// Now use the parser factory to create a SAXParser object
			SAXParser sp = spfac.newSAXParser();

			// Create an instance of this class; it defines all the handler
			// methods
			HostIPParser handler = new HostIPParser(point);

			// Finally, tell the parser to parse the input and notify the
			// handler
			sp.parse(connection.getInputStream(), handler);

			return handler.isDataOk();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

}
