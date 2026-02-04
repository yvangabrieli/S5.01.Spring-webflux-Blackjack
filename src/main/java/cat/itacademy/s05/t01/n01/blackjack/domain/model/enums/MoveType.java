package cat.itacademy.s05.t01.n01.blackjack.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum MoveType {

    HIT("hit"),
    STAND("stand");

    private final String value;

    MoveType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static MoveType from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("MoveType cannot be null");
        }
        return Arrays.stream(values())
                .filter(m -> m.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown MoveType: " + value));
    }
}


