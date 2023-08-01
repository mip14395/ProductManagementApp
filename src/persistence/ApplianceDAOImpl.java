package persistence;

import java.util.List;

import domain.model.Appliance;

public class ApplianceDAOImpl implements DAO<Appliance> {

    ApplianceJdbcGateway applianceJdbcGateway;

    public ApplianceDAOImpl(ApplianceJdbcGateway applianceJdbcGateway) {
        this.applianceJdbcGateway = applianceJdbcGateway;
    }

    @Override
    public int insert(Appliance t) {
        return applianceJdbcGateway.insert(t);
    }

    @Override
    public int update(Appliance t) {
        return applianceJdbcGateway.update(t);
    }

    @Override
    public int delete(Appliance t) {
        return applianceJdbcGateway.delete(t);
    }

    @Override
    public List<Appliance> selectAll() {
        return applianceJdbcGateway.selectAll();
    }

}
