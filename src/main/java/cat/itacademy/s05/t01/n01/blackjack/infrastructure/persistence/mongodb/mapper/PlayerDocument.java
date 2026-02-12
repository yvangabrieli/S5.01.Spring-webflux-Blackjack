package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper;

import lombok.Data;
import java.util.UUID;

@Data
public class PlayerDocument {
    private UUID id;
    private String name;
    private long money;
    private long currentBet;

    public static PlayerDocument fromDomain(cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player player) {
        PlayerDocument doc = new PlayerDocument();
        doc.setId(player.getId());
        doc.setName(player.getName());
        doc.setMoney(player.getMoney().getAmount().longValue());
        doc.setCurrentBet(player.getCurrentBet().getAmount().longValue());
        return doc;
    }

    public cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player toDomain() {
        return new cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player(
                id,
                name,
                new cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money(money)
        );
    }
}