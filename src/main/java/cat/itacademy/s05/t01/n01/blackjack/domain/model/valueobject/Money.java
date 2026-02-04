package cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject;


import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private final BigDecimal amount;

    public Money(double amount) {
        this.amount = BigDecimal.valueOf(amount).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public Money(BigDecimal amount) {
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.signum() < 0) throw new IllegalArgumentException("Insufficient funds");
        return new Money(result);
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "$" + amount;
    }
}
