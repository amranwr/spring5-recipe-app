package guru.springframework.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipeset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Recipe> getRecipeset() {
        return recipeset;
    }

    public void setRecipeset(Set<Recipe> recipeset) {
        this.recipeset = recipeset;
    }
}
