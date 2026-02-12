package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.game;

import cat.itacademy.s05.t01.n01.blackjack.application.game.port.out.GameRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Deck;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;

import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper.CardDocument;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.mapper.PlayerDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class GameRepositoryMongoAdapter implements GameRepositoryPort {
    public final SpringDataGameMongoRepository repository;
    @Override
    public Mono<Game> save(Game game){
        GameDocument document = toDocument(game);
        return repository.save(document).map(this::toDomain);
    }
    @Override
    public Mono<Game> findById(UUID gameId){
        return repository.findById(gameId).map(this::toDomain);
    }
    @Override
    public Mono<Void> deleteById(UUID gameId) {
       return repository.deleteById(gameId);
    };

    private GameDocument toDocument(Game game) {
        GameDocument doc = new GameDocument();
        doc.setId(game.getId());
        doc.setStatus(game.getStatus().name());
        doc.setCurrentPlayerIndex(game.getCurrentPlayerIndex());
        doc.setPotAmount(game.getPot().getAmount().longValue());
        doc.setCreatedAt(game.getCreatedAt().toEpochMilli());
        doc.setUpdatedAt(game.getUpdatedAt().toEpochMilli());

// Store Players ids
        doc.setPlayers(
                game.getPlayers().stream()
                        .map(PlayerDocument::fromDomain)
                        .toList()
        );
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
        game.getWinner().ifPresent(winner ->
                doc.setWinnerId(winner.getId())
        );
        return doc;
    }
        private Game toDomain (GameDocument doc){
            Game game = new Game(doc.getId());

            game.setStatus(GameStatus.valueOf(doc.getStatus()));
            game.setCurrentPlayerIndex(doc.getCurrentPlayerIndex());
            game.setPot(new Money(doc.getPotAmount()));

            //Players
            List<Player> playersList = doc.getPlayers().stream()
                    .map(PlayerDocument::toDomain)
                    .toList();
            game.setPlayers(playersList);

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
                game.getPlayers().stream()
                        .filter(p -> p.getId().equals(doc.getWinnerId()))
                        .findFirst()
                        .ifPresent(game::setWinner);
            }

            game.setUpdatedAt(Instant.ofEpochMilli(doc.getUpdatedAt()));

            return game;
    }
    }

