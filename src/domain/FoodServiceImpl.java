package domain;

import java.util.List;

import domain.model.Food;
import persistence.FoodDAOImpl;
import persistence.FoodJdbcGateway;

public class FoodServiceImpl implements Service<Food> {

    FoodDAOImpl foodDAOImpl;

    public FoodServiceImpl() {
        this.foodDAOImpl = new FoodDAOImpl(new FoodJdbcGateway());
    }

    @Override
    public int insert(Food t) {
        return foodDAOImpl.insert(t);
    }

    @Override
    public int update(Food t) {
        return foodDAOImpl.update(t);
    }

    @Override
    public int delete(Food t) {
        return foodDAOImpl.delete(t);
    }

    @Override
    public List<Food> selectAll() {
        return foodDAOImpl.selectAll();
    }

}
