package org.morsaprogramando.mcp_demo.models;

public record MarketStatusResponse(
        String exchange,
        String holiday,
        Boolean isOpen,
        String session,
        String timezone,
        Long t
) {

    @Override
    public String toString() {
        return String.format("The %s market isOpen: %s", exchange, isOpen);
    }
}
