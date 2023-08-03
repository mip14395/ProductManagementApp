package domain;

import java.util.List;

import domain.model.Product;
import persistence.ProductDAOImpl;
import persistence.ProductJdbcGateway;

public class ProductServiceImpl implements Service<Product> {

    ProductDAOImpl productDAOImpl;

    public ProductServiceImpl(ProductJdbcGateway productJdbcGateway) {
        this.productDAOImpl = new ProductDAOImpl(productJdbcGateway);
    }

    public ProductServiceImpl() {
        this.productDAOImpl = new ProductDAOImpl(new ProductJdbcGateway() {

            @Override
            public int insert(Product t) {
                throw new UnsupportedOperationException("Unimplemented method 'insert'");
            }

            @Override
            public int update(Product t) {
                throw new UnsupportedOperationException("Unimplemented method 'update'");
            }

        });
    }

    @Override
    public int insert(Product t) {
        return productDAOImpl.insert(t);
    }

    @Override
    public int update(Product t) {
        return productDAOImpl.update(t);
    }

    @Override
    public int delete(Product t) {
        return productDAOImpl.delete(t);
    }

    @Override
    public List<Product> search(String condition) {
        return productDAOImpl.search(condition);
    }

    @Override
    public List<Product> selectAll() {
        return productDAOImpl.selectAll();
    }

}
