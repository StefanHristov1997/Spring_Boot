package soft.uni.springdataadvancedquerying_lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import soft.uni.springdataadvancedquerying_lab.services.IngredientService;
import soft.uni.springdataadvancedquerying_lab.services.ShampooService;

@Component
public class Command implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public Command(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        ingredientService.updateAllBy();
    }
}