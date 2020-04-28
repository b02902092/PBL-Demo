package com.example.demo;


import com.fasterxml.jackson.annotation.JsonProperty;

public class YoutubeApiJson {
    public class PageInfo {
        public int totalResults;
        public int resultsPerPage;
    }
    public String kind;
    public String etag;
    public String nextPageToken;
    public String prevPageToken;
    public PageInfo pageInfo;
    @JsonProperty("items")
    public YoutubeVideoJson[] youtubeVideoJsons;

    @Override
    public String toString() {
        String youtubeApiJsonString =
                "{kind=" + this.kind +
                ",\netag=" + this.etag +
                ",\nnextPageToken=" + this.nextPageToken +
                ",\nprevPageToekn=" + this.prevPageToken +
                ",\nPageInfo=" + this.pageInfo.toString();
        for (YoutubeVideoJson youtubeVideoJson : youtubeVideoJsons) {
            youtubeApiJsonString += "\n" + youtubeVideoJson.toString();
        }
        youtubeApiJsonString += "}";
        return youtubeApiJsonString;
    }
}
