package cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb.game;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SpringDataGameMongoRepository
        extends MongoRepository<GameDocument, UUID>{
}
