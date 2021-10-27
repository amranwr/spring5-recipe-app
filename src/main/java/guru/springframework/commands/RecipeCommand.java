package guru.springframework.commands;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private Difficulty difficulty;
    private String description;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Set<Ingredient> ingrediants =  new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    private Note note;
}
