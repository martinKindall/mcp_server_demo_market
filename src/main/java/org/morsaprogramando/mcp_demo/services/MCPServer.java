package org.morsaprogramando.mcp_demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import org.morsaprogramando.mcp_demo.models.MarketNews;
import org.morsaprogramando.mcp_demo.models.MarketStatusResponse;

import java.util.ArrayList;
import java.util.List;

public class MCPServer {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MCPServer.class);
    private final ObjectMapper mapper;
    private final FinhubService finhubService;

    public MCPServer(ObjectMapper mapper, FinhubService finhubService) {
        this.mapper = mapper;
        this.finhubService = finhubService;
    }

    public void create() {
        var transportProvider = new StdioServerTransportProvider(mapper);

        var toolSpecifications = getSyncToolSpecifications();

        McpSyncServer syncServer = McpServer.sync(transportProvider)
                .serverInfo("morsaprogramando-mcp-server", "0.0.1")
                .capabilities(McpSchema.ServerCapabilities.builder()
                        .tools(true)
                        .logging()
                        .build()
                )
                .tools(toolSpecifications)
                .build();

        log.info("Starting server ...");
    }

    private List<McpServerFeatures.SyncToolSpecification> getSyncToolSpecifications() {
        var schema = """
            {
                "type" : "object",
                "id" : "urn:jsonschema:Operation",
                "properties" : {
                    "operation" : {
                        "type" : "string"
                    }
                }
            }
            """;

        var marketStatusTool = new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool(
                        "get_market_status",
                        "Get the current US stock market status",
                        schema),
                (exchange, arguments) -> {
                    MarketStatusResponse marketStatusResponse = finhubService.fetchMarketStatus();

                    return new McpSchema.CallToolResult(String.valueOf(marketStatusResponse), false);
                }
        );

        var marketNewsTool = new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool(
                        "get_market_news",
                        "Get latest stock market news.",
                        schema),
                (exchange, arguments) -> {
                    MarketNews[] news = finhubService.getMarketGeneralNews();

                    List<McpSchema.Content> contents = new ArrayList<>();
                    for (MarketNews marketNew : news) {
                        contents.add(new McpSchema.TextContent(marketNew.headlineSummaryAndUrl()));
                    }

                    return new McpSchema.CallToolResult(contents, false);
                }
        );

        return List.of(marketStatusTool, marketNewsTool);
    }
}
