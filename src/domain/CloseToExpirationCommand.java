package domain;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import domain.model.Product;
import presentation.ProductManagementUI;

public class CloseToExpirationCommand implements Command {
    private Product modelRemote;

    public CloseToExpirationCommand(Product modelRemote) {
        this.modelRemote = modelRemote;
    }

    @Override
    public void execute() {
        ((ProductManagementUI) modelRemote.getSubscribers().get(0))
                .setCondition("EXP <'" + new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1) * 7) + "'");
    }

}
