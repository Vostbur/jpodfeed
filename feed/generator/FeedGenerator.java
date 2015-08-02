package jpodfeed.feed.generator;

import com.mpatric.mp3agic.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeedGenerator {

    private static String RSSFEED = "c:/users/vostb_000/Downloads/feed.xml";

    public static void main(String[] args) throws IOException {
        String pattern = ".mp3";
        String dir = "C:\\users\\vostb_000\\Downloads";

        System.out.println("Creation RSS Feed (" + RSSFEED + ")");
        RSSFeed feed = new RSSFeed();

        RSSHeader header = new RSSHeader();
        header.setCopyright("Copyright by vostbur");
        header.setTitle("PodFeedGenerator");
        header.setDescription("Generator podcast RSS feed from local files");
        header.setLanguage("en");
        header.setLink("http://github.com/Vostbur/");
        header.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));

        feed.setHeader(header);

        ArrayList<RSSEntry> entries = new ArrayList<>();

        makeFileArray(dir, pattern).forEach((file) -> {
            try {
                entries.add(FeedGenerator.generateEntry(file));
            } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                e.printStackTrace();
            }
        });

        feed.setEntries(entries);

        try {
            RSSWriter.write(feed, FeedGenerator.RSSFEED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }

    public static List<String> makeFileArray(String dir, String pattern) throws IOException {
        List<String> files = new ArrayList<>();
        Path start = FileSystems.getDefault().getPath(dir);
        Files.walk(start)
                .filter(path -> path.toFile().isFile())
                .filter(path -> path.toString().endsWith(pattern))
                .forEach(path -> files.add(path.toString()));
        return files;
    }

    public static RSSEntry generateEntry(String file) throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3file = new Mp3File(file);
        RSSEntry entry = new RSSEntry();
        RSSEnclosure enclosure = new RSSEnclosure();

        entry.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));
        enclosure.setUrl(file);
        enclosure.setLength("" + mp3file.getLength());
        enclosure.setType("audio/mpeg");
        entry.setEnclosure(enclosure);

        if (mp3file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
/*            System.out.println("Track: " + id3v1Tag.getTrack());
            System.out.println("Artist: " + id3v1Tag.getArtist());*/
            entry.setTitle(id3v1Tag.getTitle());
/*            System.out.println("Album: " + id3v1Tag.getAlbum());
            System.out.println("Year: " + id3v1Tag.getYear());
            System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
            System.out.println("Comment: " + id3v1Tag.getComment());*/
        } else if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
/*            System.out.println("Track: " + id3v2Tag.getTrack());
            System.out.println("Artist: " + id3v2Tag.getArtist());*/
            entry.setTitle(id3v2Tag.getTitle());
/*            System.out.println("Album: " + id3v2Tag.getAlbum());
            System.out.println("Year: " + id3v2Tag.getYear());
            System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
            System.out.println("Comment: " + id3v2Tag.getComment());
            System.out.println("Composer: " + id3v2Tag.getComposer());
            System.out.println("Publisher: " + id3v2Tag.getPublisher());
            System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
            System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
            System.out.println("Copyright: " + id3v2Tag.getCopyright());
            System.out.println("URL: " + id3v2Tag.getUrl());
            System.out.println("Encoder: " + id3v2Tag.getEncoder());
            byte[] albumImageData = id3v2Tag.getAlbumImage();
            if (albumImageData != null) {
                System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
            }*/
        }
        return entry;
    }
}
