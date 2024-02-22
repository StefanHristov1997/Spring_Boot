package soft.uni.springdataadvancedquerying_lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft.uni.springdataadvancedquerying_lab.entities.Shampoo;
import soft.uni.springdataadvancedquerying_lab.entities.Size;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> getShampooBySizeOrderById(Size size);

    List<Shampoo> getShampooBySizeOrLabel_IdOrderByPrice(Size size, Long label_id);

    List<Shampoo> getShampooByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countShampooByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name IN (:ingredients)")
    List<Shampoo> findShampooByIngredients(List<String> ingredients);

    @Query("SELECT s FROM Shampoo AS s " +
            "WHERE s.ingredients.size < :count")
    List<Shampoo> findShampooByIngredientsLessThan(int count);
}
