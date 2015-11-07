package com.master.pam.jaxb.jaxb;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.master.pam.jaxb.generated.XmlSource;

public class XMLImporter {

  public static void main( String[] args ) throws Exception {
    JAXBContext jc = JAXBContext.newInstance( "generated" );

    Unmarshaller unmarshaller = jc.createUnmarshaller();
    InputStream xml = new FileInputStream( "import.xml" );

    JAXBElement< XmlSource > feed = unmarshaller.unmarshal( new StreamSource( xml ), XmlSource.class );
    // feed.getValue().getMarkers()

    System.out.print( "" );

    xml.close();

    // Marshaller marshaller = jc.createMarshaller();
    // marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
    //
    // List< Marker > myMarkers = new ArrayList< Marker >();
    // myMarkers.add( new Marker( "a", 1, 1, new Date() ) );
    // myMarkers.add( new Marker( "b", 1, 1, new Date() ) );
    //
    // Markers m = new Markers();
    // m.setMarkers( myMarkers );
    //
    // marshaller.marshal( m, System.out );
  }
}
