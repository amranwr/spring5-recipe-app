package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
    private String description;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String source;
    private String url;
    @Lob //large object
    private String directions;
    @Lob
    private Byte[] image;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "recipe")
    private Set<Ingredient> ingrediants =  new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public void addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingrediants.add(ingredient);
    }
    
   public void setNote(Note note) {
        note.setRecipe(this);
        this.note = note;
    }
}
