package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.game;

import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper.CardDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document (collection = "games")
public class GameDocument {
    @Id
    private UUID id;
    private String status;
    private List<UUID> playerIds;
    private List<CardDocument> deck;
    private List<CardDocument> dealerHand;
    private int currentPlayerIndex;
    private UUID winnerId;
    private long potAmount;
    private long createdAt;
    private long updatedAt;

}
