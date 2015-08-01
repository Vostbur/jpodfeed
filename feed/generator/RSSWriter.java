package jpodfeed.feed.generator;

import java.io.FileOutputStream;
import java.util.*;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;

public class RSSWriter {
    private static String XML_BLOCK = "\n";
    private static String XML_INDENT = "\t";

    public static void write(RSSFeed rssfeed, String xmlfile) throws Exception {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLEventWriter writer = output.createXMLEventWriter
                (new FileOutputStream(xmlfile));
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent endSection = eventFactory.createDTD(XML_BLOCK);

        StartDocument startDocument = eventFactory.createStartDocument("UTF-8", "1.0");
        writer.add(startDocument);
        writer.add(endSection);
        StartElement rssStart = eventFactory.createStartElement("", "", "rss");
        writer.add(rssStart);
        writer.add(eventFactory.createAttribute("version", "2.0"));
        writer.add(endSection);

        writer.add(eventFactory.createStartElement("", "", "channel"));
        writer.add(endSection);

        RSSHeader header = rssfeed.getHeader();
        createNode(writer, "title", header.getTitle());
        createNode(writer, "link", header.getLink());
        createNode(writer, "description", header.getDescription());
        createNode(writer, "language", header.getLanguage());
        createNode(writer, "copyright", header.getCopyright());
        createNode(writer, "pubDate", header.getPubDate());
        for (RSSEntry entry : rssfeed.getEntries()) {
            writer.add(eventFactory.createStartElement("", "", "item"));
            writer.add(endSection);
            createNode(writer, "title", entry.getTitle());
            createNode(writer, "description", entry.getDescription());
            createNode(writer, "link", entry.getLink());
            createNode(writer, "guid", entry.getGuid());
            createNode(writer, "pubDate", entry.getPubDate());
            createEnclosure(writer, "enclosure", entry.getEnclosure());
            writer.add(eventFactory.createEndElement("", "", "item"));
            writer.add(endSection);
        }

        writer.add(endSection);
        writer.add(eventFactory.createEndElement("", "", "channel"));
        writer.add(endSection);
        writer.add(eventFactory.createEndElement("", "", "rss"));

        writer.add(endSection);
        writer.add(eventFactory.createEndDocument());
        writer.close();
    }

    private static void createNode
            (XMLEventWriter eventWriter, String name, String value)
            throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent endSection = eventFactory.createDTD(XML_BLOCK);
        XMLEvent tabSection = eventFactory.createDTD(XML_INDENT);

        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tabSection);
        eventWriter.add(sElement);

        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(endSection);
    }

    public static void createEnclosure
        (XMLEventWriter eventWriter, String name, RSSEnclosure value)
        throws XMLStreamException {
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent endSection = eventFactory.createDTD(XML_BLOCK);
            XMLEvent tabSection = eventFactory.createDTD(XML_INDENT);


            // List attributeList = Arrays.asList(... ... ...);
            List <Attribute> attributeList = new LinkedList<>();
            attributeList.add(eventFactory.createAttribute("url", value.getUrl()));
            attributeList.add(eventFactory.createAttribute("length", value.getLength()));
            attributeList.add(eventFactory.createAttribute("type", value.getType()));
            List nsList = Collections.emptyList();

        StartElement sElement = eventFactory.createStartElement("", "", name,
                attributeList.iterator(), nsList.iterator());
            eventWriter.add(tabSection);
            eventWriter.add(sElement);

            EndElement eElement = eventFactory.createEndElement("", "", name);
            eventWriter.add(eElement);
            eventWriter.add(endSection);
    }
}