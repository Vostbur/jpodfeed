package com.feed.generator;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRSSWriter {
    private static String RSSFEED = "c:/users/vostb_000/Downloads/feed.xml";

    public static void main(String[] args) {
        System.out.println("Creation RSS Feed (" + RSSFEED + ")");
        RSSFeed feed = new RSSFeed();

        RSSHeader header = new RSSHeader();
        header.setCopyright("Copyright by Real Gagnon");
        header.setTitle("Real's HowTo");
        header.setDescription("Useful code snippets for Java");
        header.setLanguage("en");
        header.setLink("http://www.rgagnon.com");
        header.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));

        feed.setHeader(header);

        ArrayList<RSSEntry> entries = new ArrayList<>();
        RSSEntry entry = new RSSEntry();
        entry.setTitle("The PDF are updated");
        entry.setDescription("Java (756 pages), Powerbuilder (197), Javascript (99) and VBS (32)");
        entry.setGuid("http://64.18.163.122/rgagnon/download/index.htm");
        entry.setLink("http://64.18.163.122/rgagnon/download/index.htm");
        entry.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));
        RSSEnclosure enclosure = new RSSEnclosure();
        enclosure.setUrl("file1.mp3");
        enclosure.setLength("12345");
        enclosure.setType("audio/mpeg");
        entry.setEnclosure(enclosure);
        entries.add(entry);

        entry = new RSSEntry();
        entry.setTitle("Java : Display a TIF");
        entry.setDescription("Using JAI, how to display a TIF file");
        entry.setGuid("http://www.rgagnon.com/javadetails/java-0605.html");
        entry.setLink("http://www.rgagnon.com/javadetails/java-0605.html");
        entry.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));
        enclosure = new RSSEnclosure();
        enclosure.setUrl("file2.mp3");
        enclosure.setLength("8854345");
        enclosure.setType("audio/mpeg");
        entry.setEnclosure(enclosure);
        entries.add(entry);

        feed.setEntries(entries);

        try {
            RSSWriter.write(feed, TestRSSWriter.RSSFEED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
}