package API;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Controleur.Coordonnees;

public class HostIPParser extends DefaultHandler{
	private Coordonnees point;
	private String temp;
	private boolean dataOk;

	public boolean isDataOk() {
		if (dataOk){
			System.out.println(point);
		}
		return dataOk;
	}


	public HostIPParser(Coordonnees point) {
		this.point = point;
	}
	
	
    /*
    * When the parser encounters plain text (not XML elements),
    * it calls(this method, which accumulates them in a string buffer
    */
   public void characters(char[] buffer, int start, int length) {
          temp = new String(buffer, start, length);
   }
  

   /*
    * Every time the parser encounters the beginning of a new element,
    * it calls this method, which resets the string buffer
    */ 
   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
          temp = "";

   }


	@Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

	      if (qName.equalsIgnoreCase("gml:coordinates")) {
	    	 String[] coordinates = temp.split(",");
            point.createCoordinate(Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[0]));
            System.out.println("coordinates[1] "  + coordinates[1]  + "coordinates[0]" + coordinates[1]);
            dataOk = true;
	     }
    }

	

}
