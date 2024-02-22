package soft.uni.springdataadvancedquerying_lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft.uni.springdataadvancedquerying_lab.entities.Ingredient;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> getIngredientByNameStartingWith(Character letter);

    List<Ingredient> getIngredientsByNameOrderByPrice(String name);

    void deleteIngredientByName(String name);

    @Query ("UPDATE Ingredient " +
            "SET price = price * 1.10 ")
    @Modifying
    void updateAllBy();
}
