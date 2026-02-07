package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.game;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.out.GameRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Deck;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;

import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper.CardDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class GameRepositoryMongoAdapter implements GameRepositoryPort {
    public final SpringDataGameMongoRepository repository;
    @Override
    public void save(Game game){
        repository.save(toDocument(game));
    }
    @Override
    public Optional<Game> findById(UUID gameId){
        return repository.findById(gameId).map(this::toDomain);
    }
    @Override
    public void deleteById(UUID gameId){
        repository.deleteById(gameId);
    }

    private GameDocument toDocument(Game game) {
        GameDocument doc = new GameDocument();
        doc.setId(game.getId());
        doc.setStatus(game.getStatus().name());
        doc.setCurrentPlayerIndex(game.getCurrentPlayerIndex());
        doc.setPotAmount(game.getPot().getAmount().longValue());
        doc.setCreatedAt(game.getCreatedAt().toEpochMilli());
        doc.setUpdatedAt(game.getUpdatedAt().toEpochMilli());

// Store Players ids
        List<UUID> playerIds = game.getPlayers().stream()
                .map(Player::getId)
                .toList();
        doc.setPlayerIds(playerIds);
// Deck
        doc.setDeck(game.getDeck().getCards()
                        .stream()
                        .map(CardDocument::fromDomain)
                        .toList());
//Dealer hand
        doc.setDealerHand(game.getDealer().getHand().getCards().stream()
                .map(CardDocument::fromDomain)
                .toList());
// if wins
        if (game.getWinner() != null) {
            doc.setWinnerId(game.getWinner().orElseThrow().getId());
        }

        return doc;
    }
        private Game toDomain (GameDocument doc){
            Game game = new Game(doc.getId());
            game.setStatus(GameStatus.valueOf(doc.getStatus()));
            game.setCurrentPlayerIndex(doc.getCurrentPlayerIndex());
            game.setPot(new Money(doc.getPotAmount()));

            //Players
            game.getPlayers().clear();
            for (UUID playerId : doc.getPlayerIds()){
                game.getPlayers().add(new Player(playerId, "unknown", new Money(0)));
            }

            // Deck
            List<Card> deckCards = doc.getDeck().stream()
                    .map(CardDocument::toDomain)
                    .toList();
            game.setDeck(Deck.fromCards(deckCards));

            // Dealer hand
            List<Card> dealerCards = doc.getDealerHand().stream()
                    .map(CardDocument::toDomain)
                    .toList();
            game.getDealer().restoreHand(dealerCards);


            // Winner
            if (doc.getWinnerId() != null) {
                Player winner = game.getPlayers().stream()
                        .filter(p -> p.getId().equals(doc.getWinnerId()))
                        .findFirst()
                        .orElse(new Player(doc.getWinnerId(), "unknown", new Money(0)));
                game.setWinner(winner);
            }

            game.setUpdatedAt(Instant.ofEpochMilli(doc.getUpdatedAt()));

            return game;
    }
    }

