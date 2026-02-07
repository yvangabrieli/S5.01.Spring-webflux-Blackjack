package cat.itacademy.s05.t01.n01.blackjack.web.dto.request;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import lombok.NoArgsConstructor;

@NotNull
@NoArgsConstructor

public class UpdatePlayerRequest {
    @NotNull (message =  "Name cannot be null")

    private String name;
    private Money money;

    @JsonCreator
    public UpdatePlayerRequest( @JsonProperty ("name") String name,  @JsonProperty ("money") Money money){
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }
    public Money getMoney(){
        return money;
        }
}
