package soft.uni.springdataadvancedquerying_lab.services;

import soft.uni.springdataadvancedquerying_lab.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> getIngredientByNameStartingWith(Character letter);
    List<Ingredient> getIngredientsByNameOrderByPrice(String name);
    void deleteIngredientByName(String name);
    void updateAllBy();
}
