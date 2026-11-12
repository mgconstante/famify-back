package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.SteamGameDTO;
import br.edu.unifio.famify.dto.SteamResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class SteamService {
    
    @Value("${steam.api.key}")
    private String apiKey;

    private final RestClient restClient;

    public SteamService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.steampowered.com")
                .build();
    }

    public List<SteamGameDTO> buscarJogos(String steamId) {

        SteamResponse resposta = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/IPlayerService/GetOwnedGames/v1/")
                        .queryParam("key", apiKey)
                        .queryParam("steamid", steamId)
                        .queryParam("include_appinfo", true)
                        .queryParam("include_played_free_games", true)
                        .build())
                .retrieve()
                .body(SteamResponse.class);

        if (resposta == null || resposta.response() == null
                || resposta.response().games() == null) {
            return List.of();
        }

        return resposta.response().games();
    }
}
