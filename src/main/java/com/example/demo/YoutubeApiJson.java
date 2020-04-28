package com.example.demo;


import com.fasterxml.jackson.annotation.JsonProperty;

public class YoutubeApiJson {
    public class PageInfo {
        private int totalResults;
        private int resultsPerPage;

        public int getTotalResults() {
            return totalResults;
        }

        public int getResultsPerPage() {
            return resultsPerPage;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public void setResultsPerPage(int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
        }
    }
    private String kind;
    private String etag;
    private String nextPageToken;
    private String prevPageToken;
    private PageInfo pageInfo;
    @JsonProperty("items")
    private YoutubeVideoJson[] youtubeVideoJsons;

    public void setKind (String kind) {
        this.kind = kind;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public void setYoutubeVideoJsons(YoutubeVideoJson[] youtubeVideoJsons) {
        this.youtubeVideoJsons = youtubeVideoJsons;
    }

    public String getKind() {
        return kind;
    }
    public String getEtag() {
        return etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }
    public YoutubeVideoJson[] getYoutubeVideoJsons() {
        return youtubeVideoJsons;
    }


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
