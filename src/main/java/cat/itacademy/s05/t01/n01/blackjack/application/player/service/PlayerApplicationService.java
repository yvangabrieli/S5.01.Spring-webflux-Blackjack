package cat.itacademy.s05.t01.n01.blackjack.application.player.service;

import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.DeletePlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.GetPlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.player.port.in.UpdatePlayerUseCase;
import cat.itacademy.s05.t01.n01.blackjack.application.player.port.out.PlayerRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.PlayerNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;

import java.util.UUID;

public class PlayerApplicationService implements DeletePlayerUseCase, GetPlayerUseCase, UpdatePlayerUseCase {
    private final PlayerRepositoryPort repositoryPort;

    public PlayerApplicationService (PlayerRepositoryPort repositoryPort) {
    this.repositoryPort = repositoryPort;
    }
    @Override
    public Player getPlayer(UUID playerId) {
        return repositoryPort.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
    @Override
    public void deletePlayer(UUID playerId){
     Player player = getPlayer(playerId);
    repositoryPort.deleteById(player.getId());
        }
    @Override
    public Player updatePlayer(UUID playerId, String newName, Money newMoney) {
        Player player = getPlayer(playerId);

        if (newName != null && !newName.isBlank()) {
            player.updateName(newName);
        }

        if (newMoney != null) {
            repositoryPort.save(player); // money changes are handled via Game normally, skip here if domain forbids direct update
        }
        repositoryPort.save(player);
        return player;
    }
    }


