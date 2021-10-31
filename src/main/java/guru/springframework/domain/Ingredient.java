package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER) //to make it easy for the reader!!!!!!!!
    private UnitOfMeasure unitOfMeasure;
    private BigDecimal amout;
    private String description;
    @ManyToOne
    private Recipe recipe;

    public Ingredient(String description, BigDecimal amout,  UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        this.amout = amout;
        this.description = description;
    }

}
