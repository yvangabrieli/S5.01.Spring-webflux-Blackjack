package cat.itacademy.s05.t01.n01.blackjack.web.controllers;

import cat.itacademy.s05.t01.n01.blackjack.application.ranking.port.in.GetRankingUseCase;
import cat.itacademy.s05.t01.n01.blackjack.web.dto.response.RankingResponse;
import cat.itacademy.s05.t01.n01.blackjack.web.mapper.RankingResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final GetRankingUseCase getRankingUseCase;


    @GetMapping
    public Flux<RankingResponse> getRanking() {
        return Flux.fromIterable(
                getRankingUseCase.getRanking()
                        .stream()
                        .map(RankingResponseMapper::from)
                        .toList()
        );
    }
}