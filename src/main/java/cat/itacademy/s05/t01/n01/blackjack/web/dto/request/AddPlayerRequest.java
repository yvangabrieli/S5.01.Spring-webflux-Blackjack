package cat.itacademy.s05.t01.n01.blackjack.web.dto.request;

import cat.itacademy.s05.t01.n01.blackjack.domain.model.valueobject.Money;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPlayerRequest {
 @NotBlank
 private String name;
 @NotNull
 private Money initialMoney;

}
