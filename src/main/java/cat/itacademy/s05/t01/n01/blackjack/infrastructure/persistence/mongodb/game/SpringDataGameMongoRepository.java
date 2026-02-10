package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.game;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataGameMongoRepository
        extends ReactiveMongoRepository<GameDocument, UUID> {
}
