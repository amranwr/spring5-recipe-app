package guru.springframework.repositories;

import guru.springframework.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngrediantRepository extends CrudRepository<Ingredient,Long> {
}
