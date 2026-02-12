package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.game;

import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper.CardDocument;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper.PlayerDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;


import java.util.List;
import java.util.UUID;

@Data
public class GameDocument {
    @Id
    private UUID id;
    private String status;
    private List<PlayerDocument> players;
    private List<CardDocument> deck;
    private List<CardDocument> dealerHand;
    private int currentPlayerIndex;
    private UUID winnerId;
    private long potAmount;
    private long createdAt;
    private long updatedAt;

}
