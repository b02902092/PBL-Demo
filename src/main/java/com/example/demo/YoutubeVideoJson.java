package com.example.demo;

public class YoutubeVideoJson {
    private String kind;
    private String etag;
    private String id;

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getId() {
        return id;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[kind=" + this.kind +
                "\netag=" + this.etag +
                "\nid=" + this.id + "]";
    }
}
