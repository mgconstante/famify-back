package br.edu.unifio.famify.dto;

public record SteamGameDTO(
    Integer appid,
    String name,
    Integer playtime_forever
) {}
