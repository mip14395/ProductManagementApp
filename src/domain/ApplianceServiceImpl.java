package domain;

import java.util.List;

import domain.model.Appliance;
import persistence.ApplianceDAOImpl;
import persistence.ApplianceJdbcGateway;

public class ApplianceServiceImpl implements Service<Appliance> {

    ApplianceDAOImpl applianceDAOImpl;

    public ApplianceServiceImpl() {
        this.applianceDAOImpl = new ApplianceDAOImpl(new ApplianceJdbcGateway());
    }

    @Override
    public int insert(Appliance t) {
        return applianceDAOImpl.insert(t);
    }

    @Override
    public int update(Appliance t) {
        return applianceDAOImpl.update(t);
    }

    @Override
    public int delete(Appliance t) {
        return applianceDAOImpl.delete(t);
    }

    @Override
    public List<Appliance> selectAll() {
        return applianceDAOImpl.selectAll();
    }

}
