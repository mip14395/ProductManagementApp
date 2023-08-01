package domain;

import java.util.List;

import domain.model.Pottery;
import persistence.PotteryDAOImpl;
import persistence.PotteryJdbcGateway;

public class PotteryServiceImpl implements Service<Pottery> {

    PotteryDAOImpl potteryDAOImpl;

    public PotteryServiceImpl() {
        this.potteryDAOImpl = new PotteryDAOImpl(new PotteryJdbcGateway());
    }

    @Override
    public int insert(Pottery t) {
        return potteryDAOImpl.insert(t);
    }

    @Override
    public int update(Pottery t) {
        return potteryDAOImpl.update(t);
    }

    @Override
    public int delete(Pottery t) {
        return potteryDAOImpl.delete(t);
    }

    @Override
    public List<Pottery> selectAll() {
        return potteryDAOImpl.selectAll();
    }

}
