package cat.itacademy.s05.t01.n01.blackjack.web.mapper;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Game;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.GameResponse;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.LastMoveResponse;

public class GameResponseMapper {
    private GameResponseMapper(){
    }

    public static GameResponse from(Game game){
        LastMoveResponse lastMove = null;
        if (game.getLastMove()!=null){
            lastMove = new LastMoveResponse(
                    game.getLastMovePlayerId(),
                    game.getLastMove().toString(),
                    game.getLastCardDrawn().toString());
        }
        return new GameResponse(
                    game.getId(),
                    game.getStatus(),
                    game.getCurrentPlayerIndex(),
                    lastMove
        );
    }
}
