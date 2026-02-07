package cat.itacademy.s05.t01.n01.blackjack.web.controllers;

import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.DeletePlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.GetPlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.UpdatePlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.request.UpdatePlayerRequest;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.PlayerResponse;
import cat.itacademy.s05.t01.n01.blackjack.web.mapper.PlayerResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping ("/players")
@RequiredArgsConstructor

public class PlayerController {
    private final GetPlayerUseCase getPlayerUseCase;
    private final DeletePlayerUseCase deletePlayerUseCase;
    private final UpdatePlayerUseCase updatePlayerUseCase;


@GetMapping ("/{playerId}")
    public Mono<PlayerResponse> getPlayer(
            @PathVariable UUID playerId){
    Player player = getPlayerUseCase.getPlayer(playerId);
    return Mono.just(PlayerResponseMapper.from(player));
}
@PutMapping ("/{playerId}")
    public Mono<PlayerResponse> updatePlayer(
            @PathVariable UUID playerId,
            @RequestBody @Valid UpdatePlayerRequest request
){
    Player player = updatePlayerUseCase.updatePlayer(playerId, request.getName(), request.getMoney());
    return Mono.just(PlayerResponseMapper.from(player));
}

@DeleteMapping ("/{playerId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer (@PathVariable UUID playerId){
    deletePlayerUseCase.deletePlayer(playerId);
    }
}
