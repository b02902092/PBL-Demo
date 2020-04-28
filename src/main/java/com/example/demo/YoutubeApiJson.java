package com.example.demo;

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
    private YoutubeVideoJson[] items;

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

    public void setItems(YoutubeVideoJson[] items) {
        this.items = items;
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

    public YoutubeVideoJson[] getItems() {
        return items;
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
}
