package org.morsaprogramando.mcp_demo.services;

import org.morsaprogramando.mcp_demo.models.MarketNews;
import org.morsaprogramando.mcp_demo.models.MarketStatusResponse;

public class FinhubService {

    private static final String API_KEY = "<INSERT_API_KEY_HERE>";
    private final HttpService httpService;

    public FinhubService(HttpService httpService) {
        this.httpService = httpService;
    }

    public MarketStatusResponse fetchMarketStatus() {
        String url = "https://finnhub.io/api/v1/stock/market-status?exchange=US&token=" + API_KEY;

        return httpService.getRequest(url, MarketStatusResponse.class);
    }

    public MarketNews[] getMarketGeneralNews() {
        String url = "https://finnhub.io/api/v1/news?category=general&token=" + API_KEY;

        return httpService.getRequest(url, MarketNews[].class);
    }
}
