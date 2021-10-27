package guru.springframework.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@EqualsAndHashCode(exclude = {"recipe","uniteOfMeasure"})
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
    public Ingredient(){

    }

}
