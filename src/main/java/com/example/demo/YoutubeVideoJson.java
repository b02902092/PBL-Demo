package com.example.demo;

public class YoutubeVideoJson {
    public String kind;
    public String etag;
    public String id;

    @Override
    public String toString() {
        return "[kind=" + this.kind +
                "\netag=" + this.etag +
                "\nid=" + this.id + "]";
    }
}
