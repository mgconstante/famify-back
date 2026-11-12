package br.edu.unifio.famify.dto;

import java.util.List;

public record SteamResponse(
    Response response
) {
    public record Response(
        Integer game_count,
        List<SteamGameDTO> games
    ) {}
}
