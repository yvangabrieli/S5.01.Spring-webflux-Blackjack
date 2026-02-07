package cat.itacademy.s05.t01.n01.blackjack.web.mapper;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.view.RankingEntry;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.RankingResponse;

public class RankingResponseMapper {
    public static RankingResponse from(RankingEntry entry) {
        return new RankingResponse(
                entry.getPlayerId(),
                entry.getPlayerName(),
                entry.getBalance()
        );
    }
}
