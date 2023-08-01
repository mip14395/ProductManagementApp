package persistence;

import java.util.List;

import domain.model.Pottery;

public class PotteryDAOImpl implements DAO<Pottery> {

    PotteryJdbcGateway potteryJdbcGateway;

    public PotteryDAOImpl(PotteryJdbcGateway potteryJdbcGateway) {
        this.potteryJdbcGateway = potteryJdbcGateway;
    }

    @Override
    public int insert(Pottery t) {
        return potteryJdbcGateway.insert(t);
    }

    @Override
    public int update(Pottery t) {
        return potteryJdbcGateway.update(t);
    }

    @Override
    public int delete(Pottery t) {
        return potteryJdbcGateway.delete(t);
    }

    @Override
    public List<Pottery> selectAll() {
        return potteryJdbcGateway.selectAll();
    }

}
