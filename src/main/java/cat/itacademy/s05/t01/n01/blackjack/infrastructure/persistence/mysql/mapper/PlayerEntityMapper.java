package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.mapper;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.player.PlayerEntity;

public class PlayerEntityMapper {
    public static PlayerEntity toEntity(Player player) {
    return new PlayerEntity(
            player.getId(),
            player.getName(),
            player.getMoney().getAmount()
    );
}

    public static Player toDomain(PlayerEntity entity) {
        return new Player(
                entity.getId(),
                entity.getName(),
                new Money(entity.getBalance())
        );
    }
}

