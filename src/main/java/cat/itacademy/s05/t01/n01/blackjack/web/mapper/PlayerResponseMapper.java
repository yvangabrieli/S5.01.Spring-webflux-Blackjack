package cat.itacademy.s05.t01.n01.blackjack.web.mapper;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.PlayerResponse;

public class PlayerResponseMapper {
    private PlayerResponseMapper() {
    }

    public static PlayerResponse from(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getName(),
                player.getMoney().getAmount()
        );
    }
}
