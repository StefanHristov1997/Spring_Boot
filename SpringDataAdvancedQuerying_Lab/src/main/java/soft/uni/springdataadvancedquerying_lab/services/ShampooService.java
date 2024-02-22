package soft.uni.springdataadvancedquerying_lab.services;

import org.springframework.stereotype.Service;
import soft.uni.springdataadvancedquerying_lab.entities.Ingredient;
import soft.uni.springdataadvancedquerying_lab.entities.Shampoo;
import soft.uni.springdataadvancedquerying_lab.entities.Size;

import java.math.BigDecimal;
import java.util.List;


public interface ShampooService  {
    List<Shampoo> getShampooBySizeOrderById(Size size);
    List<Shampoo> getShampooBySizeOrLabel_IdOrderByPrice(Size size, Long label_id);
    List<Shampoo> getShampooByPriceGreaterThanOrderByPriceDesc(BigDecimal price);
    int countShampooByPriceLessThan(BigDecimal price);
    List<Shampoo> findShampooByIngredients(List<String> ingredients);
    List<Shampoo> findShampooByIngredientsIsLessThan(int count);
}
