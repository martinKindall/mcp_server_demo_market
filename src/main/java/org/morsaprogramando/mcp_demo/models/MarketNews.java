package org.morsaprogramando.mcp_demo.models;

public record MarketNews(String category, Long datetime, String headline, Long id, String image,
                         String related, String source, String summary, String url) {

    public String headlineSummaryAndUrl() {
        return "headline: " + headline + ".\n Summary: " + summary + ". Reference: " + url;
    }
}
