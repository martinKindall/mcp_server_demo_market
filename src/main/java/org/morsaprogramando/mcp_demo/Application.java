package org.morsaprogramando.mcp_demo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.morsaprogramando.mcp_demo.services.FinhubService;
import org.morsaprogramando.mcp_demo.services.HttpService;
import org.morsaprogramando.mcp_demo.services.MCPServer;

public class Application {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        HttpService httpService = new HttpService(mapper);
        FinhubService finhubService = new FinhubService(httpService);

        new MCPServer(mapper, finhubService).create();
    }


}
