package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPlayerJpaRepository extends JpaRepository<PlayerEntity, UUID> {
}
