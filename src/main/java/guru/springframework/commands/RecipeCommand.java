package guru.springframework.commands;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    private Difficulty difficulty;

    @NotBlank
    @Size(min = 3,max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private int prepTime;

    @Min(1)
    @Max(999)
    private int cookTime;

    @Min(1)
    @Max(100)
    private int servings;
    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;
    private Byte[] image;
    private Set<Ingredient> ingrediants =  new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    private Note note;
}
