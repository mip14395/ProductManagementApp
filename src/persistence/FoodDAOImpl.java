package persistence;

import java.util.List;

import domain.model.Food;

public class FoodDAOImpl implements DAO<Food> {

    FoodJdbcGateway foodJdbcGateway;

    public FoodDAOImpl(FoodJdbcGateway foodJdbcGateway) {
        this.foodJdbcGateway = foodJdbcGateway;
    }

    @Override
    public int insert(Food t) {
        return foodJdbcGateway.insert(t);
    }

    @Override
    public int update(Food t) {
        return foodJdbcGateway.update(t);
    }

    @Override
    public int delete(Food t) {
        return foodJdbcGateway.delete(t);
    }

    @Override
    public List<Food> selectAll() {
        return foodJdbcGateway.selectAll();
    }

}
