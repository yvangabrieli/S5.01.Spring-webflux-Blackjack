package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.player;

import cat.itacademy.s05.t01.n01.blackjack.application.player.port.out.PlayerRepositoryPort;
import cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates.Player;
import cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.mapper.PlayerEntityMapper;

import java.util.Optional;
import java.util.UUID;

public class PlayerRepositoryJpaAdapter implements PlayerRepositoryPort {

    private final SpringDataPlayerJpaRepository repository;

    public PlayerRepositoryJpaAdapter(SpringDataPlayerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Player player) {
        repository.save(PlayerEntityMapper.toEntity(player));
    }

    @Override
    public Optional<Player> findById(UUID playerId) {
        return repository.findById(playerId)
                .map(PlayerEntityMapper::toDomain);
    }

    @Override
    public void deleteById(UUID playerId) {
        repository.deleteById(playerId);
    }
}
