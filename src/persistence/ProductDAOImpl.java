package persistence;

import java.util.List;

import domain.model.Product;

public class ProductDAOImpl implements DAO<Product> {

    ProductJdbcGateway productJdbcGateway;

    public ProductDAOImpl(ProductJdbcGateway productJdbcGateway) {
        this.productJdbcGateway = productJdbcGateway;
    }

    @Override
    public int insert(Product t) {
        return productJdbcGateway.insert(t);
    }

    @Override
    public int update(Product t) {
        return productJdbcGateway.update(t);
    }

    @Override
    public int delete(Product t) {
        return productJdbcGateway.delete(t);
    }

    @Override
    public List<Product> selectAll() {
        return productJdbcGateway.selectAll();
    }

    @Override
    public List<Product> search(String condition) {
        return productJdbcGateway.search(condition);
    }

}
