package com.example.demo;

import java.util.List;

public class YoutubeApiJson {
    private String kind;
    private String etag;
    private String nextPageToken;
    private String prevPageToken;
    private PageInfo pageInfo;
    private List<YoutubeVideoJson> items;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<YoutubeVideoJson> getItems() {
        return items;
    }

    public void setItems(List<YoutubeVideoJson> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        String youtubeApiJsonString =
                "{kind=" + this.kind +
                        ",\netag=" + this.etag +
                        ",\nnextPageToken=" + this.nextPageToken +
                        ",\nprevPageToekn=" + this.prevPageToken +
                        ",\nPageInfo=" + this.pageInfo.toString();
        for (YoutubeVideoJson item : items) {
            youtubeApiJsonString += "\n" + item.toString();
        }
        youtubeApiJsonString += "}";
        return youtubeApiJsonString;
    }

    public class PageInfo {
        private int totalResults;
        private int resultsPerPage;

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public int getResultsPerPage() {
            return resultsPerPage;
        }

        public void setResultsPerPage(int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
        }
    }
}
