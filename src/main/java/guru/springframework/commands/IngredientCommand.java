package guru.springframework.commands;

import guru.springframework.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id ;
    private BigDecimal amout;
    private String description;
    private UnitOfMeasure unitOfMeasure;
}
