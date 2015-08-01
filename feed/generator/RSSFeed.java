package jpodfeed.feed.generator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

class RSSFeed {
    private RSSHeader header;
    private List<RSSEntry> entries;

    public RSSHeader getHeader() { return header; }

    public void setHeader(RSSHeader header) { this.header = header; }

    public List<RSSEntry> getEntries() { return entries; }

    public void setEntries(List<RSSEntry> entries) { this.entries = entries; }

    public static String formatDate(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        return sdf.format(cal.getTime());
    }
}
