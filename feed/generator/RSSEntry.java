package jpodfeed.feed.generator;

class RSSEntry {
    private String title = "";
    private String link = "";
    private String description = "";
    private String language = "";
    private String copyright = "";
    private String pubDate = "";
    private String guid = "";
    private RSSEnclosure enclosure = new RSSEnclosure();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public RSSEnclosure getEnclosure() { return enclosure; }

    public void setEnclosure(RSSEnclosure enclosure) { this.enclosure = enclosure; }
}
