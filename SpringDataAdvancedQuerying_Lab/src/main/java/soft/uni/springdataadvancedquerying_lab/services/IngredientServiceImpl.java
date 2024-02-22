package soft.uni.springdataadvancedquerying_lab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.uni.springdataadvancedquerying_lab.entities.Ingredient;
import soft.uni.springdataadvancedquerying_lab.repositories.IngredientRepository;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getIngredientByNameStartingWith(Character letter) {
        return this.ingredientRepository.getIngredientByNameStartingWith(letter);
    }

    @Override
    public List<Ingredient> getIngredientsByNameOrderByPrice(String name) {
        return this.ingredientRepository.getIngredientsByNameOrderByPrice(name);
    }

    @Override
    @Transactional
    public void deleteIngredientByName(String name) {
        this.ingredientRepository.deleteIngredientByName(name);
    }

    @Override
    @Transactional
    public void updateAllBy() {
        this.ingredientRepository.updateAllBy();
    }


}
