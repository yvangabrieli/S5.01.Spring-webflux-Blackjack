package cat.itacademy.s05.t01.n01.blackjack.web.controllers;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in.GetRankingUseCase;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.RankingResponse;
import cat.itacademy.s05.t01.n01.blackjack.web.mapper.RankingResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/ranking")

public class RankingController {

    private final GetRankingUseCase getRankingUseCase;

    public RankingController (@Qualifier("rankingApplicationService")GetRankingUseCase getRankingUseCase){
        this.getRankingUseCase = getRankingUseCase;

    }

    @GetMapping
    public Flux<RankingResponse> getRanking(@RequestParam UUID gameId) {
        return getRankingUseCase.getRanking(gameId)
                .flatMapMany(Flux::fromIterable)
                .map(RankingResponseMapper::from);
    }
}