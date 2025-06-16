# MCP Server demo for Market news

This MCP server is a wrapper around some [finnhub.io](https://finnhub.io/) API calls for retrieving market status and market news.
It is a very simple demo but it is enough to get started around MCP servers using the Java SDK of the [model context protocol](https://modelcontextprotocol.io/sdk/java/mcp-overview).

## Requirements

- Java 21 or greater
- Maven
- Claude Desktop (free plan)
- Finnhub account (free plan)

## Installation

- ```bash
  mvn clean package
  ```
- Add a configuration for Claude Desktop
  - Windows or Mac (https://modelcontextprotocol.io/quickstart/server#windows)
  - Add the following into the __claude_desktop_config.json__ file:
    - ```json
        {
          "mcpServers": {
              "US_stock_market_status": {
                  "command": "java",
                  "args": [
                    "-jar",
                    "<PATH-TO-JAR>"
                  ],
                  "env": {
                    "FINNHUB_API_TOKEN": "<INSERT-TOKEN>"
                  }
              }
          }
        }
      ```
- Restart Claude Desktop, and enable this tool.
- Example prompts:
  - _Give me the latest market news._
  - _Give me the latest market news. Just 1 new and give priority to the news related to the attacks between Israel and Iran._
  - _Give me the top 3 latest market news with their reference urls and the original headlines._ 

