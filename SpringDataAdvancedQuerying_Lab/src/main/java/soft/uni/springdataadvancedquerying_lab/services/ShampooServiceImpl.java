package soft.uni.springdataadvancedquerying_lab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft.uni.springdataadvancedquerying_lab.entities.Shampoo;
import soft.uni.springdataadvancedquerying_lab.entities.Size;
import soft.uni.springdataadvancedquerying_lab.repositories.ShampooRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {
    private ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> getShampooBySizeOrderById(Size size) {
        return shampooRepository.getShampooBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> getShampooBySizeOrLabel_IdOrderByPrice(Size size, Long label_id) {
        return shampooRepository.getShampooBySizeOrLabel_IdOrderByPrice(size, label_id);
    }

    @Override
    public List<Shampoo> getShampooByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return shampooRepository.getShampooByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countShampooByPriceLessThan(BigDecimal price) {
        return shampooRepository.countShampooByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findShampooByIngredients(List<String> ingredients) {
        return this.shampooRepository.findShampooByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> findShampooByIngredientsIsLessThan(int count) {
        return shampooRepository.findShampooByIngredientsLessThan(count);
    }


}
