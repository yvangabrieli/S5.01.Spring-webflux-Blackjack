package cat.itacademy.s05.t01.n01.blackjack.web.mapper;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.DealerResponse;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.GameResponse;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.LastMoveResponse;

import java.util.UUID;
import java.util.stream.Collectors;

public class GameResponseMapper {
    private GameResponseMapper() {}

    public static GameResponse from(Game game) {
        LastMoveResponse lastMove = null;
        if (game.getLastMove() != null) {
            lastMove = new LastMoveResponse(
                    game.getLastMovePlayerId(),
                    game.getLastMove().toString(),
                    game.getLastCardDrawn() != null ? game.getLastCardDrawn().toString() : null
            );
        }

        var players = game.getPlayers().stream()
                .map(PlayerResponseMapper::fromWithHand)  // ← Use enhanced mapper
                .collect(Collectors.toList());

        DealerResponse dealer = new DealerResponse(
                game.getDealer().getHand().getCards().stream()
                        .map(card -> card.toString())
                        .collect(Collectors.toList()),
                game.getDealer().getHand().score().getValue(),
                game.getDealer().getHand().isBusted()
        );

        UUID winnerId = game.getWinner()
                .map(Player::getId)
                .orElse(null);

        return new GameResponse(
                game.getId(),
                game.getStatus(),
                game.getCurrentPlayerIndex(),
                players,
                dealer,
                winnerId,
                lastMove
        );
    }
}