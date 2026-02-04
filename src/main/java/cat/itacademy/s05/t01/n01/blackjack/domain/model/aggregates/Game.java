package cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates;

import cat.itacademy.s05.t01.n01.blackjack.domain.exception.GameFinishedException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.InvalidMoveException;
import cat.itacademy.s05.t01.n01.blackjack.domain.exception.NotPlayersTurnException;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.entity.Dealer;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.enums.MoveType;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Card;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Deck;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Score;
import cat.itacademy.s05.t01.n01.blackjack.domain.service.BlackjackDomainService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Game {

    private final UUID id;
    private GameStatus status;
    private final List<Player> players;
    private final Dealer dealer;
    private Deck deck;
    private int currentPlayerIndex;
    private Player winner;
    private Money pot;
    private final Instant createdAt;
    private Instant updatedAt;
    private MoveType lastMove;
    private Card lastCardDrawn;
    private UUID lastMovePlayerId;

    private static final int MAX_PLAYERS = 5;

    public Game(UUID id) {
        this.id = id;
        this.status = GameStatus.NOT_STARTED;
        this.players = new ArrayList<>();
        this.dealer = new Dealer();
        this.deck = new Deck().shuffle();
        this.currentPlayerIndex = 0;
        this.winner = null;
        this.pot = new Money(0);
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // ------------------------
    // Aggregate Methods
    // ------------------------

    public void addPlayer(Player player) {
        if (status != GameStatus.NOT_STARTED) {
            throw new InvalidMoveException("Cannot add player once game started");
        }
        if (players.size() >= MAX_PLAYERS) {
            throw new InvalidMoveException("Max players reached");
        }
        players.add(player);
        updatedAt = Instant.now();
    }

    public void placeBet (Player player, Money amount){
        if (status != GameStatus.NOT_STARTED){
            throw new InvalidMoveException("Cannot place bet after game started");
        }
        if (!players.contains(player)){
            throw new InvalidMoveException("Player not in the game");
        }

        player.placeBet(amount);
        pot = pot.add(amount);
        updatedAt = Instant.now();
    }
    public Money getPot(){return pot;}

    public void start() {
        if (status != GameStatus.NOT_STARTED) {
            throw new InvalidMoveException("Game already started");
        }
        if (players.isEmpty()) {
            throw new InvalidMoveException("Cannot start a game with no players");
        }

        // Deal two cards to each player
        for (Player player : players) {
            player.addCard(deck.draw());
            player.addCard(deck.draw());
        }

        // Deal two cards to dealer (one hidden)
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());

        status = GameStatus.IN_PROGRESS;
        currentPlayerIndex = 0;
        updatedAt = Instant.now();
    }

    public void play(UUID playerId, MoveType move) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new GameFinishedException("Cannot play a move, game is not in progress");
        }
        Player currentPlayer = players.get(currentPlayerIndex);
        if (!currentPlayer.getId().equals(playerId)){
            throw new NotPlayersTurnException(playerId);
        }
        this.lastMove = move;
        this.lastMovePlayerId = playerId;
        switch (move) {
            case HIT -> {
                Card drawn = deck.draw();
                currentPlayer.addCard(drawn);
                if (currentPlayer.getHand().score().isBusted()) {
                    nextTurn();
                }
            }
            case STAND -> nextTurn();
            default -> throw new InvalidMoveException("Unknown move type");
        }

        // If all players done, dealer plays
        if (allPlayersDone()) {
            dealerPlay();
            finish();
        }

        updatedAt = Instant.now();
    }

    private void nextTurn() {
        currentPlayerIndex++;
    }

    private boolean allPlayersDone() {
        return currentPlayerIndex >= players.size();
    }

    private void dealerPlay() {
        BlackjackDomainService rules = new BlackjackDomainService();
        while (rules.dealerShouldHit(dealer)) {
            dealer.addCard(deck.draw());
        }
    }

    public void finish() {
        if (status == GameStatus.FINISHED) {return;}

        BlackjackDomainService rules = new BlackjackDomainService();

        // Ask domain rules who won
        this.winner = rules.determineWinner(players, dealer).orElse(null);

        if (winner != null) {
            winner.addMoney(pot); // winner takes pot
        }

        // Clear all bets
        for (Player player : players) {
            player.clearBet();
        }

        pot = new Money(0);
        status = GameStatus.FINISHED;
        updatedAt = Instant.now();
    }

    // ------------------------
    // Query / Utility Methods
    // ------------------------

    public Player getCurrentPlayer() {
        if (status != GameStatus.IN_PROGRESS || allPlayersDone()) {
            throw new InvalidMoveException("No current player");
        }
        return players.get(currentPlayerIndex);
    }

    public boolean isGameFinished() {
        return status == GameStatus.FINISHED;
    }

    public boolean isPlayerTurn(UUID playerId) {
        if (status != GameStatus.IN_PROGRESS || allPlayersDone()) return false;
        return players.get(currentPlayerIndex).getId().equals(playerId);
    }

    public Score getPlayerScore(UUID playerId) {
        return players.stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .map(p -> p.getHand().score())
                .orElseThrow(() -> new InvalidMoveException("Player not found"));
    }

    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }


    // ------------------------
    // Getters
    // ------------------------

    public UUID getId() { return id; }
    public GameStatus getStatus() { return status; }
    public List<Player> getPlayers() { return List.copyOf(players); }
    public Dealer getDealer() { return dealer; }
    public Deck getDeck() { return deck; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public int getCurrentPlayerIndex(){return currentPlayerIndex;}
    public UUID getLastMovePlayerId() {return lastMovePlayerId;}
    public Card getLastCardDrawn() {return lastCardDrawn;}
    public MoveType getLastMove(){return lastMove;}


    // Setter
    public void setStatus(GameStatus status) {
        this.status = status;
    }


    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setPot(Money pot) {
        this.pot = pot;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setLastMove(MoveType lastMove) {
        this.lastMove = lastMove;
    }

    public void setLastCardDrawn(Card lastCardDrawn) {
        this.lastCardDrawn = lastCardDrawn;
    }

    public void setLastMovePlayerId(UUID lastMovePlayerId) {
        this.lastMovePlayerId = lastMovePlayerId;
    }
}

