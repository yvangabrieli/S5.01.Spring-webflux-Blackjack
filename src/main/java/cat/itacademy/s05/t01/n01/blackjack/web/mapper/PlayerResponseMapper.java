package cat.itacademy.s05.t01.n01.blackjack.web.mapper;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.PlayerResponse;

import java.util.stream.Collectors;

public class PlayerResponseMapper {
    private PlayerResponseMapper() {}

    // For GET /players/{id}
    public static PlayerResponse from(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getName(),
                player.getMoney().getAmount().doubleValue()
        );
    }

    // For game context (with hand)
    public static PlayerResponse fromWithHand(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getName(),
                player.getMoney().getAmount().doubleValue(),
                player.getHand().getCards().stream()
                        .map(card -> card.toString())
                        .collect(Collectors.toList()),
                player.getHand().score().getValue(),
                player.getHand().isBusted()
        );
    }
}